package com.qingfeng.service;

import com.qingfeng.entity.Check;
import com.qingfeng.vo.ResultVO;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/6/7
 */
public interface ActiveCheckService {

    /**
     * 根据活动Id查询活动审查的详情信息
     * @param activeId
     * @return
     */
    ResultVO selectActiveDetailsById(Integer activeId);

    /**
     * 上传活动图片
     * @param applyId
     * @param newFileName
     */
    void uploadActiveImg(Integer applyId, String newFileName);

    /**
     * 根据活动Id指定删除上传的图片
     * @param applyId
     * @param fileName
     * @return
     */
    ResultVO deleteActiveImg(Integer applyId, String fileName);

    /**
     * 提交活动待审核的内容
     * @param check
     * @return
     */
    ResultVO submitCheck(Check check);
}
