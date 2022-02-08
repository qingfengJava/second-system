package com.qingfeng.controller;

import com.qingfeng.entity.User;
import com.qingfeng.service.UserService;
import com.qingfeng.utils.GetDayForWeek;
import com.qingfeng.utils.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;

/**
 * 登录的控制层
 *
 * @author 清风学Java
 * @date 2021/10/27
 * @apiNote
 */
@Controller
@RequestMapping("/userLogin")
public class LoginController {

    /**
     * 用户登录控制层需要维护业务层的接口
     */
    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 生成验证码
     */
    @RequestMapping("/generateImageCode")
    public void generateImageCode(HttpSession session, HttpServletResponse response) throws IOException {
        //1、生成4位随机数
        String code = VerifyCodeUtils.generateVerifyCode(4);
        //2、将得到的验证码保存到session作用域
        session.setAttribute("code",code);
        //3、根据随机数生成图片 && 4、通过response响应图片 && 5、设置响应类型
        response.setContentType("image/png");
        ServletOutputStream outputStream = response.getOutputStream();
        VerifyCodeUtils.outputImage(100,32,outputStream,code);
    }

    /**
     * 用户登录
     * @return
     */
    @RequestMapping("/login")
    public String login(String username, String password,String code,HttpSession session) {
        try {
            //首先判断验证码是否输入正确
            String oldCode = (String) session.getAttribute("code");
            //获取code之后，就将session中的code移除
            session.removeAttribute("code");
            if (!oldCode.equalsIgnoreCase(code)){
                //说明验证码输入不正确
                session.setAttribute("msg","验证码输入错误！");
                return "redirect:/login";
            }

            //1、调用业务层进行登录
            User user = userService.login(username,password);
            //2、返回user对象之后，说明登录成功，保存用户信息到session中
            session.setAttribute("user",user);
            //3、将年月日，星期几存入session域中，并要保证每跳转一个界面，就要重新存入，保证时间实时刷新
            session.setAttribute("day", GetDayForWeek.getDateDayForWeek());

        } catch (Exception e) {
            e.printStackTrace();
            //将错误提示信息保存到session中
            session.setAttribute("msg",e.getMessage());
            //如果登录失败，也就是登录过程中出现了异常，回到登录页面
            return "redirect:/login";
        }

        //跳转到主页面
        return "index";
    }

    /**
     * 定义一个跳转到首页的方法
     * @param session
     * @return
     * @throws ParseException
     */
    @RequestMapping("/index")
    public String goToIndex(HttpSession session) throws ParseException {
        //将年月日，星期几存入session域中，并要保证每跳转一个界面，就要重新存入，保证时间实时刷新
        session.setAttribute("day", GetDayForWeek.getDateDayForWeek());
        return "/index/index";
    }
}
