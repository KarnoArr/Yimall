package com.zackyj.Mmall.controller.background;

import com.google.common.collect.Maps;
import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.model.pojo.Product;
import com.zackyj.Mmall.service.IFileService;
import com.zackyj.Mmall.service.IProductService;
import com.zackyj.Mmall.utils.PropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
    @Resource
    IFileService fileService;

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
    @ApiOperation(value = "保存商品信息")
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

    @ApiOperation("文件上传接口")
    @PostMapping("/uploadFile")
    public CommonResponse uploadFile(MultipartFile file, @ApiIgnore HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = fileService.upload(file, path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

        Map<String, String> fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);
        return CommonResponse.success(fileMap);
    }
}
