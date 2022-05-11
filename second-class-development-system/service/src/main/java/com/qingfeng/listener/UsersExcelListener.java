package com.qingfeng.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.qingfeng.dao.UsersMapper;
import com.qingfeng.entity.Users;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.Map;
/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/5
 */
public class UsersExcelListener extends AnalysisEventListener<Users> {

    private UsersMapper usersMapper;

    /**
     * 使用有参的构造方法，传入需要的参数对象
     * @param usersMapper
     */
    public UsersExcelListener(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    /**
     * 一行一行读取Excel内容，从第二行读取，第一行是表头不读取
     *
     * @param userData
     * @param analysisContext
     */
    @Transactional
    @Override
    public void invoke(Users userData, AnalysisContext analysisContext) {
        //首先读取数据库，判断数据库中是否存在该用户，不存在则添加，存在则更新
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", userData.getUsername());
        Users users = usersMapper.selectOneByExample(example);
        if (users == null) {
            //不存在则添加
            userData.setCreateTime(new Date());
            if (userData.getRealname() == null){
                userData.setRealname("");
            }
            usersMapper.insertUseGeneratedKeys(userData);
        }else {
            //存在则更新
            userData.setUid(users.getUid());
            if (userData.getRealname() == null){
                userData.setRealname("");
            }
            userData.setCreateTime(new Date());
            usersMapper.updateByPrimaryKey(userData);
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
