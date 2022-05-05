package com.qingfeng.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.qingfeng.dao.OrganizeMapper;
import com.qingfeng.dao.UsersMapper;
import com.qingfeng.dto.OrganizeDto;
import com.qingfeng.entity.Organize;
import com.qingfeng.entity.Users;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Map;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/5
 */

/**
* @author 清风学Java
* @version 1.0.0
* @date 2022/5/5
*/
public class OrganizeExcelListener extends AnalysisEventListener<OrganizeDto> {

   private UsersMapper usersMapper;
   private OrganizeMapper organizeMapper;

   /**
    * 使用有参的构造方法，传入需要的参数对象
    * @param usersMapper
    */
    public OrganizeExcelListener(UsersMapper usersMapper, OrganizeMapper organizeMapper) {
        this.usersMapper = usersMapper;
        this.organizeMapper = organizeMapper;
    }

    /**
    * 一行一行读取Excel内容，从第二行读取，第一行是表头不读取
    *
    * @param userData
    * @param analysisContext
    */
   @Override
   @Transactional
   public void invoke(OrganizeDto organizeDto, AnalysisContext analysisContext) {
       //将拿到的每一行数据，分别设置成对应的实体类
       Users users = new Users();
       users.setUsername(organizeDto.getUsername());
       users.setPassword(organizeDto.getPassword());
       users.setSalt(organizeDto.getSalt());
       users.setTelphone(organizeDto.getTelphone());
       users.setEmail(organizeDto.getEmail());
       users.setPhoto(organizeDto.getPhoto());
       users.setIsAdmin(organizeDto.getIsAdmin());

       Organize organize = new Organize();
       organize.setUserId(organizeDto.getUserId());
       organize.setOrganizeName(organizeDto.getOrganizeName());
       organize.setOrganizeDepartment(organizeDto.getOrganizeDepartment());
       organize.setOrganizeIntroduce(organizeDto.getOrganizeIntroduce());
       organize.setBirthTime(organizeDto.getBirthTime());
       organize.setIsDelete(organizeDto.getIsDelete());
       organize.setCreateTime(organizeDto.getCreateTime());
       organize.setUpdateTime(organizeDto.getUpdateTime());

       //查询用户及对应的社团组织是否存在
       Example example = new Example(Users.class);
       Example.Criteria criteria = example.createCriteria();
       criteria.andEqualTo("username", users.getUsername());
       criteria.andEqualTo("isDelete", 0);
       Users user = usersMapper.selectOneByExample(example);

       Example example1 = new Example(Organize.class);
       Example.Criteria criteria1 = example1.createCriteria();
       criteria1.andEqualTo("userId", organize.getUserId());
       criteria1.andLike("organizeName", "%"+organize.getOrganizeName()+"%");
       criteria1.andEqualTo("isDelete", 0);
       Organize organize1 = organizeMapper.selectOneByExample(example1);

       //进行条件判断 只有都不存在的时候才进行添加
       if (user == null && organize1 == null) {
           usersMapper.insertUseGeneratedKeys(users);
           organize.setUserId(users.getUid());
           organizeMapper.insertUseGeneratedKeys(organize);
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
