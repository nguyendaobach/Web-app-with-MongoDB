package com.LQD.service;

import com.LQD.entity.DemandsCreate;
import com.LQD.entity.UserPrinciple;
import com.LQD.entity.pojo.Demands;
import com.LQD.entity.pojo.Users;
import com.LQD.repository.DemandsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandService {

    @Autowired
    private DemandsRepository demandsRepository;

    public void createDemand(DemandsCreate demand) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Users users=userPrinciple.getUser();
        Demands demands=new Demands();
        demands.setImage(demand.getImage());
        demands.setLatitude(demand.getLatitude());
        demands.setLongitude(demand.getLongitude());
        demands.setMessage(demand.getMessage());
        demands.setStatusUser(0);
        demands.setStatusSupport(0);
        demands.setUsers(users);
        demandsRepository.save(demands);
    }

    public List<Demands> getAllDemands() {
        return demandsRepository.findAll();
    }
}
