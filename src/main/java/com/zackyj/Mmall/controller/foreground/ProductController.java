package com.zackyj.Mmall.controller.foreground;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.controller.foreground ON 2021/7/28-周三.
 */

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.model.vo.ProductDetailVO;
import com.zackyj.Mmall.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *
 */
@Api(tags = "前台商品模块")
@RestController
@RequestMapping("/product/")
public class ProductController {
    @Resource
    IProductService productService;

    @ApiOperation(value = "获取商品详情")
    @GetMapping("/detail")
    public CommonResponse<ProductDetailVO> getDetail(Integer productId) {
        return productService.getProductDetail(productId);
    }

    @ApiOperation(value = "获取商品列表（搜索排序）")
    @GetMapping("/list")
    public CommonResponse list(@RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "categoryId", required = false) Integer categoryId,
                               @RequestParam(value = "sortBy", required = false) String sortBy,
                               @RequestParam(value = "sortOrder", required = false) String sortOrder,
                               @RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
                               @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        return productService.getListForUser(categoryId, keyword, sortBy, sortOrder, pageNumber, pageSize);
    }
}
