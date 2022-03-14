package com.qingfeng.service.impl;

import com.qingfeng.dao.GradeMapper;
import com.qingfeng.entity.Grade;
import com.qingfeng.service.GradeService;
import com.qingfeng.utils.SchoolYearUtils;
import com.qingfeng.vo.ResStatus;
import com.qingfeng.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * 社团评级业务层接口实现
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/3/14
 */
@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeMapper gradeMapper;

    @Override
    public ResultVO commentGrade(Grade grade) {
        //获取学年
        String str = SchoolYearUtils.getSchoolYearByOne();
        String schoolYear = str.substring(0, str.lastIndexOf("-"));
        //先查询该社团这个学年是否已经评级过   直接根据学年和社团组织Id进行查询
        Example example = new Example(Grade.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("organizeId",grade.getOrganizeId());
        criteria.andEqualTo("schoolYear",grade.getSchoolYear());
        Grade oldGrade = gradeMapper.selectOneByExample(example);
        if (oldGrade == null){
            //说明该社团这个学年还没有进行过评级
            grade.setSchoolYear(schoolYear);
            grade.setCreateTime(new Date());
            int i = gradeMapper.insertUseGeneratedKeys(grade);
            if (i > 0){
                return new ResultVO(ResStatus.OK,"success",grade);
            }else{
                return new ResultVO(ResStatus.NO,"网络异常，稍后再试！",null);
            }
        }
        return new ResultVO(ResStatus.NO,"该社团已经评级，不能重复评！",null);
    }
}
