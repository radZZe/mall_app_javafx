package com.example.mall_app_javafx.models;

public class Unit {
    Integer unitID;
    String value;

    public Unit(Integer unitID, String value) {
        this.unitID = unitID;
        this.value = value;
    }

    public Integer getUnitID() {
        return unitID;
    }

    public void setUnitID(Integer unitID) {
        this.unitID = unitID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
