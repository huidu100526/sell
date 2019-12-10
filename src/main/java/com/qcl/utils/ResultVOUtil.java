package com.qcl.utils;

import com.qcl.VO.ResultVO;

/**
 * controller返回结果统一
 */
public class ResultVOUtil {
    /**
     * 返回成功方法
     */
    public static ResultVO<Object> success(Object object) {
        ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setMessage("成功");
        resultVO.setData(object);
        return resultVO;
    }

    /**
     * 空对象的返回成功方法
     */
    public static ResultVO success() {
        return success(null);
    }

    /**
     * 返回错误信息方法
     */
    public static ResultVO error(Integer code, String message) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(message);
        return resultVO;
    }
}
