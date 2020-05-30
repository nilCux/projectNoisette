package com.nilCux.backRacine.config.springSecurityConf.filter;

import com.alibaba.fastjson.JSONObject;
import com.nilCux.backRacine.modules.dao.entities.UsersNoisette;
import com.nilCux.backRacine.config.Constants;
import com.nilCux.backRacine.config.springSecurityConf.impl.AuthenticationManagerImpl;
import com.nilCux.backRacine.config.springSecurityConf.authResultHandlers.AuthenticationFailureHandlerImpl;
import com.nilCux.backRacine.config.springSecurityConf.authResultHandlers.AuthenticationSuccessHandlerImpl;
import com.nilCux.backRacine.modules.utils.MultiReadHttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Customized Au
 */
@Slf4j
@Component
public class CustomizedAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * @param authenticationManager
     * @param authenticationSuccessHandlerImpl
     * @param authenticationFailureHandlerImpl
     */
    public CustomizedAuthenticationProcessingFilter(AuthenticationManagerImpl authenticationManager, AuthenticationSuccessHandlerImpl authenticationSuccessHandlerImpl, AuthenticationFailureHandlerImpl authenticationFailureHandlerImpl) {
        super(new AntPathRequestMatcher("/login", "POST"));
        this.setAuthenticationManager(authenticationManager);
        this.setAuthenticationSuccessHandler(authenticationSuccessHandlerImpl);
        this.setAuthenticationFailureHandler(authenticationFailureHandlerImpl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getContentType() == null || !request.getContentType().contains(Constants.REQUEST_HEADERS_CONTENT_TYPE)) {
            throw new AuthenticationServiceException("Header type unsupported: " + request.getContentType());
        }

        UsernamePasswordAuthenticationToken authRequest;
        try {
            MultiReadHttpServletRequest wrappedRequest = new MultiReadHttpServletRequest(request);
            /** Parse request content to JSON format */
            log.info("Capture wrapped request: "+wrappedRequest.getHeader("Cookie"));

            UsersNoisette usersNoisette = JSONObject.parseObject(wrappedRequest.getBodyJsonStrByJson(wrappedRequest), UsersNoisette.class);
            authRequest = new UsernamePasswordAuthenticationToken(usersNoisette.getMail(), usersNoisette.getPassword(), null);
            authRequest.setDetails(authenticationDetailsSource.buildDetails(wrappedRequest));
        } catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
        return this.getAuthenticationManager().authenticate(authRequest);
    }

}
