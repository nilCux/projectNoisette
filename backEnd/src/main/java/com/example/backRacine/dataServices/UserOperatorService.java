package com.example.backRacine.dataServices;

import com.example.backRacine.dataModel.UsersNoisette;
import com.example.backRacine.repositories.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@EnableMongoRepositories
@NoArgsConstructor
@Service
public class UserOperatorService {
    @Autowired
    UserRepository userRepository;

    public boolean registerUser(String mail, String password) {
        UsersNoisette userFound = userRepository.findByMail(mail);
        if(userFound != null) {
            return false;
        }
        UsersNoisette user = UsersNoisette.builder()
                .mail(mail)
                .password(password)
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


    public  boolean loggingIn(String mail, String password) {
        try {
            UsersNoisette userFound = userRepository.findByMail(mail);
            return  userFound.getPassword().equals(password);
        } catch (Exception e){
            log.error(e.toString());
            return  false;
        }
    }
}
