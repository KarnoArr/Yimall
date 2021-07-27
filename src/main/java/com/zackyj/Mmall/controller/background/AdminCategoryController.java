package com.zackyj.Mmall.controller.background;

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.service.ICategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.controller.background ON 2021/7/27-周二.
 */
@RestController
@RequestMapping("/admin/category")
public class AdminCategoryController {
    @Resource
    ICategoryService categoryService;

    @PostMapping("add")
    public CommonResponse addCategory(String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        return categoryService.addCategory(categoryName, parentId);
    }

    @PostMapping("update")
    public CommonResponse updateName(Integer categoryId, String categoryName) {
        return categoryService.updateName(categoryId, categoryName);
    }

    @PostMapping("discard")
    public CommonResponse discard(Integer categoryId, Integer status) {
        return categoryService.discard(categoryId, status);
    }

    /**
     * 获取传入分类 ID 下第一级的子分类
     * 不传则默认为 0，返回最高级分类
     *
     * @param categoryId
     * @return
     */
    @GetMapping("childListLevel")
    public CommonResponse getChildrenParallel(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        return categoryService.getChildrenParallel(categoryId);
    }


}
