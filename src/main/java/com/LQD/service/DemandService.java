package com.LQD.service;

import com.LQD.entity.pojo.Demands;
import com.LQD.repository.DemandsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandService {

    @Autowired
    private DemandsRepository demandsRepository;

    public void createDemand(Demands demand) {
        demandsRepository.save(demand);  // Lưu demand vào MongoDB
    }

    // Phương thức lấy tất cả demands
    public List<Demands> getAllDemands() {
        return demandsRepository.findAll();  // Lấy tất cả demands
    }
}
