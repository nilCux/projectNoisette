package com.example.backRacine.config.security.login;

import com.example.backRacine.config.security.dto.SecurityUser;
import com.example.backRacine.dataModel.UsersNoisette;
import com.example.backRacine.modules.common.dto.output.ApiResult;
import com.example.backRacine.modules.common.utils.ResponseUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  <p> 认证成功处理 </p>
 *
 * @description :
 * @author : zhengqing
 * @date : 2019/10/12 15:31
 */
@Component
public class AdminAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        UsersNoisette usersNoisette = new UsersNoisette();
        SecurityUser securityUser = ((SecurityUser) auth.getPrincipal());
        usersNoisette.setToken(securityUser.getCurrentUserInfo().getToken());
        ResponseUtils.out(response, ApiResult.ok("登录成功!", usersNoisette));
    }

}
