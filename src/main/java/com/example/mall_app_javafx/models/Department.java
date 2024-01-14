package com.example.mall_app_javafx.models;

public class Department {


    Integer departmentCode;

    String name;
    Integer managerID;

    public Integer getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(Integer departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getManagerID() {
        return managerID;
    }

    public void setManagerID(Integer managerID) {
        this.managerID = managerID;
    }


    public Department(Integer departmentCode, String name, Integer managerID){
        this.departmentCode = departmentCode;
        this.managerID = managerID;
        this.name = name;
    }




}
