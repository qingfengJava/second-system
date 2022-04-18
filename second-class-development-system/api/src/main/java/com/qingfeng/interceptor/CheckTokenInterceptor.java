package com.qingfeng.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qingfeng.constant.ResStatus;
import com.qingfeng.vo.ResultVO;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 定义一个拦截器
 *
 * 在配置类中添加拦截器，定义我们要拦截的路径。
 * 拦截之后。满足条件返回true，继续按照执行。
 * 若不满足条件，返回false，执行结束。
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/04/18
 */
@Component
public class CheckTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //放行options请求(预检请求)
        String method = request.getMethod();
        if("OPTIONS".equalsIgnoreCase(method)){
            return true;
        }
        //从请求头中拿到token
        String token = request.getHeader("token");

        //判断token是否为空
        if(token == null){
            ResultVO resultVO = new ResultVO(ResStatus.NO, "请先登录！", null);
            //提示请先登录
            doResponse(response,resultVO);
        }else{
            try {
                //验证token  只要没有抛出异常，说明校验通过
                JwtParser parser = Jwts.parser();
                //解析token的SigningKey必须和生成token时设置密码一致
                parser.setSigningKey("QINGfengJAVA");
                //如果token正确（密码正确，有效期内）则正常执行，否则抛出异常
                Jws<Claims> claimsJws = parser.parseClaimsJws(token);
                return true;
            }catch (ExpiredJwtException e){
                ResultVO resultVO = new ResultVO(ResStatus.NO, "登录过期，请重新登录！", null);
                doResponse(response,resultVO);
            }catch (UnsupportedJwtException e){
                ResultVO resultVO = new ResultVO(ResStatus.NO, "Token不合法，请自重！", null);
                doResponse(response,resultVO);
            }catch (Exception e){
                ResultVO resultVO = new ResultVO(ResStatus.NO, "请先登录！", null);
                doResponse(response,resultVO);
            }
        }
        return false;
    }

    /**
     * 封装响应的方法
     */
    private void doResponse(HttpServletResponse response, ResultVO resultVO) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(resultVO);
        out.print(s);
        out.flush();
        out.close();
    }
}
