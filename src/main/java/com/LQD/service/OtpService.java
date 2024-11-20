package com.LQD.service;

import com.LQD.entity.pojo.OTP;
import com.LQD.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OtpService {

    @Autowired
    OtpRepository otpRepository;
    public Integer generateOTP() {
        return (int) (Math.random() * 900000) + 100000;
    }

    @Transactional
    public void save(String email, int otp) {
        OTP otp1 = otpRepository.findByEmail(email);
        if(otp1!=null){
            otpRepository.delete(otp1);
        }
        OTP otp2 = new OTP(email,otp);
        otpRepository.save(otp2);
    }

    public OTP getOTPByEmail(String email) {
        return otpRepository.findByEmail(email);
    }
}
