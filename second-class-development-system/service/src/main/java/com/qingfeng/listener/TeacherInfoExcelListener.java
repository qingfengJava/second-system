package com.qingfeng.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.qingfeng.dao.TeacherInfoMapper;
import com.qingfeng.dao.UsersMapper;
import com.qingfeng.entity.TeacherInfo;
import com.qingfeng.entity.Users;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Map;

/**
* @author 清风学Java
* @version 1.0.0
* @date 2022/5/5
*/
public class TeacherInfoExcelListener extends AnalysisEventListener<TeacherInfo> {

   private UsersMapper usersMapper;
   private TeacherInfoMapper teacherInfoMapper;

    /**
    * 一行一行读取Excel内容，从第二行读取，第一行是表头不读取
    *
    * @param userInfo
    * @param analysisContext
    */
   @Transactional
   @Override
   public void invoke(TeacherInfo teacherInfo, AnalysisContext analysisContext) {
       //先根据uid查询数据库用户是否存在，存在则添加，不存在则不添加
       Example example = new Example(Users.class);
       Example.Criteria criteria = example.createCriteria();
       criteria.andEqualTo("uid",teacherInfo.getUid());
       criteria.andEqualTo("isDelete",0);
      Users users = usersMapper.selectOneByExample(example);
      if(users!=null){
         //查询教师详情信息   存在就更新，不存在就添加
         Example example1 = new Example(TeacherInfo.class);
         Example.Criteria criteria1 = example1.createCriteria();
         criteria1.andEqualTo("uid",teacherInfo.getUid());
         criteria1.andEqualTo("isDelete",0);
         TeacherInfo teacherInfo1 = teacherInfoMapper.selectOneByExample(example1);
         if(teacherInfo1!=null){
            teacherInfo.setTeacherInfoId(teacherInfo1.getTeacherInfoId());
            teacherInfoMapper.updateByPrimaryKeySelective(teacherInfo);
         }else{
            teacherInfoMapper.insertUseGeneratedKeys(teacherInfo);
         }
      }
   }

   /**
    * 读取Excel表头第一行的数据
    *
    * @param headMap
    * @param context
    */
   @Override
   public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {

   }

   /**
    * 读取Excel结束后调用
    *
    * @param analysisContext
    */
   @Override
   public void doAfterAllAnalysed(AnalysisContext analysisContext) {

   }
}
