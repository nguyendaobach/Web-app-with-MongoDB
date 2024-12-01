package com.LQD.controller;

import com.LQD.entity.DemandsCreate;
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

    @PostMapping("/create")
    public String createDemand(@RequestBody DemandsCreate demand) {
        demandService.createDemand(demand);
        return "Demand created successfully!";
    }

    @GetMapping("/all")
    public List<Demands> getAllDemands() {
        return demandService.getAllDemands();
    }
}
