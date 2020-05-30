package com.nilCux.backRacine.config.springSecurityConf.authResultHandlers;

import com.nilCux.backRacine.config.springSecurityConf.impl.UserDetailsImpl;
import com.nilCux.backRacine.modules.dao.entities.UsersNoisette;
import com.nilCux.backRacine.modules.output.ApiResult;
import com.nilCux.backRacine.modules.utils.ResponseUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        UsersNoisette usersNoisette = new UsersNoisette();
        UserDetailsImpl userDetailsImpl = ((UserDetailsImpl) auth.getPrincipal());
        usersNoisette.setToken(userDetailsImpl.getCurrentUserInfo().getToken());
        usersNoisette.setId(userDetailsImpl.getCurrentUserInfo().getId());
        ResponseUtils.respondWithResult(response, ApiResult.ok("登录成功!", usersNoisette));
    }

}
