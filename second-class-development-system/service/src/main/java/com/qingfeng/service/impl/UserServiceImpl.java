package com.qingfeng.service.impl;

import com.qingfeng.dao.UsersMapper;
import com.qingfeng.entity.Users;
import com.qingfeng.service.UserService;
import com.qingfeng.utils.SaltUtils;
import com.qingfeng.vo.ResStatus;
import com.qingfeng.vo.ResultVO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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
                    .signWith(SignatureAlgorithm.HS256, "QIANfengJava")
                    .compact();

            //返回的时候要把token返回回去
            return new ResultVO(ResStatus.LOGIN_SUCCESS,token,users.get(0));
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            return new ResultVO(ResStatus.LOGIN_FAIL_OVERDUE,"用户名错误！",null);
        } catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误！");
            return new ResultVO(ResStatus.LOGIN_FAIL_OVERDUE,"密码错误！",null);

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
                user.setSalt(salt);
                user.setIsAdmin(isAdmin);
                user.setPhoto("default.png");
                user.setIsDelete(0);
                user.setCreateTime(new Date());
                //插入数据，并且实现主键回填
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
        int i = usersMapper.updateByPrimaryKey(user);
        if (i > 0){
            return new ResultVO(ResStatus.OK,"信息保存成功！！！",user);
        }else{
            return new ResultVO(ResStatus.NO,"信息保存失败！！！",null);
        }
    }
}
