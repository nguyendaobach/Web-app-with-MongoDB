package com.LQD.controller;

import com.LQD.entity.pojo.Demands;
import com.LQD.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demands")
public class DemandController {

    @Autowired
    private DemandService demandService;

    // Endpoint để tạo một demand mới
    @PostMapping("/create")
    public String createDemand(@RequestBody Demands demand) {
        demandService.createDemand(demand);
        return "Demand created successfully!";
    }

    // Endpoint để lấy danh sách tất cả các demands
    @GetMapping("/all")
    public List<Demands> getAllDemands() {
        return demandService.getAllDemands();
    }
}
