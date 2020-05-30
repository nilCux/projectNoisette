package com.nilCux.backRacine.config.springSecurityConf.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * <p> 自定义认证管理器 </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2019/10/12 14:49
 */
@Component
public class AuthenticationManagerImpl implements AuthenticationManager {

    private final AuthenticationProviderImpl authenticationProviderImpl;

    public AuthenticationManagerImpl(AuthenticationProviderImpl authenticationProviderImpl) {
        this.authenticationProviderImpl = authenticationProviderImpl;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication result = authenticationProviderImpl.authenticate(authentication);
        if (Objects.nonNull(result)) {
            return result;
        }
        throw new ProviderNotFoundException("Authentication failed!");
    }

}
