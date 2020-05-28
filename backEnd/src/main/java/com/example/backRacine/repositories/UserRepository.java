package com.example.backRacine.repositories;

import com.example.backRacine.dataModel.UsersNoisette;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UsersNoisette, String> {
    UsersNoisette findByMail(String mail);
}
