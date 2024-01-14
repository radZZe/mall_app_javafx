package com.example.mall_app_javafx;

public class DataStorage {
    private static DataStorage instance;
    private Boolean isAdmin;
    private int idDepartment;

    private DataStorage() {
        isAdmin = false;
        idDepartment = 0;
    }

    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setIdDepartment(int idDepartment) {
        this.idDepartment = idDepartment;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public int getIdDepartment() {
        return idDepartment;
    }
}