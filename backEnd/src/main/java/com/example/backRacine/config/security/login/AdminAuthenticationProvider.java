package com.example.backRacine.config.security.login;

import com.example.backRacine.config.security.Service.Impl.UserDetailsServiceImpl;
import com.example.backRacine.config.security.dto.SecurityUser;
import com.example.backRacine.dataModel.UsersNoisette;
import com.example.backRacine.modules.common.utils.PasswordUtils;
import com.example.backRacine.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 *  <p> 自定义认证处理 </p>
 *
 * @description :
 * @author : zhengqing
 * @date : 2019/10/12 14:49
 */
@Slf4j
@Component
public class AdminAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取前端表单中输入后返回的用户名、密码
        String userName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        SecurityUser userInfo = (SecurityUser) userDetailsService.loadUserByUsername(userName);
        log.info("received pwd: "+password);
        log.info("Saved pwd: "+ userInfo.getPassword());
        //boolean isValid = PasswordUtils.isValidPassword(password, userInfo.getPassword(), userInfo.getCurrentUserInfo().getSalt());
        boolean isValid = password.equals(userInfo.getPassword());
        log.info("Validate: "+isValid);
        // 验证密码
        if (!isValid) {
            throw new BadCredentialsException("密码错误！");
        }

        // 前后端分离情况下 处理逻辑...
        // 更新登录令牌 - 之后访问系统其它接口直接通过token认证用户权限...
        String token = PasswordUtils.encodePassword(System.currentTimeMillis() + userInfo.getCurrentUserInfo().getSalt(), userInfo.getCurrentUserInfo().getSalt());
        Optional<UsersNoisette> usersNoisette = userRepository.findById(userInfo.getCurrentUserInfo().getId());
        if (usersNoisette.isPresent()) {
            UsersNoisette user = usersNoisette.get();
            user.setToken(token);
            userRepository.save(user);
            userInfo.getCurrentUserInfo().setToken(token);
        }

        //userRepository.updateById(user);

        return new UsernamePasswordAuthenticationToken(userInfo, password, userInfo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
