package com.hyj.cloud.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyj.cloud.annotation.ResponseResult;
import com.hyj.cloud.common.api.R;
import com.hyj.cloud.common.constants.JwtConstants;
import com.hyj.cloud.utils.JwtTokenUtil;
import com.hyj.cloud.utils.UserContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Slf4j
@AllArgsConstructor
@ResponseResult
public class UserInfoInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.尝试获取头信息中的用户信息
        String token = request.getHeader(JwtConstants.USER_HEADER);
        try {
            // 2.判断是否为空
            if(StringUtils.isEmpty(token)){
                throw new AuthException("没有token");
            }
            // 3.转为用户id并保存
            String userId = JwtTokenUtil.getUserIdByToken(token);
            UserContext.setUser(userId);
            return true;
        } catch (Exception e) {
            R r = R.fail(401,"token无效");
            //设置一个标志
            //response.setHeader("invalid-token","true");
            returnJson(response,JSON.toJSONString(r));
            return false;
        }
    }

    /*返回客户端数据*/
    private void returnJson(HttpServletResponse response, String json) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
        } finally {
            if (writer != null)
                writer.close();
        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清理用户信息
        UserContext.removeUser();
    }
}