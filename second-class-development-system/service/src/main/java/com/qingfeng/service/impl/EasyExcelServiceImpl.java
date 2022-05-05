package com.qingfeng.service.impl;

import com.alibaba.excel.EasyExcel;
import com.qingfeng.constant.UserStatus;
import com.qingfeng.dao.OrganizeMapper;
import com.qingfeng.dao.UsersMapper;
import com.qingfeng.dto.OrganizeDto;
import com.qingfeng.entity.Organize;
import com.qingfeng.entity.Users;
import com.qingfeng.listener.OrganizeExcelListener;
import com.qingfeng.listener.UsersExcelListener;
import com.qingfeng.service.EasyExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/5
 */
@Service
public class EasyExcelServiceImpl implements EasyExcelService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private OrganizeMapper organizeMapper;

    /**
     * 用户列表导出Excel表
     * @return
     */
    @Override
    public void userExport(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            //设置URLEncoder.encode可以解决中文乱码问题   这个和EasyExcel没有关系
            String fileName = URLEncoder.encode("用户信息列表", "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="+fileName+".xlsx");

            //查询所有用户（学生/校领导）
            Example example = new Example(Users.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andIn("isAdmin", Arrays.asList(UserStatus.STUDENT_CONSTANTS, UserStatus.LEADER_CONSTANTS, UserStatus.ADMIN_CONSTANTS));
            criteria.andEqualTo("isDelete", 0);
            List<Users> users = usersMapper.selectByExample(example);

            // 调用方法实现写操作
            EasyExcel.write(response.getOutputStream(), Users.class)
                    .sheet("用户信息列表")
                    .doWrite(users);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入用户信息列表
     * @param file
     * @return
     */
    @Override
    public void userImport(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), Users.class, new UsersExcelListener(usersMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 社团信息列表导出Excel表
     * @param response
     */
    @Override
    public void clubExport(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            //设置URLEncoder.encode可以解决中文乱码问题   这个和EasyExcel没有关系
            String fileName = URLEncoder.encode("社团信息列表", "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="+fileName+".xlsx");

            ArrayList<OrganizeDto> organizeList = new ArrayList<>();

            //查询所有社团用户信息
            Example example = new Example(Users.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andIn("isAdmin", Arrays.asList(UserStatus.CLUB_CONSTANTS,UserStatus.STUDENTS_UNION_CONSTANTS));
            criteria.andEqualTo("isDelete", 0);
            List<Users> users = usersMapper.selectByExample(example);
            //循环遍历所有的users，查询每个users对应的社团信息
            for (Users user : users) {
                OrganizeDto organizeDto = new OrganizeDto();
                organizeDto.setUsername(user.getUsername());
                organizeDto.setPassword(user.getPassword());
                organizeDto.setSalt(user.getSalt());
                organizeDto.setTelphone(user.getTelphone());
                organizeDto.setEmail(user.getEmail());
                organizeDto.setPhoto(user.getPhoto());
                organizeDto.setIsAdmin(user.getIsAdmin());

                Example example1 = new Example(Organize.class);
                Example.Criteria criteria1 = example1.createCriteria();
                criteria1.andEqualTo("userId", user.getUid());
                criteria1.andEqualTo("isDelete", 0);
                Organize organize = organizeMapper.selectOneByExample(example1);
                if (organize != null) {
                    organizeDto.setUserId(organize.getUserId());
                    organizeDto.setOrganizeName(organize.getOrganizeName());
                    organizeDto.setOrganizeDepartment(organize.getOrganizeDepartment());
                    organizeDto.setOrganizeIntroduce(organize.getOrganizeIntroduce());
                    organizeDto.setBirthTime(organize.getBirthTime());
                    organizeDto.setIsDelete(organize.getIsDelete());
                    organizeDto.setCreateTime(organize.getCreateTime());
                    organizeDto.setUpdateTime(organize.getUpdateTime());
                }
                organizeList.add(organizeDto);
            }

            // 调用方法实现写操作
            EasyExcel.write(response.getOutputStream(), OrganizeDto.class)
                    .sheet("社团组织信息列表")
                    .doWrite(organizeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clubImport(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), OrganizeDto.class, new OrganizeExcelListener(usersMapper,organizeMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
