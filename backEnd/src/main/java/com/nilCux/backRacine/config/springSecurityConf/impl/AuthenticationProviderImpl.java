package com.nilCux.backRacine.config.springSecurityConf.impl;

import com.nilCux.backRacine.modules.services.UserDBService;
import com.nilCux.backRacine.modules.utils.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserDBService userDBService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取前端表单中输入后返回的用户名、密码
        String userName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        UserDetailsImpl userInfo = (UserDetailsImpl) userDetailsService.loadUserByUsername(userName);
        log.info("received pwd: "+password);
        log.info("Saved pwd: "+ userInfo.getPassword());
        boolean isValid = PasswordUtils.isValidPassword(password, userInfo.getPassword(), userInfo.getCurrentUserInfo().getSalt());
        //boolean isValid = password.equals(userInfo.getPassword());
        log.info("Validate: "+isValid);
        // 验证密码
        if (!isValid) {
            throw new BadCredentialsException("Password_Incorrect");
        }

        // 前后端分离情况下 处理逻辑...
        // 更新登录令牌 - 之后访问系统其它接口直接通过token认证用户权限...
        String token = PasswordUtils.encodePassword(System.currentTimeMillis() + userInfo.getCurrentUserInfo().getSalt(), userInfo.getCurrentUserInfo().getSalt());
        if (userDBService.updateTokenById(userInfo.getCurrentUserInfo().getId(), token))
            userInfo.getCurrentUserInfo().setToken(token);

        return new UsernamePasswordAuthenticationToken(userInfo, password, userInfo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
