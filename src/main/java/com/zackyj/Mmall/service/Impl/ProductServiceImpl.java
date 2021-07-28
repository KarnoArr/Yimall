package com.zackyj.Mmall.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zackyj.Mmall.common.CommonResponse;
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
import java.util.ArrayList;
import java.util.List;

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
        return CommonResponse.success("保存商品成功");
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

    @Override
    public CommonResponse<ProductDetailVO> managerProductDetail(Integer productId) {
        if (productId == null) throw new BusinessException(ExceptionEnum.WRONG_ARG);
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) throw new BusinessException(ExceptionEnum.NO_PDT_INFO);
        //组装 VO 对象
        ProductDetailVO productDetailVO = new ProductDetailVO();
        BeanUtils.copyProperties(product, productDetailVO);
        productDetailVO.setImageHostPrefix(PropertiesUtil.getProperty("ftp.server.http.prefix", "defaultImageHostPrefix"));
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        productDetailVO.setParentCategoryId(category.getParentId());

        productDetailVO.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVO.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return CommonResponse.success(productDetailVO);
    }

    @Override
    public CommonResponse getProductListForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectList();
        List<ProductListVO> productListVOList = new ArrayList<>();
        for (Product product : productList) {
            ProductListVO productListVO = new ProductListVO();
            BeanUtils.copyProperties(product, productListVO);
            productListVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.xxxxx.com/"));
            productListVOList.add(productListVO);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVOList);
        return CommonResponse.success(pageResult);
    }
}
