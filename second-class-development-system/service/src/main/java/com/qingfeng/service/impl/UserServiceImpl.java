package com.qingfeng.service.impl;

import com.qingfeng.constant.ResStatus;
import com.qingfeng.constant.UserStatus;
import com.qingfeng.dao.OrganizeMapper;
import com.qingfeng.dao.TeacherInfoMapper;
import com.qingfeng.dao.UserInfoMapper;
import com.qingfeng.dao.UsersMapper;
import com.qingfeng.entity.Organize;
import com.qingfeng.entity.TeacherInfo;
import com.qingfeng.entity.UserInfo;
import com.qingfeng.entity.Users;
import com.qingfeng.service.UserService;
import com.qingfeng.utils.PageHelper;
import com.qingfeng.utils.SaltUtils;
import com.qingfeng.vo.ResultVO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 用户业务层接口实现
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/2/11
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserInfoMapper usreInfoMapper;
    @Autowired
    private TeacherInfoMapper teacherInfoMapper;
    @Autowired
    private OrganizeMapper organizeMapper;

    @Override
    public ResultVO checkLogin(String username, String password) {
        //获取主体对象  注意：SecurityUtils需要安全管理器，在web环境下，只要我们配置了安全管理器，那么我们就可以放心使用
        Subject subject = SecurityUtils.getSubject();
        try {
            //把登录校验的工作交给shiro去完成
            subject.login(new UsernamePasswordToken(username,password));
            //校验成功，先查询用户，后面要用
            Example example = new Example(Users.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("username",username);
            List<Users> users = usersMapper.selectByExample(example);

            //生成token
            //使用JWT规则生成token字符串
            JwtBuilder builder = Jwts.builder();

            //主题，就是token中携带的数据
            String token = builder.setSubject(username)
                    //设置token的生成时间
                    .setIssuedAt(new Date())
                    //设置用户Id为token的id
                    .setId(users.get(0).getUid()+"")
                    //设置token的过期时间
                    .setExpiration(new Date(System.currentTimeMillis() + 24*60*60*1000))
                    //设置加密方式和加密密码
                    .signWith(SignatureAlgorithm.HS256, "QINGfengJAVA")
                    .compact();

            //返回的时候要把token返回回去
            return new ResultVO(ResStatus.LOGIN_SUCCESS,token,users.get(0));
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            return new ResultVO(ResStatus.LOGIN_FAIL_USERNAME,"用户名错误！",null);
        } catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误！");
            return new ResultVO(ResStatus.LOGIN_FAIL_PASSWORD,"密码错误！",null);

        }
    }

    @Override
    public ResultVO userAdd(String username, String password, int isAdmin) {
        synchronized(this){
            //首先根据用户名查询是否有用户名已经存在
            Example example = new Example(Users.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("username",username);
            List<Users> users = usersMapper.selectByExample(example);

            //如果没有该用户，则允许添加
            if (users.size() == 0){
                //1、生成随机盐
                String salt = SaltUtils.getSalt(8);
                //3、明文密码进行md5+salt+hash散列
                Md5Hash md5Hash = new Md5Hash(password, salt, 3);
                //得到加密后的密码
                String newPassWord = md5Hash.toHex();
                //将信息保存到user对象中
                Users user = new Users();
                user.setUsername(username);
                user.setPassword(newPassWord);
                user.setRealname("");
                user.setSalt(salt);
                user.setIsAdmin(isAdmin);
                user.setPhoto("default.png");
                user.setIsDelete(0);
                user.setCreateTime(new Date());
                //插入数据，并且实现主键回填到user对象中
                int i = usersMapper.insertUseGeneratedKeys(user);
                if (i > 0){
                    return new ResultVO(ResStatus.OK,"添加用户成功",user);
                }else{
                    return new ResultVO(ResStatus.NO,"添加用户失败",null);
                }
            }else{
                return new ResultVO(ResStatus.NO,"用户已经存在，请确认信息！",null);
            }
        }
    }

    @Override
    public ResultVO updatePassword(String uid, String password) {
        System.out.println(uid);
        //根据用户Id修改用户密码  先获取一个完整的用户
        Users users = usersMapper.selectByPrimaryKey(uid);
        //封装要修改的信息
        //1、生成随机盐
        String salt = SaltUtils.getSalt(8);
        //3、明文密码进行md5+salt+hash散列
        Md5Hash md5Hash = new Md5Hash(password, salt, 3);
        //得到加密后的密码
        String newPassWord = md5Hash.toHex();
        users.setPassword(newPassWord);
        users.setSalt(salt);
        int i = usersMapper.updateByPrimaryKeySelective(users);
        if (i > 0){
            //说明修改成功
            return  new ResultVO(ResStatus.OK,"修改密码成功，前去登录！",users);
        }
        return new ResultVO(ResStatus.NO,"网络异常，修改密码失败!",null);
    }

    @Override
    public ResultVO updateMessage(String uid, Users users) {
        //根据用户Id来完善信息  先获取一个完整的用户对象，然后再进行修改
        Users user = usersMapper.selectByPrimaryKey(uid);
        //将信息全部封装到user里
        user.setRealname(users.getRealname());
        user.setNickname(users.getNickname());
        user.setTelphone(users.getTelphone());
        user.setQq(users.getQq());
        user.setEmail(users.getEmail());
        user.setPhoto(users.getPhoto());
        user.setHobydes(users.getHobydes());
        //根据主键更新属性不为null的值
        int i = usersMapper.updateByPrimaryKeySelective(user);
        if (i > 0){
            return new ResultVO(ResStatus.OK,"信息保存成功！！！",user);
        }else{
            return new ResultVO(ResStatus.NO,"信息保存失败！！！",null);
        }
    }

    @Override
    public ResultVO checkUser(String uid) {
        Users users = usersMapper.selectByPrimaryKey(uid);
        if (users != null){
            return new ResultVO(ResStatus.OK,"success",users);
        }
        return new ResultVO(ResStatus.NO,"fail",null);
    }

    @Override
    public ResultVO checkUserInfo(String uid,Integer isAdmin) {
        //对用户身份进行判断，不同用户查询的详情信息不一样
        if (isAdmin == UserStatus.STUDENT_CONSTANTS){
            //学生身份，查询学生对应的信息
            Example example = new Example(UserInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("uid",uid);
            UserInfo userInfo = usreInfoMapper.selectOneByExample(example);
            if (userInfo != null){
                //说明查询成功
                return new ResultVO(ResStatus.OK,"success", userInfo);
            }
        }else if (isAdmin == UserStatus.CLUB_CONSTANTS || isAdmin == UserStatus.STUDENTS_UNION_CONSTANTS){
            //说明是学生社团或学生社团发展中心的身份
            Organize organize = organizeMapper.selectOrganizeByUserId(Integer.parseInt(uid));
            if (organize != null){
                return new ResultVO(ResStatus.OK,"success",organize);
            }
        }else if (isAdmin == UserStatus.LEADER_CONSTANTS || isAdmin == UserStatus.ADMIN_CONSTANTS){
            //说明是校领导
            Example example = new Example(TeacherInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("uid",uid);
            TeacherInfo teacherInfo = teacherInfoMapper.selectOneByExample(example);
            if (teacherInfo != null){
                return new ResultVO(ResStatus.OK,"success",teacherInfo);
            }
        }
        //如果没有查到直接返回失败
        return new ResultVO(ResStatus.NO,"用户详情信息不存在！",null);
    }

    @Override
    public ResultVO updateUserInfo(Integer uid, UserInfo userInfo) {
        //先根据uid查询详情表是否有该学生的详情记录
        Example example = new Example(UserInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid",uid);
        UserInfo oldUserInfo = usreInfoMapper.selectOneByExample(example);
        //定义一个记录数
        int count = 0;
        userInfo.setUid(uid);
        userInfo.setUpdateTime(new Date());
        if (oldUserInfo == null){
            //说明没有记录，要进行添加操作
            userInfo.setCreateTime(new Date());
            userInfo.setIsDelete(0);
            userInfo.setIsChange(0);
            //封装完信息进行保存操作
            count = usreInfoMapper.insertUseGeneratedKeys(userInfo);
        }else{
            //说明是进行信息更新操作
            userInfo.setUserInfoId(oldUserInfo.getUserInfoId());
            //更新
            count = usreInfoMapper.updateByPrimaryKeySelective(userInfo);
        }
        if (count > 0) {
            //说明添加或更新信息成功
            return new ResultVO(ResStatus.OK, "信息保存成功！", userInfo);
        }else {
            return new ResultVO(ResStatus.NO,"网络超时，信息保存失败！",null);
        }
    }

    @Override
    public ResultVO updateTeacherInfo(Integer uid, TeacherInfo teacherInfo) {
        //先根据uid查询详情表是否有该校领导的详情记录
        Example example = new Example(TeacherInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid",uid);
        TeacherInfo oldTeacherInfo = teacherInfoMapper.selectOneByExample(example);
        //定义一个记录数
        int count = 0;
        //设置公告需要设置的值
        teacherInfo.setUid(uid);
        teacherInfo.setUpdateTime(new Date());
        if (oldTeacherInfo == null){
            //说明没有记录，要进行添加操作
            teacherInfo.setCreateTime(new Date());
            teacherInfo.setIsDelete(0);
            //封装完信息进行保存操作
            count = teacherInfoMapper.insertUseGeneratedKeys(teacherInfo);
        }else{
            //说明是进行信息更新操作
            teacherInfo.setTeacherInfoId(oldTeacherInfo.getTeacherInfoId());
            //更新
            count = teacherInfoMapper.updateByPrimaryKeySelective(teacherInfo);
        }
        if (count > 0) {
            //说明添加或更新信息成功
            return new ResultVO(ResStatus.OK, "信息保存成功！", teacherInfo);
        }else {
            return new ResultVO(ResStatus.NO,"网络超时，信息保存失败！",null);
        }
    }

    @Override
    public List<Users> selectByUserIdentity(Integer isAdmin) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isAdmin",isAdmin);
        return usersMapper.selectByExample(example);
    }

    /**
     * 分页条件查询所有学生用户列表信息
     * @param pageNum
     * @param limit
     * @param realName
     * @param username
     * @param isAdmin
     * @return
     */
    @Override
    public ResultVO findByList(int pageNum, int limit, String realName, String username,String isAdmin) {
        try {
            //首先判断是否有查询条件
            Example example = new Example(Users.class);
            Example.Criteria criteria = example.createCriteria();
            if (realName == null){
                realName = "";
            }
            criteria.andLike("realname","%"+realName+"%");
            if (username == null){
                username = "";
            }
            criteria.andLike("username","%"+username+"%");
            if ("".equals(isAdmin)){
                //查询学生领导及管理员
                criteria.andIn("isAdmin", Arrays.asList(UserStatus.STUDENT_CONSTANTS,UserStatus.LEADER_CONSTANTS,UserStatus.ADMIN_CONSTANTS));
            }else if (UserStatus.STUDENT_ADMIN.equals(isAdmin)){
                //说明是学生用户
                criteria.andEqualTo("isAdmin",Arrays.asList(UserStatus.STUDENT_CONSTANTS));
            }else if (UserStatus.ADMIN.equals(isAdmin)){
                //说明是校领导用户
                criteria.andIn("isAdmin", Arrays.asList(UserStatus.LEADER_CONSTANTS,UserStatus.ADMIN_CONSTANTS));
            }

            criteria.andEqualTo("isDelete",0);

            //分页
            int start = (pageNum-1)*limit;
            RowBounds rowBounds = new RowBounds(start,limit);
            //查询
            List<Users> usersList = usersMapper.selectByExampleAndRowBounds(example, rowBounds);
            //查询总记录数（满足条件）
            int count = usersMapper.selectCountByExample(example);
            //计算总页数
            int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
            //封装分页信息
            PageHelper<Users> pageHelper = new PageHelper<>(count, pageCount, usersList);

            return new ResultVO(ResStatus.OK, "success", pageHelper);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO(ResStatus.NO, "网络超时，查询失败！", null);
        }
    }

    /**
     * 用户完善头像的方法
     * @param uid
     * @param newFileName
     * @return
     */
    @Override
    public ResultVO updateImg(Integer uid, String newFileName) {
        Users users = new Users();
        users.setUid(uid);
        users.setPhoto(newFileName);
        int count = usersMapper.updateByPrimaryKeySelective(users);
        if (count > 0){
            return new ResultVO(ResStatus.OK,"头像修改成功！",users);
        }
        return new ResultVO(ResStatus.NO,"头像修改失败！",null);
    }

    /**
     * 根据用户信息删除用户信息
     * @param uid
     * @return
     */
    @Override
    @Transactional
    public ResultVO deleteUserByUid(Integer uid) {
        Users users = new Users();
        users.setUid(uid);
        users.setIsDelete(1);
        //直接将值更新为1
        int i = usersMapper.updateByPrimaryKeySelective(users);
        if (i > 0){
            //将用户详情信息也删除
            UserInfo userInfo = new UserInfo();
            userInfo.setIsDelete(1);
            Example example = new Example(UserInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("uid",uid);
            usreInfoMapper.updateByExampleSelective(userInfo,example);
            return new ResultVO(ResStatus.OK,"删除成功！",null);
        }
        return new ResultVO(ResStatus.NO,"删除失败！",null);
    }

    @Override
    public ResultVO deleteBatch(Integer[] uIds) {
        Users users = new Users();
        users.setIsDelete(1);
        Example example = new Example(Users.class);
        example.createCriteria().andIn("uid", Arrays.asList(uIds));
        int i = usersMapper.updateByExampleSelective(users, example);
        if (i > 0) {
            //将用户详情表中的记录也要删除
            UserInfo userInfo = new UserInfo();
            userInfo.setIsDelete(1);
            Example example1 = new Example(UserInfo.class);
            example1.createCriteria().andIn("uid",Arrays.asList(uIds));
            usreInfoMapper.updateByExampleSelective(userInfo,example1);
            return new ResultVO(ResStatus.OK,"success",null);
        }
        return new ResultVO(ResStatus.NO,"fail",null);
    }
}
