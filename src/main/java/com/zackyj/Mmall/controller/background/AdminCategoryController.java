package com.zackyj.Mmall.controller.background;

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.service.ICategoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("addCategory")
    public CommonResponse addCategory(String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        return categoryService.addCategory(categoryName, parentId);
    }

    @PostMapping("updateName")
    public CommonResponse updateName(Integer categoryId, String categoryName) {
        return categoryService.updateName(categoryId, categoryName);
    }

    @PostMapping("updateParent")
    public CommonResponse updateName(Integer categoryId, Integer parentId) {
        return categoryService.updateParent(categoryId, parentId);
    }
}
