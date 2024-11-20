package com.LQD.repository;

import com.LQD.entity.pojo.Demands;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DemandsRepository extends MongoRepository<Demands,Integer> {
}
