package com.LQD.repository;

import com.LQD.entity.pojo.OTP;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends MongoRepository<OTP,Integer> {
    OTP findByEmail(String email);
}
