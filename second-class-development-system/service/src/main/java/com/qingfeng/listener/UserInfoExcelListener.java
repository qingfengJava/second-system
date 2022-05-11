package com.qingfeng.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.qingfeng.dao.UserInfoMapper;
import com.qingfeng.dao.UsersMapper;
import com.qingfeng.entity.UserInfo;
import com.qingfeng.entity.Users;
import com.qingfeng.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.Map;

/**
* @author 清风学Java
* @version 1.0.0
* @date 2022/5/5
*/
public class UserInfoExcelListener extends AnalysisEventListener<UserInfo> {

   private UserInfoMapper userInfoMapper;
   private UsersMapper usersMapper;

   /**
    * 使用有参的构造方法，传入需要的参数对象
    * @param userInfoMapper
    * @param usersMapper
    */
    public UserInfoExcelListener(UserInfoMapper userInfoMapper, UsersMapper usersMapper) {
        this.userInfoMapper = userInfoMapper;
        this.usersMapper = usersMapper;
    }

    /**
    * 一行一行读取Excel内容，从第二行读取，第一行是表头不读取
    *
    * @param userInfo
    * @param analysisContext
    */
   @Transactional
   @Override
   public void invoke(UserInfo userInfo, AnalysisContext analysisContext) {
       //先根据学号查询学生信息是否存在
       Example example = new Example(Users.class);
       Example.Criteria criteria = example.createCriteria();
       criteria.andEqualTo("username",userInfo.getStudentNum());
       criteria.andEqualTo("isDelete",0);
       Users users = usersMapper.selectOneByExample(example);
       if (users == null){
           //学生信息不存在，创建学生信息
           users = new Users();
           users.setUsername(userInfo.getStudentNum());
           users.setIsDelete(0);
           users.setCreateTime(new Date());
           users.setIsAdmin(1);
           //1、生成随机盐
           String salt = SaltUtils.getSalt(8);
           //3、明文密码进行md5+salt+hash散列
           Md5Hash md5Hash = new Md5Hash("123456", salt, 3);
           //得到加密后的密码
           String newPassWord = md5Hash.toHex();
           users.setPassword(newPassWord);
           users.setSalt(salt);
           usersMapper.insertUseGeneratedKeys(users);
       }
       //设置学生Id
       userInfo.setUid(users.getUid());

       Example example1 = new Example(UserInfo.class);
       Example.Criteria criteria1 = example1.createCriteria();
       criteria1.andEqualTo("studentNum",userInfo.getStudentNum());
       criteria1.andEqualTo("isDelete",0);
       UserInfo userInfo1 = userInfoMapper.selectOneByExample(example1);
       if (userInfo1 != null){
           //学生详情信息存在，更新学生详情信息
           userInfoMapper.updateByExampleSelective(userInfo,example1);
       }else{
           //学生详情信息不存在，插入学生详情信息
           userInfoMapper.insertUseGeneratedKeys(userInfo);
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
