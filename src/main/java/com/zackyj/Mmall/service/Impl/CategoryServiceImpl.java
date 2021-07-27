package com.zackyj.Mmall.service.Impl;

import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.common.Exception.BusinessException;
import com.zackyj.Mmall.common.Exception.ExceptionEnum;
import com.zackyj.Mmall.dao.CategoryMapper;
import com.zackyj.Mmall.model.pojo.Category;
import com.zackyj.Mmall.model.vo.CategoryVO;
import com.zackyj.Mmall.service.ICategoryService;
import javafx.scene.chart.CategoryAxis;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.ServerResponse;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.service.Impl ON 2021/7/27-周二.
 */
@Slf4j
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Resource
    CategoryMapper categoryMapper;

    @Override
    public CommonResponse addCategory(String categoryName, Integer parentId) {
        //参数校验
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            throw new BusinessException(ExceptionEnum.WRONG_CATEGORY_ARGS);
        }
        //检查分类名是否重名
        int count = categoryMapper.checkCategoryExist(categoryName);
        if (count > 0) {
            throw new BusinessException(ExceptionEnum.CATEGORY_NAME_EXIST);
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);

        int rowCount = categoryMapper.insert(category);
        if (rowCount == 0) {
            throw new BusinessException(ExceptionEnum.ADD_CATEGORY_FAILED);
        }
        return CommonResponse.success("添加品类成功");
    }

    @Override
    public CommonResponse updateName(Integer categoryId, String categoryName) {
        //参数校验
        if (categoryId == null || StringUtils.isBlank(categoryName)) {
            throw new BusinessException(ExceptionEnum.WRONG_ARG);
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (rowCount == 0) {
            throw new BusinessException(ExceptionEnum.UPDATE_CATEGORY_FAILED);
        }
        return CommonResponse.success("更新分类信息成功");
    }

    @Override
    public CommonResponse<List<Category>> getChildrenParallel(Integer categoryId) {
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)) {
            log.info("未找到ID为" + categoryId + "分类的子分类");
        }
        return CommonResponse.success(categoryList);
    }

    @Override
    public CommonResponse<List<CategoryVO>> getRecursiveCategory(Integer categoryId) {
        ArrayList<CategoryVO> voList = new ArrayList<>();
        findDeepCategory(voList, categoryId);
        return CommonResponse.success(voList);
    }

    private void findDeepCategory(List<CategoryVO> voList, Integer parentId) {
        //查找以传入id为父id的分类列表
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(parentId);
        //递归停止条件：直到没有以自己为parentId的categoryList
        if (!CollectionUtils.isEmpty(categoryList)) {
            for (int i = 0; i < categoryList.size(); i++) {
                Category category = categoryList.get(i);
                CategoryVO categoryVO = new CategoryVO();
                BeanUtils.copyProperties(category, categoryVO);
                voList.add(categoryVO);
                findDeepCategory(categoryVO.getChildCategory(), categoryVO.getId());
            }
        }
    }

    @Override
    public CommonResponse discard(Integer categoryId, Integer status) {
        int count = categoryMapper.discard(categoryId, status);
        return null;
    }
}
