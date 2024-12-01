package com.LQD.entity;

import com.LQD.entity.pojo.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DemandsCreate {

    private String image;

    private String message;

    private double latitude;

    private double longitude;


}
