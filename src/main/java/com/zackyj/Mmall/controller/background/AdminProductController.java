package com.zackyj.Mmall.controller.background;

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.model.pojo.Product;
import com.zackyj.Mmall.service.IProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.controller.background ON 2021/7/27-周二.
 */
@RestController
@RequestMapping("/admin/product")
public class AdminProductController {
    @Resource
    IProductService productService;

    /**
     * 保存产品信息，包含新增和修改
     *
     * @param product
     * @return
     */
    @PostMapping("/save")
    public CommonResponse saveProduct(Product product) {
        return productService.addOrUpdateProduct(product);
    }
}
