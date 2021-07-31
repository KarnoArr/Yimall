package com.zackyj.Mmall.controller.background;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.service.ICategoryService;
import io.swagger.annotations.*;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.service.Tags;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.controller.background ON 2021/7/27-周二.
 */
@Api(tags = "后台分类管理模块")
@RestController
@RequestMapping("/admin/category")
public class AdminCategoryController {
    @Resource
    ICategoryService categoryService;

    @ApiOperation(value = "增加分类")
    @ApiOperationSupport(order = 201)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryName", value = "分类名", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "parentId", value = "父分类ID", dataType = "Int", paramType = "query", defaultValue = "0")
    }
    )
    @PostMapping("add")
    public CommonResponse addCategory(String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        return categoryService.addCategory(categoryName, parentId);
    }

    @ApiOperation(value = "更新分类名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "分类ID", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "categoryName", value = "新的分类名", dataType = "String", paramType = "query", required = true)
    })
    @ApiOperationSupport(order = 203)
    @PostMapping("update")
    public CommonResponse updateName(Integer categoryId, String categoryName) {
        return categoryService.updateName(categoryId, categoryName);
    }

    @ApiOperation(value = "设置分类状态", notes = "1-启用,2-弃用")
    @ApiOperationSupport(order = 202)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "分类ID", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "Integer", paramType = "query", required = true)
    })
    @PostMapping("discard")
    public CommonResponse discard(Integer categoryId, Integer status) {
        return categoryService.discard(categoryId, status);
    }

    @ApiOperation(value = "获取子分类", notes = "传入分类 ID 下第一级的子分类，不传则默认为 0，返回最高级分类")
    @ApiOperationSupport(order = 204)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "分类ID", dataType = "Integer", paramType = "query"),
    })
    @GetMapping("childListLevel")
    public CommonResponse getChildrenParallel(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        return categoryService.getChildrenParallel(categoryId);
    }


}
