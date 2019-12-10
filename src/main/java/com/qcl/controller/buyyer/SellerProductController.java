package com.qcl.controller.buyyer;

import com.qcl.daobean.ProductCategory;
import com.qcl.daobean.ProductInfo;
import com.qcl.enums.ResultEnum;
import com.qcl.exception.SellException;
import com.qcl.form.ProductForm;
import com.qcl.service.CategoryService;
import com.qcl.service.ProductService;
import com.qcl.utils.GetKeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 卖家端商品
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 商品列表
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        ModelAndView modelAndView = new ModelAndView();
        PageRequest request = PageRequest.of(page - 1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
        modelAndView.addObject("productInfoPage", productInfoPage);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("size", size);
        modelAndView.setViewName("product/list");
        return modelAndView;
    }

    /**
     * 商品上架
     */
    @RequestMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            productService.onSale(productId);
        } catch (SellException e) {
            modelAndView.addObject("msg", e.getMessage());
            modelAndView.addObject("url", "/sell/seller/product/list");
            modelAndView.setViewName("common/prompt");
            return modelAndView;
        }
        modelAndView.addObject("url", "/sell/seller/product/list");
        modelAndView.addObject("msg", ResultEnum.PRODUCT_UPSALE_SUCCESS.getMessage());
        modelAndView.setViewName("common/prompt");
        return modelAndView;
    }

    /**
     * 商品下架
     */
    @RequestMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            productService.offSale(productId);
        } catch (SellException e) {
            modelAndView.addObject("msg", e.getMessage());
            modelAndView.addObject("url", "/sell/seller/product/list");
            modelAndView.setViewName("common/prompt");
            return modelAndView;
        }
        modelAndView.addObject("url", "/sell/seller/product/list");
        modelAndView.addObject("msg", ResultEnum.PRODUCT_DOWNSALE_SUCCESS.getMessage());
        modelAndView.setViewName("common/prompt");
        return modelAndView;
    }

    /**
     * 到新增商品页面
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId) {
        ModelAndView modelAndView = new ModelAndView();
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productService.findOne(productId);
            modelAndView.addObject("productInfo", productInfo);
        }
        //查询所有的类目
        List<ProductCategory> categoryList = categoryService.findAll();
        modelAndView.addObject("categoryList", categoryList);
        modelAndView.setViewName("product/index");
        return modelAndView;
    }

    /**
     * 新增/修改商品
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm form, BindingResult bindingResult,
                             Map<String, Object> map) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("msg", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            modelAndView.addObject("url", "/sell/seller/product/index");
            modelAndView.setViewName("common/prompt");
            return modelAndView;
        }

        ProductInfo productInfo = new ProductInfo();
        try {
            if (!StringUtils.isEmpty(form.getProductId())) {
                // 如果没有传商品id则为新增方法
                productInfo = productService.findOne(form.getProductId());
            } else {
                form.setProductId(GetKeyUtil.getKey());
            }
            BeanUtils.copyProperties(form, productInfo);
            productService.save(productInfo);
        } catch (SellException e) {
            modelAndView.addObject("msg", e.getMessage());
            modelAndView.addObject("url", "/sell/seller/product/index");
            modelAndView.setViewName("common/prompt");
            return modelAndView;
        }
        modelAndView.addObject("msg", ResultEnum.PRODUCT_UPDATE_SUCCESS.getMessage());
        modelAndView.addObject("url", "/sell/seller/product/list");
        modelAndView.setViewName("common/prompt");
        return modelAndView;
    }
}
