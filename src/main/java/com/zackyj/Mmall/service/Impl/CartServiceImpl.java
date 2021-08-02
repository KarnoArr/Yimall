package com.zackyj.Mmall.service.Impl;


import com.google.common.base.Splitter;
import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.common.Constant;
import com.zackyj.Mmall.common.Exception.ExceptionEnum;
import com.zackyj.Mmall.dao.CartMapper;
import com.zackyj.Mmall.dao.ProductMapper;
import com.zackyj.Mmall.model.pojo.Cart;
import com.zackyj.Mmall.model.pojo.Product;
import com.zackyj.Mmall.model.vo.CartProductVO;
import com.zackyj.Mmall.model.vo.CartVO;
import com.zackyj.Mmall.service.ICartService;
import com.zackyj.Mmall.utils.BigDecimalUtil;
import com.zackyj.Mmall.utils.PropertiesUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.service.Impl ON 2021/7/29-周四.
 */
@Service
public class CartServiceImpl implements ICartService {
    @Resource
    CartMapper cartMapper;
    @Resource
    ProductMapper productMapper;

    @Override
    public CommonResponse list(Integer userId) {
        CartVO cartVo = this.getCartVO(userId);
        return CommonResponse.success(cartVo);
    }

    @Override
    public CommonResponse<CartVO> add(Integer userId, Integer count, Integer productId) {
        //查询当前用户是否有这件商品的购物车记录
        Cart cart = cartMapper.selectByUserAndPdtId(userId, productId);
        if (cart == null) {
            //没有商品，新增购物车记录
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            newCart.setProductId(productId);
            newCart.setQuantity(count);
            newCart.setChecked(Constant.Cart.CHECKED);
            cartMapper.insert(newCart);
        } else {
            //有这件商品，新增数量
            count = count + cart.getQuantity();
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        CartVO cartVO = this.getCartVO(userId);
        return CommonResponse.success(cartVO);
    }

    @Override
    public CommonResponse update(Integer userId, Integer productId, Integer count) {
        //参数校验
        Cart cart = cartMapper.selectByUserAndPdtId(userId, productId);
        if (cart != null) {
            cart.setQuantity(count);
        }
        cartMapper.updateByPrimaryKeySelective(cart);
        CartVO cartVO = this.getCartVO(userId);
        return CommonResponse.success(cartVO);
    }

    @Override
    public CommonResponse delete(Integer userId, String productId) {
        List<String> productIdList = Splitter.on(",").splitToList(productId);
        if (CollectionUtils.isEmpty(productIdList)) {
            return CommonResponse.error(ExceptionEnum.WRONG_ARG);
        }
        cartMapper.deleteByUserAndPdtId(userId, productIdList);
        return this.list(userId);
    }

    @Override
    public CommonResponse getCount(Integer userId) {
        if (userId == null) return CommonResponse.success(0);
        return CommonResponse.success(cartMapper.getAllCount(userId));
    }

    @Override
    public CommonResponse changeCheckedStatus(Integer userId, Integer productId, int checked) {
        cartMapper.changeCheckedStatus(userId, productId, checked);
        return this.list(userId);
    }

    //获取用户的CartVO,进行库存判断，信息组装等操作
    private CartVO getCartVO(Integer userId) {
        CartVO cartVO = new CartVO();
        //查询用户购物车记录
        List<Cart> cartList = cartMapper.selectListByUserId(userId);
        //组装cartProductVOList
        List<CartProductVO> cartProductVOList = new ArrayList<>();
        BigDecimal totalPrice = new BigDecimal("0");
        if (CollectionUtils.isNotEmpty(cartList)) {
            //如果用户购物车有记录，就遍历这个记录并组装 cartProductVO
            for (Cart cartItem : cartList) {
                CartProductVO cartProductVO = new CartProductVO();
                cartProductVO.setId(cartItem.getId());
                cartProductVO.setUserId(userId);
                cartProductVO.setProductId(cartItem.getProductId());
                //组装商品信息
                //查询每一项商品的信息
                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if (product != null) {
                    //属性拷贝
                    //BeanUtils.copyProperties(product,cartProductVO);
                    cartProductVO.setProductMainImage(product.getMainImage());
                    cartProductVO.setProductName(product.getName());
                    cartProductVO.setProductSubtitle(product.getSubtitle());
                    cartProductVO.setProductStatus(product.getStatus());
                    cartProductVO.setProductPrice(product.getPrice());
                    cartProductVO.setProductStock(product.getStock());
                    //判断库存
                    //最大限制的购买数量
                    int buyLimitCount = 0;
                    if (product.getStock() >= cartItem.getQuantity()) {
                        //库存充足的时候
                        buyLimitCount = cartItem.getQuantity();
                        cartProductVO.setLimitQuantity(Constant.Cart.LIMIT_NUM_SUCCESS);
                    } else {
                        //库存不足，把最大值限制为商品库存
                        buyLimitCount = product.getStock();
                        cartProductVO.setLimitQuantity(Constant.Cart.LIMIT_NUM_FAIL);
                        //购物车中更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cartItem.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartProductVO.setQuantity(buyLimitCount);
                    //计算总价
                    cartProductVO.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVO.getQuantity()));
                    cartProductVO.setProductChecked(cartItem.getChecked());
                }
                if (cartItem.getChecked() == Constant.Cart.CHECKED) {
                    //如果已经勾选,增加到整个的购物车总价中
                    totalPrice = BigDecimalUtil.add(totalPrice.doubleValue(), cartProductVO.getProductTotalPrice().doubleValue());
                }
                cartProductVOList.add(cartProductVO);
            }
        }
        cartVO.setCartTotalPrice(totalPrice);
        cartVO.setCartProductList(cartProductVOList);
        cartVO.setIsAllChecked(this.isAllChecked(userId));
        //cartVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return cartVO;
    }

    private boolean isAllChecked(Integer userId) {
        if (userId == null) return false;
        return cartMapper.selectAllChecked(userId) == 0;
    }
}
