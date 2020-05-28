package com.example.backRacine.dataModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UsersNoisette {
    @Id
    private String id;
    private String mail;
    private String password;
    private Date createTime;
    private Date updateTime;
    private String token;
    private String salt;

}

