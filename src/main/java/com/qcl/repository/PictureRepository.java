package com.qcl.repository;

import com.qcl.daobean.Picture;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Integer> {
    /**
     * 根据轮播图id查询图片信息
     */
    Picture findByPicId(Integer picId);
}
