package com.qingfeng.cms.biz.college.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.college.dao.CollegeInformationDao;
import com.qingfeng.cms.biz.college.enums.CollegeInformationExceptionMsg;
import com.qingfeng.cms.biz.college.service.CollegeInformationService;
import com.qingfeng.cms.biz.student.service.StuInfoService;
import com.qingfeng.cms.domain.college.dto.CollegeInformationSaveDTO;
import com.qingfeng.cms.domain.college.dto.CollegeInformationUpdateDTO;
import com.qingfeng.cms.domain.college.entity.CollegeInformationEntity;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.common.enums.RoleEnum;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import com.qingfeng.sdk.auth.role.RoleApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 院级信息（包含班级），数据字典
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Service
public class CollegeInformationServiceImpl extends ServiceImpl<CollegeInformationDao, CollegeInformationEntity> implements CollegeInformationService {

    @Autowired
    private RoleApi roleApi;
    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private StuInfoService stuInfoService;

    /**
     * 保存二级学院用户关联的院系信息
     *
     * @param collegeInformationSaveDTO
     * @param userId
     */
    @Override
    public void saveCollegeInformation(CollegeInformationSaveDTO collegeInformationSaveDTO, Long userId) {
        // TODO 要进行修改  不能只是二级学院

        //校验用户信息是不是属于二级学院
        R<List<Long>> userIdByCode = roleApi.findUserIdByCode(new String[]{RoleEnum.YUAN_LEVEL_LEADER.name()});
        if (userIdByCode.getData().contains(collegeInformationSaveDTO.getUserId()) &&
                collegeInformationSaveDTO.getUserId().equals(userId)) {
            //说明用户身份符合要求，可以进行存储
            baseMapper.insert(dozerUtils.map2(collegeInformationSaveDTO, CollegeInformationEntity.class));
        } else {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), CollegeInformationExceptionMsg.ILLEGAL_USER.getMsg());
        }
    }

    /**
     * 根据用户Id查询用户关联的二级学院的信息
     *
     * @param userId
     * @return
     */
    @Override
    public CollegeInformationEntity getByUserId(Long userId) {
        return baseMapper.selectOne(Wraps.lbQ(new CollegeInformationEntity())
                .eq(CollegeInformationEntity::getUserId, userId));
    }

    /**
     * 修改用户关联的二级学院的信息
     *
     * @param collegeInformationUpdateDTO
     * @param userId
     */
    @Override
    public void updateCollegeInformationById(CollegeInformationUpdateDTO collegeInformationUpdateDTO, Long userId) {
        R<List<Long>> userIdByCode = roleApi.findUserIdByCode(new String[]{RoleEnum.YUAN_LEVEL_LEADER.name()});
        if (userIdByCode.getData().contains(collegeInformationUpdateDTO.getUserId()) &&
                collegeInformationUpdateDTO.getUserId().equals(userId)) {
            //说明用户身份符合要求，可以进行存储
            baseMapper.updateById(dozerUtils.map2(collegeInformationUpdateDTO, CollegeInformationEntity.class));
        } else {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), CollegeInformationExceptionMsg.ILLEGAL_USER.getMsg());
        }
    }

    /**
     * 查询二级学院下的学生信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<StuInfoEntity> getUserInfoList(Long userId) {
        CollegeInformationEntity collegeInformationEntity = baseMapper.selectOne(Wraps.lbQ(new CollegeInformationEntity())
                .eq(CollegeInformationEntity::getUserId, userId)
        );
        if (ObjectUtil.isEmpty(collegeInformationEntity)) {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), CollegeInformationExceptionMsg.IMPROVE_COLLEGE_INFORMATION.getMsg());
        }

        return stuInfoService.list(Wraps.lbQ(new StuInfoEntity())
                .eq(StuInfoEntity::getDepartment, collegeInformationEntity.getUserId())
        );
    }
}