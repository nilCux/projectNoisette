package com.example.backRacine.config.security.Service.Impl;

import com.example.backRacine.dataModel.UsersNoisette;
import com.example.backRacine.repositories.UserRepository;
import com.example.backRacine.config.security.dto.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 *  <p> 自定义userDetailsService - 认证用户详情 </p>
 *
 * @description :
 * @author : zhengqing
 * @date : 2019/10/14 10:33
 */
@Slf4j
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /***
     * 根据账号获取用户信息
     * @param username:
     * @return: org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        log.info("Received Request" + username);
        UsersNoisette usersNoisette =userRepository.findByMail(username);
        //List<User> userList = userMapper.selectList(new EntityWrapper<User>().eq("username", username));
        //Users user;
        // 判断用户是否存在
        if (usersNoisette == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        // 返回UserDetails实现类
        return new SecurityUser(usersNoisette);
    }

}
