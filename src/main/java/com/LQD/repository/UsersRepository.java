package com.LQD.repository;

import com.LQD.entity.pojo.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<Users,Integer> {
    Users findByEmail(String email);
}
