package com.qfedu.fmmall.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import io.jsonwebtoken.*;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2022-09-21  10:22 AM
 */
@Component
public class CheckTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if("OPTIONS".equalsIgnoreCase(method)){
            return true;
        }
        String token = request.getHeader("token");
        if(token == null){
            ResultVO resultVO = new ResultVO(ResStatus.LOGIN_FAIL_NOT, "请先登录！", null);
            //提示请先登录
            doResponse(response,resultVO);
        }else{
            //如果token正确（密码正确，有效期内）则正常执行，否则抛出异常
            try{
                //2.校验token
                JwtParser parser = Jwts.parser();
                parser.setSigningKey("Yuanchuang666");
                //解析token的SigningKey必须和生成token时设置的面膜一致
                Jws<Claims> claimsJws = parser.parseClaimsJws(token);
                return true;
            }catch (ExpiredJwtException e){
                ResultVO resultVO = new ResultVO(ResStatus.LOGIN_FAIL_OVERDUE, "登录过期，请重新登录！", null);
                doResponse(response,resultVO);
            }catch (UnsupportedJwtException e){
                ResultVO resultVO = new ResultVO(ResStatus.NO, "token不合法！", null);
                doResponse(response,resultVO);
            }catch (Exception e){
                ResultVO resultVO = new ResultVO(ResStatus.LOGIN_FAIL_NOT,"请先登录！",null);
                doResponse(response,resultVO);
            }
        }
        return false;
    }

    private void doResponse(HttpServletResponse response,ResultVO resultVO) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(resultVO);
        out.print(s);
        out.flush();
        out.close();
    }
}
