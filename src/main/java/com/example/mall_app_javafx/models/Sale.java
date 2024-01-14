package com.example.mall_app_javafx.models;

import java.util.Date;

public class Sale {
    Integer saleID;
    Integer article;
    Date date;

    Integer amount;
    Integer departmentCode;
    Integer sum;

    public Sale(Integer saleId, Integer article, Date date, Integer amount, Integer departmentCode, Integer sum) {
        this.saleID = saleId;
        this.article = article;
        this.date = date;
        this.amount = amount;
        this.departmentCode = departmentCode;
        this.sum = sum;
    }

    public Integer getSaleID() {
        return saleID;
    }

    public void setSaleID(Integer saleID) {
        this.saleID = saleID;
    }

    public Integer getArticle() {
        return article;
    }

    public void setArticle(Integer article) {
        this.article = article;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(Integer departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

}
