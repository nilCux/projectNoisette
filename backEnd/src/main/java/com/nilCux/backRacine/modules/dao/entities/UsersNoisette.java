package com.nilCux.backRacine.modules.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Date;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UsersNoisette {
    @Id
    private String id;
    private String fakeName;
    private String mail;
    private String password;

    private Date createTime;
    private Date updateTime;
    private String token;
    private String salt;
    private String role;
}

