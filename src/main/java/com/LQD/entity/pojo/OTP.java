package com.LQD.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "otp")
@Getter
@Setter
@NoArgsConstructor
public class OTP {
    @Id
    private String id;

    private String email;

    private int otp;

    public OTP(String email, int otp) {
        this.email = email;
        this.otp = otp;
    }


}
