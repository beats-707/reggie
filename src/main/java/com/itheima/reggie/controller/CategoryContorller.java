package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryContorller {

    @Autowired
    private CategoryService categoryService;

    /**
     * add category
     *
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {

        log.info("新赠的分类：{}", category);
        categoryService.save(category);
        return R.success("新增成功");
    }

    /**
     * paging view
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        //新建分页constructor
        Page<Category> cPage = new Page<>();
        //condition constructor
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(Category::getSort);

        categoryService.page(cPage, lambdaQueryWrapper);
        return R.success(cPage);
    }

    /**
     * delete category
     *
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long id) {

        log.info("remove category {}", id);
//        categoryService.removeById(id);
        categoryService.remove(id);
        return R.success("delete success");
    }

    @PutMapping
    public R<String> upadate(@RequestBody Category category) {
        log.info("修改分类信息：{}", category);
        categoryService.updateById(category);
        return R.success("修改分类成功");
    }


    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        // condition constructor
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // add condition
        lambdaQueryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        //Add sort condition
        lambdaQueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(lambdaQueryWrapper);
        return R.success(list);
    }
}
