package com.LQD.controller;

import com.LQD.entity.UserLogin;
import com.LQD.entity.pojo.Users;
import com.LQD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin user) {
        return userService.login(user);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody String email) {
        return userService.register(email);
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody Users user,@RequestParam int otp) {
        return userService.save(user,otp);
    }
}
