package com.zackyj.Mmall.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.common.Constant;
import com.zackyj.Mmall.common.Exception.BusinessException;
import com.zackyj.Mmall.common.Exception.ExceptionEnum;
import com.zackyj.Mmall.dao.*;
import com.zackyj.Mmall.model.pojo.*;
import com.zackyj.Mmall.model.vo.OrderItemVO;
import com.zackyj.Mmall.model.vo.OrderProductVO;
import com.zackyj.Mmall.model.vo.OrderVO;
import com.zackyj.Mmall.model.vo.ShippingVO;
import com.zackyj.Mmall.service.IOrderService;
import com.zackyj.Mmall.utils.BigDecimalUtil;
import com.zackyj.Mmall.utils.DateTimeUtil;
import com.zackyj.Mmall.utils.PropertiesUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.function.ServerResponse;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 订单业务逻辑实现类
 *
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.service.Impl ON 2021/7/31-周六.
 */
@Service
public class OrderServiceImpl implements IOrderService {
    @Resource
    CartMapper cartMapper;
    @Resource
    ProductMapper productMapper;
    @Resource
    OrderMapper orderMapper;
    @Resource
    OrderItemMapper orderItemMapper;
    @Resource
    ShippingMapper shippingMapper;

    /**
     * 创建订单业务逻辑
     *
     * @param Id         用户 ID
     * @param shippingId 收货地址 ID
     */
    @Override
    public CommonResponse createOrder(Integer Id, Integer shippingId) {
        //从购物车获取勾选的商品信息
        List<Cart> cartList = cartMapper.getSelectedByUserId(Id);
        //校验这些商品状态、库存等信息
        List<OrderItem> orderItemList = getCartOrderItem(Id, cartList);
        //计算订单总价
        BigDecimal payment = getOrderTotalPrice(orderItemList);
        //生成订单：组装订单对象
        Order order = this.assembleOrder(Id, shippingId, payment);
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderNo(order.getOrderNo());
        }
        //批量插入orderItem
        orderItemMapper.batchInsert(orderItemList);
        //减少产品库存
        this.reduceProductStock(orderItemList);
        //清空选中的购物车
        this.cleanCart(cartList);
        //返回前端数据：组装
        OrderVO orderVO = assembleOrderVo(order, orderItemList);
        return CommonResponse.success(orderVO);
    }

    /**
     * 将多个orderItem 和 order 对象组装成 OrderVO 对象
     *
     * @param order         order 对象
     * @param orderItemList
     * @return OrderVO 对象
     */
    private OrderVO assembleOrderVo(Order order, List<OrderItem> orderItemList) {
        OrderVO orderVo = new OrderVO();
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPaymentTypeDesc(Constant.PaymentTypeEnum.codeOf(order.getPaymentType()).getValue());
        orderVo.setPostage(order.getPostage());
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(Constant.OrderStatusEnum.codeOf(order.getStatus()).getValue());

        orderVo.setShippingId(order.getShippingId());
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        if (shipping != null) {
            orderVo.setReceiverName(shipping.getReceiverName());
            ShippingVO shippingVO = new ShippingVO();
            BeanUtils.copyProperties(shipping, shippingVO);
            orderVo.setShippingVo(shippingVO);
        }

        //时间戳转换为字符串形式
        orderVo.setPaymentTime(DateTimeUtil.dateToStr(order.getPaymentTime()));
        orderVo.setSendTime(DateTimeUtil.dateToStr(order.getSendTime()));
        orderVo.setEndTime(DateTimeUtil.dateToStr(order.getEndTime()));
        orderVo.setCreateTime(DateTimeUtil.dateToStr(order.getCreateTime()));
        orderVo.setCloseTime(DateTimeUtil.dateToStr(order.getCloseTime()));

        //填充orderItemVoList
        List<OrderItemVO> orderItemVoList = Lists.newArrayList();
        for (OrderItem orderItem : orderItemList) {
            OrderItemVO orderItemVo = assembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }

        orderVo.setOrderItemVoList(orderItemVoList);
        return orderVo;
    }

    /**
     * orderItem 组装成 orderItemVO
     *
     * @param orderItem 要组装的 orderItem对象
     */
    private OrderItemVO assembleOrderItemVo(OrderItem orderItem) {
        OrderItemVO orderItemVo = new OrderItemVO();
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());

        orderItemVo.setCreateTime(DateTimeUtil.dateToStr(orderItem.getCreateTime()));
        return orderItemVo;
    }

    /**
     * 清理加入订单的购物车记录项
     *
     * @param cartList 购物车记录列表
     */
    private void cleanCart(List<Cart> cartList) {
        for (Cart cart : cartList) {
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
    }

    /**
     * 减库存
     *
     * @param orderItemList 订单项列表
     */
    private void reduceProductStock(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList) {
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            product.setStock(product.getStock() - orderItem.getQuantity());
            productMapper.updateByPrimaryKeySelective(product);
        }
    }


    /**
     * 组装订单对象
     *
     * @param userId     用户 ID
     * @param shippingId 收货地址 ID
     * @param payment    支付金额
     * @return Order对象
     */
    private Order assembleOrder(Integer userId, Integer shippingId, BigDecimal payment) {
        Order order = new Order();
        //设置订单号
        long orderNo = this.generateOrderNo();
        //设置订单状态
        order.setOrderNo(orderNo);
        order.setStatus(Constant.OrderStatusEnum.NO_PAY.getCode());
        //设置运费（都包邮）
        order.setPostage(0);
        order.setPaymentType(Constant.PaymentTypeEnum.ONLINE_PAY.getCode());
        order.setPayment(payment);
        order.setUserId(userId);
        order.setShippingId(shippingId);
        int rowCount = orderMapper.insert(order);
        if (rowCount == 0) {
            throw new BusinessException(ExceptionEnum.CREATE_ORDER_FAILED);
        }
        return order;
    }

    /**
     * 生成订单号：规则-
     *
     * @return
     */
    private long generateOrderNo() {
        long currentTime = System.currentTimeMillis();
        return currentTime + new Random().nextInt(100);
    }

    /**
     * 计算订单总价
     *
     * @param orderItemList
     * @return
     */
    private BigDecimal getOrderTotalPrice(List<OrderItem> orderItemList) {
        BigDecimal payment = new BigDecimal("0");
        for (OrderItem orderItem : orderItemList) {
            payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
        }
        return payment;
    }

    private List<OrderItem> getCartOrderItem(Integer userId, List<Cart> cartList) {
        List<OrderItem> orderItemList = Lists.newArrayList();
        //检查已勾选购物车是否为空
        if (CollectionUtils.isEmpty(cartList)) {
            throw new BusinessException(ExceptionEnum.EMPTY_CART);
        }
        //校验已勾选的商品
        for (Cart cart : cartList) {
            OrderItem orderItem = new OrderItem();
            Product product = productMapper.selectByPrimaryKey(cart.getProductId());
            //检查是否上线
            if (Constant.ProductStatusEnum.ON_SALE.getCode() != product.getStatus()) {
                throw new BusinessException(ExceptionEnum.NOT_ON_SALE);
            }
            //校验库存
            if (cart.getQuantity() > product.getStock()) {
                throw new BusinessException(ExceptionEnum.NOT_ON_SALE);
            }
            orderItem.setUserId(userId);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cart.getQuantity()));
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    @Override
    public CommonResponse cancelOrder(Integer userId, Long orderNo) {
        Order order = orderMapper.selectByUserAndOrderNo(userId, orderNo);
        if (order == null) {
            throw new BusinessException(ExceptionEnum.NO_SUCH_ORDER);
        }
        if (order.getStatus() != Constant.OrderStatusEnum.NO_PAY.getCode()) {
            throw new BusinessException(ExceptionEnum.PAID_ORDER_CANCEL);
        }
        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        updateOrder.setStatus(Constant.OrderStatusEnum.CANCELED.getCode());
        int row = orderMapper.updateByPrimaryKeySelective(updateOrder);
        if (row == 0) {
            throw new BusinessException(ExceptionEnum.CANCEL_ORDER_FAILED);
        }
        return CommonResponse.success("订单取消成功");
    }

    @Override
    public CommonResponse getOrderCartProduct(Integer userId) {
        OrderProductVO orderProductVo = new OrderProductVO();
        //查询选中的购物车项列表
        List<Cart> cartList = cartMapper.getSelectedByUserId(userId);

        List<OrderItem> orderItemList = this.getCartOrderItem(userId, cartList);
        List<OrderItemVO> orderItemVoList = Lists.newArrayList();

        BigDecimal payment = new BigDecimal("0");
        for (OrderItem orderItem : orderItemList) {
            payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
            orderItemVoList.add(assembleOrderItemVo(orderItem));
        }
        orderProductVo.setProductTotalPrice(payment);
        orderProductVo.setOrderItemVoList(orderItemVoList);
        return CommonResponse.success(orderProductVo);
    }

    @Override
    public CommonResponse<OrderVO> getOrderDetail(Integer userId, Long orderNo) {
        Order order = orderMapper.selectByUserAndOrderNo(userId, orderNo);
        if (order != null) {
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNoUserId(userId, orderNo);
            OrderVO orderVo = assembleOrderVo(order, orderItemList);
            return CommonResponse.success(orderVo);
        }
        return CommonResponse.error(ExceptionEnum.NO_SUCH_ORDER);
    }

    @Override
    public CommonResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        //通过 userId 获取订单集合
        List<Order> orderList = orderMapper.selectByUserId(userId);
        //组装成 订单VO 集合
        List<OrderVO> orderVoList = assembleOrderVoList(orderList, userId);
        PageInfo pageResult = new PageInfo(orderList);
        pageResult.setList(orderVoList);
        return CommonResponse.success(pageResult);
    }

    @Override
    public CommonResponse<PageInfo> getOrderListForAdmin(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectAllOrder();
        List<OrderVO> orderVOList = this.assembleOrderVoList(orderList, null);
        PageInfo pageResult = new PageInfo(orderList);
        pageResult.setList(orderVOList);
        return CommonResponse.success(pageResult);
    }

    private List<OrderVO> assembleOrderVoList(List<Order> orderList, Integer userId) {
        List<OrderVO> orderVoList = Lists.newArrayList();
        for (Order order : orderList) {
            List<OrderItem> orderItemList;
            if (userId == null) {
                //todo 管理员过来查询的时候 不需要传userId
                orderItemList = orderItemMapper.getByOrderNo(order.getOrderNo());
            } else {
                orderItemList = orderItemMapper.getByOrderNoUserId(userId, order.getOrderNo());
            }
            OrderVO orderVo = assembleOrderVo(order, orderItemList);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }

    @Override
    public CommonResponse getOrderDetailForAdmin(Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order != null) {
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(orderNo);
            OrderVO orderVo = assembleOrderVo(order, orderItemList);
            return CommonResponse.success(orderVo);
        }
        return CommonResponse.error(ExceptionEnum.NO_SUCH_ORDER);
    }

    @Override
    public CommonResponse orderSearch(Long orderNo, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order != null) {
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(orderNo);
            OrderVO orderVo = assembleOrderVo(order, orderItemList);

            PageInfo pageResult = new PageInfo(Lists.newArrayList(order));
            pageResult.setList(Lists.newArrayList(orderVo));
            return CommonResponse.success(orderVo);
        }
        return CommonResponse.error(ExceptionEnum.NO_SUCH_ORDER);
    }

    @Override
    public CommonResponse deliver(Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order != null) {
            if (order.getStatus() == Constant.OrderStatusEnum.PAID.getCode()) {
                order.setStatus(Constant.OrderStatusEnum.SHIPPED.getCode());
                order.setSendTime(new Date());
                orderMapper.updateByPrimaryKeySelective(order);
                return CommonResponse.success("发货成功");
            }
        }
        return CommonResponse.error(ExceptionEnum.NO_SUCH_ORDER);
    }

}
