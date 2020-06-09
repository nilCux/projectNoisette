package com.nilCux.backRacine.config.springSecurityConf.impl;

import com.nilCux.backRacine.modules.dao.entities.UsersNoisette;
import com.nilCux.backRacine.modules.services.UserDBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *  <p> 自定义userDetailsService - 认证用户详情 </p>
 *
 * @description :
 * @author : nilCux
 * @date : 2019/10/14 10:33
 */
@Slf4j
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserDBService userDBService;

    /***
     * Create UserDeteil instance by pulling user from DB based on username searching
     * @param username
     * @return: org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**TODO: Currently using mail to identify users, to be improved*/
        UsersNoisette usersNoisette =userDBService.findUserByMail(username);
        if (usersNoisette == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        return new UserDetailsImpl(usersNoisette);
    }

}
