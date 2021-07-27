package com.zackyj.Mmall.service.Impl;

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.common.Exception.BusinessException;
import com.zackyj.Mmall.common.Exception.ExceptionEnum;
import com.zackyj.Mmall.dao.ProductMapper;
import com.zackyj.Mmall.model.pojo.Product;
import com.zackyj.Mmall.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.service.Impl ON 2021/7/27-周二.
 */
@Service
public class ProductServiceImpl implements IProductService {
    @Resource
    ProductMapper productMapper;

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
        return CommonResponse.success("保存产品成功");
    }
}
