package com.zackyj.Mmall.controller.foreground;

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.model.vo.CategoryVO;
import com.zackyj.Mmall.service.ICategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.controller.foreground ON 2021/7/27-周二.
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    ICategoryService categoryService;

    @GetMapping("/list")
    public CommonResponse getCategoryList(@RequestParam(value = "category", defaultValue = "0") Integer categoryId) {
        return categoryService.getRecursiveCategory(categoryId);
    }
}
