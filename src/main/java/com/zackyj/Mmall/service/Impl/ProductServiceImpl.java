package com.zackyj.Mmall.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.common.Constant;
import com.zackyj.Mmall.common.Exception.BusinessException;
import com.zackyj.Mmall.common.Exception.ExceptionEnum;
import com.zackyj.Mmall.dao.CategoryMapper;
import com.zackyj.Mmall.dao.ProductMapper;
import com.zackyj.Mmall.model.pojo.Category;
import com.zackyj.Mmall.model.pojo.Product;
import com.zackyj.Mmall.model.vo.ProductDetailVO;
import com.zackyj.Mmall.model.vo.ProductListVO;
import com.zackyj.Mmall.service.IProductService;
import com.zackyj.Mmall.utils.DateTimeUtil;
import com.zackyj.Mmall.utils.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.service.Impl ON 2021/7/27-周二.
 */
@Service
public class ProductServiceImpl implements IProductService {
    @Resource
    ProductMapper productMapper;
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public CommonResponse addOrUpdateProduct(Product product) {
        if (product != null) {
            product.setStatus(1);
            if (StringUtils.isNotBlank(product.getSubImages())) {
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]);
                }
            }

            if (product.getId() != null) {
                int rowCount = productMapper.updateByPrimaryKey(product);
                if (rowCount == 0) {
                    throw new BusinessException(ExceptionEnum.UPDATE_PDT_FAILED);
                }
            } else {
                int rowCount = productMapper.insert(product);
                if (rowCount == 0) {
                    throw new BusinessException(ExceptionEnum.INSERT_PDT_FAILED);
                }
            }
        }
        return CommonResponse.success("保存商品更改成功");
    }

    @Override
    public CommonResponse setSaleStatus(Integer productId, Integer status) {
        //参数校验
        if (productId == null || status == null) {
            throw new BusinessException(ExceptionEnum.WRONG_ARG);
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if (rowCount == 0) {
            throw new BusinessException(ExceptionEnum.SET_STATUS_FAILED);
        }
        return CommonResponse.success("修改商品状态成功");
    }

    private ProductDetailVO assembleProductDetailVO(Product product) {
        ProductDetailVO productDetailVO = new ProductDetailVO();
        BeanUtils.copyProperties(product, productDetailVO);
        //productDetailVO.setImageHostPrefix(PropertiesUtil.getProperty("ftp.server.http.prefix", "defaultImageHostPrefix"));
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        productDetailVO.setParentCategoryId(category.getParentId());

        productDetailVO.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVO.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVO;
    }

    @Override
    public CommonResponse<ProductDetailVO> getProductDetailForAdmin(Integer productId) {
        if (productId == null) throw new BusinessException(ExceptionEnum.WRONG_ARG);
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) throw new BusinessException(ExceptionEnum.NO_PDT_INFO);
        //组装 VO 对象
        ProductDetailVO productDetailVO = assembleProductDetailVO(product);
        return CommonResponse.success(productDetailVO);
    }

    @Override
    public CommonResponse getProductDetail(Integer productId) {
        if (productId == null) throw new BusinessException(ExceptionEnum.WRONG_ARG);
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) throw new BusinessException(ExceptionEnum.NO_PDT_INFO);
        if (product.getStatus() != Constant.ProductStatusEnum.ON_SALE.getCode()) {
            throw new BusinessException(ExceptionEnum.NO_PDT_INFO);
        }
        //组装 VO 对象
        ProductDetailVO productDetailVO = assembleProductDetailVO(product);
        return CommonResponse.success(productDetailVO);
    }

    private List<ProductListVO> assembleProductListVO(List<Product> productList) {
        List<ProductListVO> productListVOList = new ArrayList<>();
        for (Product product : productList) {
            ProductListVO productListVO = new ProductListVO();
            BeanUtils.copyProperties(product, productListVO);
            //productListVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.xxxxx.com/"));
            productListVOList.add(productListVO);
        }
        return productListVOList;
    }

    @Override
    public CommonResponse getProductListForAdmin(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = null;
        if (StringUtils.isNumeric(keyword)) {
            productList = Collections.singletonList(productMapper.selectByPrimaryKey(Integer.valueOf(keyword)));
        } else {
            productList = productMapper.selectList(keyword);
        }
        List<ProductListVO> productListVOList = assembleProductListVO(productList);
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVOList);
        return CommonResponse.success(pageResult);
    }

    @Override
    public CommonResponse getListForUser(Integer categoryId, String keyword, String sortBy, String sortOrder, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.getListForUser(categoryId, keyword, sortBy, sortOrder);
        if (StringUtils.isNotBlank(sortBy) && sortBy.equals("price")) {
            if (sortOrder.equals("desc")) {
                productList.sort(Comparator.comparing(Product::getPrice).reversed());
            } else {
                productList.sort(Comparator.comparing(Product::getPrice));
            }
        }
        List<ProductListVO> productListVOList = assembleProductListVO(productList);
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVOList);
        return CommonResponse.success(pageResult);
    }
}
