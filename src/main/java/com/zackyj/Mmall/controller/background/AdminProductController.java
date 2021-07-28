package com.zackyj.Mmall.controller.background;

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.model.pojo.Product;
import com.zackyj.Mmall.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.controller.background ON 2021/7/27-周二.
 */
@RestController
@Api(tags = "后台商品模块")
@RequestMapping("/admin/product")
public class AdminProductController {
    @Resource
    IProductService productService;

    @ApiOperation(value = "获取商品列表")
    @ApiParam(name = "keyword", value = "关键字,可以传商品名关键字（非纯数字）或商品ID", required = false)
    @GetMapping("/list")
    public CommonResponse getList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String keyword) {
        return productService.getProductListForAdmin(pageNum, pageSize, keyword);
    }

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

    /**
     * 修改商品上下架状态
     *
     * @param productId
     * @param status
     * @return
     */
    @PostMapping("/setStatus")
    public CommonResponse setStatus(Integer productId, Integer status) {
        return productService.setSaleStatus(productId, status);
    }

    @PostMapping("/detail")
    public CommonResponse getDetail(Integer productId) {
        return productService.managerProductDetail(productId);
    }
}
