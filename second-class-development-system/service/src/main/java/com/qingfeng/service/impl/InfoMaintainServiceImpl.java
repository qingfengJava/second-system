package com.qingfeng.service.impl;

import com.qingfeng.constant.InfoMaintainStatus;
import com.qingfeng.constant.ResStatus;
import com.qingfeng.dao.InfoMaintainMapper;
import com.qingfeng.dao.TeacherInfoMapper;
import com.qingfeng.dao.UserInfoMapper;
import com.qingfeng.entity.InfoMaintain;
import com.qingfeng.entity.TeacherInfo;
import com.qingfeng.entity.UserInfo;
import com.qingfeng.service.InfoMaintainService;
import com.qingfeng.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/10
 */
@Service
public class InfoMaintainServiceImpl implements InfoMaintainService {

    @Autowired
    private InfoMaintainMapper infoMaintainMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private TeacherInfoMapper teacherInfoMapper;

    /**
     * 查询是否开启学生信息维护功能
     *
     * @return
     */
    @Override
    public ResultVO getUserInfoMaintain() {
        Example example = new Example(InfoMaintain.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("objectType", InfoMaintainStatus.STU_TYPE);
        criteria.andEqualTo("isEnd", 0);
        InfoMaintain infoMaintain = infoMaintainMapper.selectOneByExample(example);
        return new ResultVO(ResStatus.OK, "success", infoMaintain);
    }

    @Override
    @Transactional
    public ResultVO addUserInfoMaintain(InfoMaintain infoMaintain) {
        infoMaintain.setIsEnd(0);
        int i = infoMaintainMapper.insertUseGeneratedKeys(infoMaintain);
        if (i > 0) {
            //TODO 解决定时问题   后序使用MQ解决
            if (infoMaintain.getObjectType().equals(InfoMaintainStatus.STU_TYPE)) {
                //将学生信息维护功能开启
                Example example = new Example(UserInfo.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("isDelete", 0);
                UserInfo userInfo = new UserInfo();
                userInfo.setIsChange(1);
                userInfoMapper.updateByExampleSelective(userInfo, example);
            }else if (infoMaintain.getObjectType().equals(InfoMaintainStatus.ADMIN_TYPE)) {
                //将教师信息维护功能开启
                Example example = new Example(TeacherInfo.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("isDelete", 0);
                TeacherInfo teacherInfo = new TeacherInfo();
                teacherInfo.setIsChange(1);
                teacherInfoMapper.updateByExampleSelective(teacherInfo, example);
            }
            return new ResultVO(ResStatus.OK, "success", null);

        }
        return new ResultVO(ResStatus.NO, "开启信息维护功能失败", null);
    }

    @Override
    public ResultVO updateUserInfoMaintain(Integer id, Integer type) {
        InfoMaintain infoMaintain = new InfoMaintain();
        infoMaintain.setId(id);
        infoMaintain.setIsEnd(1);
        int i = infoMaintainMapper.updateByPrimaryKeySelective(infoMaintain);
        if (i > 0) {
            //TODO 解决定时问题   后序使用MQ解决
            if (type.equals(InfoMaintainStatus.STU_TYPE)) {
                //一旦关闭学生信息维护功能，则将学生信息维护功能关闭
                Example example = new Example(UserInfo.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("isDelete", 0);
                UserInfo userInfo = new UserInfo();
                userInfo.setIsChange(0);
                userInfoMapper.updateByExampleSelective(userInfo, example);
            } else if (type.equals(InfoMaintainStatus.ADMIN_TYPE)) {
                Example example = new Example(TeacherInfo.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("isDelete", 0);
                TeacherInfo teacherInfo = new TeacherInfo();
                teacherInfo.setIsChange(0);
                teacherInfoMapper.updateByExampleSelective(teacherInfo, example);
            }
            return new ResultVO(ResStatus.OK, "success", null);
        }
        return new ResultVO(ResStatus.NO, "关闭信息维护功能失败", null);
    }

    @Override
    public ResultVO getTeacherInfoMaintain() {
        Example example = new Example(InfoMaintain.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("objectType", InfoMaintainStatus.ADMIN_TYPE);
        criteria.andEqualTo("isEnd", 0);
        InfoMaintain infoMaintain = infoMaintainMapper.selectOneByExample(example);
        return new ResultVO(ResStatus.OK, "success", infoMaintain);
    }

}
