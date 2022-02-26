package com.qingfeng.service;

import com.qingfeng.entity.Regist;
import com.qingfeng.vo.ResultVO;

/**
 * 活动报名的拉业务层接口
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/2/26
 */
public interface RegistService {

    /**
     * 活动报名
     * @param applyId
     * @param regist
     * @return
     */
    ResultVO registrationActive(Integer applyId, Regist regist);

    /**
     * 审核报名是否成功（主要审核参与者身份是否正确）
     * @param activeRegId
     * @param status
     * @return
     */
    ResultVO checkIsSuccess(Integer activeRegId, Integer status);

    /**
     * 根据报名表的主键Id删除学生的报名信息
     * @param activeRegId
     * @return
     */
    ResultVO deleteRegistration(Integer activeRegId);
}
