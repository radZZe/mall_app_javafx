package com.example.mall_app_javafx.models;

public class Manager {


    Integer managerID;

    String manager;

    String phone;

    public Manager(Integer managerID, String manager, String phone) {
        this.managerID = managerID;
        this.manager = manager;
        this.phone = phone;
    }

    public Integer getManagerID() {
        return managerID;
    }

    public void setManagerID(Integer managerID) {
        this.managerID = managerID;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
