package com.qingfeng.service.impl;

import com.qingfeng.dao.QualityActiveMapper;
import com.qingfeng.entity.QualityActive;
import com.qingfeng.service.QualityActiveService;
import com.qingfeng.utils.SchoolYearUtils;
import com.qingfeng.vo.ResStatus;
import com.qingfeng.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * 精品活动业务层接口实现
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/3/14
 */
@Service
public class QualityActiveServiceImpl implements QualityActiveService {

    @Autowired
    private QualityActiveMapper qualityActiveMapper;

    @Override
    public ResultVO queryApplyQualityActiveCount(Integer organizeId) {
        //先获取学年
        String strYear = SchoolYearUtils.getSchoolYearByOne();
        String schoolYear = strYear.substring(0, strYear.lastIndexOf("-"));

        //设置条件查询
        Example example = new Example(QualityActive.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("organizeId",organizeId);
        criteria.andEqualTo("isCheck",1);
        criteria.andEqualTo("isConfirm",2);
        criteria.andEqualTo("isDelete",0);
        criteria.andLike("schoolYear","%"+schoolYear+"%");
        int count = qualityActiveMapper.selectCountByExample(example);
        if (count >= 0){
            return new ResultVO(ResStatus.OK,"success",count);
        }
        return new ResultVO(ResStatus.NO,"fail",null);
    }

    @Override
    public ResultVO addQualityActive(QualityActive qualityActive) {
        //查看当前学年活动是否重复申请
        String schoolYear = SchoolYearUtils.getSchoolYearByOne();
        String strSchoolYear = schoolYear.substring(0, schoolYear.lastIndexOf("-"));

        //封装查询的数据
        Example example = new Example(QualityActive.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("applyId",qualityActive.getApplyId());
        criteria.andEqualTo("organizeId",qualityActive.getOrganizeId());
        criteria.andLike("schoolYear","%"+strSchoolYear+"%");
        criteria.andEqualTo("isDelete",0);
        QualityActive active = qualityActiveMapper.selectOneByExample(example);
        if (active == null){
            //说明可以申请
            //获取学年时间
            qualityActive.setSchoolYear(schoolYear);
            qualityActive.setIsCheck(0);
            qualityActive.setIsConfirm(0);
            qualityActive.setCreateTime(new Date());
            qualityActive.setIsDelete(0);
            int i = qualityActiveMapper.insertUseGeneratedKeys(qualityActive);
            if (i > 0){
                //说明添加成功
                return new ResultVO(ResStatus.OK,"success",qualityActive);
            }else {
                return new ResultVO(ResStatus.NO,"fail",null);
            }
        }else{
            return new ResultVO(ResStatus.NO,"不能重复申请活动！",null);
        }
    }

    /**
     * 精品活动信息的修改
     * @param qualityActive
     * @return
     */
    @Override
    public ResultVO updateQualityActive(QualityActive qualityActive) {
        //这里进行修改之后，不需要额外的审核   只是针对原有的信息进行修改即可
        qualityActive.setIsCheck(0);
        qualityActive.setIsConfirm(0);
        qualityActive.setCreateTime(new Date());
        qualityActive.setIsDelete(0);
        int i = qualityActiveMapper.updateByPrimaryKeySelective(qualityActive);
        if (i > 0){
            //说明修改成功
            return new ResultVO(ResStatus.OK,"success",qualityActive);
        }
        return new ResultVO(ResStatus.NO,"fail",null);
    }
}
