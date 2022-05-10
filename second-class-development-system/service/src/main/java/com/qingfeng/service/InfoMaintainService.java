package com.qingfeng.service;

import com.qingfeng.entity.InfoMaintain;
import com.qingfeng.vo.ResultVO;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/10
 */
public interface InfoMaintainService {

    /**
     * 查询是否开启学生信息维护功能
     * @return
     */
    ResultVO getUserInfoMaintain();

    /**
     * 开启信息维护功能
     * @param infoMaintain
     * @return
     */
    ResultVO addUserInfoMaintain(InfoMaintain infoMaintain);

    /**
     * 根据id信息维护功能
     * @param id
     * @param type
     * @return
     */
    ResultVO updateUserInfoMaintain(Integer id,Integer type);

    /**
     * 查询是否开启校领导/老师信息维护功能
     * @return
     */
    ResultVO getTeacherInfoMaintain();
}
