package com.LQD.entity.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Document(collection = "demands")
public class Demands {

    @Id
    private String demandId;

    @DBRef
    private Users users;

    private String image;

    private String message;

    private double latitude;

    private double longitude;

    @DBRef
    private Users support;

    private double statusUser;

    private double statusSupport;

    // Getters and Setters
    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Users getSupport() {
        return support;
    }

    public void setSupport(Users support) {
        this.support = support;
    }

    public double getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(double statusUser) {
        this.statusUser = statusUser;
    }

    public double getStatusSupport() {
        return statusSupport;
    }

    public void setStatusSupport(double statusSupport) {
        this.statusSupport = statusSupport;
    }
}
