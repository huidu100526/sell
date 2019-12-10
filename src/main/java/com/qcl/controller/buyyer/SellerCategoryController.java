package com.qcl.controller.buyyer;

import com.qcl.daobean.ProductCategory;
import com.qcl.enums.ResultEnum;
import com.qcl.exception.SellException;
import com.qcl.form.CategoryForm;
import com.qcl.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 卖家类目
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 类目列表
     */
    @GetMapping("/list")
    public ModelAndView list(ModelAndView modelAndView) {
        List<ProductCategory> categoryList = categoryService.findAll();
        modelAndView.addObject("categoryList", categoryList);
        modelAndView.setViewName("category/list");
        return modelAndView;
    }

    /**
     * 到新增类目页面
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId) {
        ModelAndView modelAndView = new ModelAndView();
        if (categoryId != null) {
            ProductCategory productCategory = categoryService.findOne(categoryId);
            modelAndView.addObject("category", productCategory);
        }
        modelAndView.setViewName("category/index");
        return modelAndView;
    }

    /**
     * 新增/修改类目
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm form, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        // 校验表单信息
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("msg", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            modelAndView.addObject("url", "/sell/seller/category/index");
            modelAndView.setViewName("common/prompt");
            return modelAndView;
        }

        ProductCategory productCategory = new ProductCategory();
        try {
            if (form.getCategoryId() != null) {
                productCategory = categoryService.findOne(form.getCategoryId());
            }
            BeanUtils.copyProperties(form, productCategory);
            categoryService.save(productCategory);
        } catch (SellException e) {
            modelAndView.addObject("msg", e.getMessage());
            modelAndView.addObject("url", "/sell/seller/category/index");
            modelAndView.setViewName("common/prompt");
            return modelAndView;
        }
        modelAndView.addObject("msg", ResultEnum.PRODUCT_CATEGORY_SUCCESS.getMessage());
        modelAndView.addObject("url", "/sell/seller/category/list");
        modelAndView.setViewName("common/prompt");
        return modelAndView;
    }
}
