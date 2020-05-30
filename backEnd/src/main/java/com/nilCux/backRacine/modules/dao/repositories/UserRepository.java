package com.nilCux.backRacine.modules.dao.repositories;

import com.nilCux.backRacine.modules.dao.entities.UsersNoisette;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UsersNoisette, String> {
    UsersNoisette findByMail(String mail);
}
