package com.nilCux.backRacine.modules.services;

import com.nilCux.backRacine.modules.dao.entities.UsersNoisette;
import com.nilCux.backRacine.modules.dao.repositories.UserRepository;
import com.nilCux.backRacine.modules.utils.PasswordUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@EnableMongoRepositories
@NoArgsConstructor
@Service
@CacheConfig(cacheNames = "userCache")
public class UserDBService {
    @Autowired
    UserRepository userRepository;

    public UsersNoisette findUserByMail(String mail) {
        return userRepository.findByMail(mail);
    }

    public boolean registerUser(String mail, String password) {
        UsersNoisette userFound = userRepository.findByMail(mail);
        if(userFound != null) {
            return false;
        }
        String naCl = PasswordUtils.saltGenerator(mail);
        UsersNoisette user = UsersNoisette.builder()
                .mail(mail)
                .password(PasswordUtils.encodePassword(password, naCl))
                .salt(naCl)
                .createTime(new Date())
                .updateTime(new Date()).build();
        try {
            UsersNoisette userRegistered = userRepository.insert(user);
            log.info(userRegistered.toString());
            return true;
        } catch (Exception e) {
            log.error(e.toString());
            return false;
        }
    }

    @Cacheable
    public  boolean userExist(String mail) {
        try {
            UsersNoisette userFound = userRepository.findByMail(mail);
            if (userFound != null)
                return  true;
            else
                return false;
        } catch (Exception e){
            log.error(e.toString());
            return  false;
        }
    }

    public boolean updateTokenById(String id, String token) {
        Optional<UsersNoisette> usersNoisette = userRepository.findById(id);
        if (usersNoisette.isPresent()) {
            try{
                UsersNoisette user = usersNoisette.get();
                user.setToken(token);
                userRepository.save(user);
                return true;
                //userInfo.getCurrentUserInfo().setToken(token);
            } catch (Exception e) {
                log.error(e.toString());
                return false;
            }
        } else {
            return false;
        }
    }

    @Cacheable
    public UsersNoisette findUserById(String id) {
        try {
            Optional<UsersNoisette> usersNoisette = userRepository.findById(id);
            if (usersNoisette.isPresent())
                return usersNoisette.get();
            else return null;
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }
}
