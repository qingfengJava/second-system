package com.qingfeng.controller;

import com.qingfeng.entity.User;
import com.qingfeng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 用户的控制层
 *
 * @author 清风学Java
 * @date 2021/11/17
 * @apiNote
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 跳转到修改密码页面
     * @return
     */
    @RequestMapping("/changePwd")
    public String goToUpdatePwd(){
        return "change-password";
    }

    /**
     * 修改密码的方法
     * @param oldPassword
     * @param newPassword
     * @param code
     * @param session
     * @return
     */
    @RequestMapping("/update")
    public String updatePwd(String oldPassword, String newPassword, String code, HttpSession session){

        try {
            //首先判断验证码是否输入正确
            String oldCode = (String) session.getAttribute("code");
            //获取code之后，就将session中的code移除
            session.removeAttribute("code");
            if (!oldCode.equals(code)){
                //说明验证码输入不正确
                session.setAttribute("update_msg","验证码输入错误！");
                //重新跳转回修改密码页面
                return "redirect:/user/changePwd";
            }

            //比较原密码是否正确，因为用户对象已经存入session中，可以直接从session中取原密码进行比较
            User user = (User) session.getAttribute("user");
            if (!user.getPassword().equals(oldPassword)){
                session.setAttribute("update_msg","旧密码输入错误！");
            }

            //旧密码输入正确，那么就开始修改密码，调用service层的修改密码的方法，根据用户名修改密码
            userService.updatePassword(user.getUsername(),newPassword);

        } catch (Exception e) {
            e.printStackTrace();
            //将错误提示信息保存到session中
            session.setAttribute("update_msg",e.getMessage());
            //如果修改密码失败，重新跳转回修改密码页面
            return "redirect:/user/changePwd";
        }

        //修改成功，回到登录页
        return "login";
    }

    /**
     * 跳转到完善信息的页面
     * @return
     */
    @RequestMapping("/goToComInfo")
    public String goToCompleteInfo() {
        //注意：跳转到完善信息页面的时候，应该确保session域中有user对象，首先要做的就是信息的回显。
        // 因为登录的时候已经保存了user对象，所以这里就不需要再查询用户对象了
        return "complete-information";
    }
}
