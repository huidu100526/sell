package com.qcl.controller;

import com.qcl.VO.ResultVO;
import com.qcl.daobean.Picture;
import com.qcl.enums.ResultEnum;
import com.qcl.exception.SellException;
import com.qcl.form.PictureForm;
import com.qcl.repository.PictureRepository;
import com.qcl.utils.ResultVOUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

/**
 * 小程序页面轮播图
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @Autowired
    PictureRepository repository;

    /**
     * 获得所有首页轮播图
     */
    @GetMapping("/getAll")
    public ResultVO getUserInfo() {
        List<Picture> pictures = repository.findAll();
        return ResultVOUtil.success(pictures);
    }

    /**
     * 轮播图列表
     */
    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map) {
        List<Picture> pictures = repository.findAll();
        map.put("categoryList", pictures);
        return new ModelAndView("picture/list", map);
    }

    /**
     * 到修改页面
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "picId", required = false) Integer picId) {
        ModelAndView modelAndView = new ModelAndView();
        Picture picture = repository.findByPicId(picId);
        modelAndView.addObject("category", picture);
        modelAndView.setViewName("picture/index");
        return modelAndView;
    }

    /**
     * 修改/新增轮播图
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid PictureForm form, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        log.info("SellerForm={}", form);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("msg", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            modelAndView.addObject("url", "/sell/picture/index");
            modelAndView.setViewName("common/prompt");
            return modelAndView;
        }
        Picture picture = new Picture();
        try {
            if (form.getPicId() != null) {
                // 如果有id则是修改
                picture = repository.findByPicId(form.getPicId());
            }
            BeanUtils.copyProperties(form, picture);
            repository.save(picture);
        } catch (SellException e) {
            modelAndView.addObject("msg", e.getMessage());
            modelAndView.addObject("url", "/sell/picture/index");
            modelAndView.setViewName("common/prompt");
            return modelAndView;
        }
        modelAndView.addObject("msg", ResultEnum.PICTURE_UPDATE_SUCCESS.getMessage());
        modelAndView.addObject("url", "/sell/picture/list");
        modelAndView.setViewName("common/prompt");
        return modelAndView;
    }

}
