package com.example.mall_app_javafx.models;

public class ReportEntry {
    String name;
    String unit;
    Integer price;
    Integer amount;
    Double sum;

    public ReportEntry(String name, String unit, Integer price, Integer amount, Double sum) {
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.amount = amount;
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
