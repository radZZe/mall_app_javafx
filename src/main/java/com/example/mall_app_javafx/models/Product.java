package com.example.mall_app_javafx.models;

public class Product {
    Integer article;
    String name;
    Integer unitID;
    Integer price;

    public Product(Integer article, String name, Integer unitID, Integer price) {
        this.article = article;
        this.name = name;
        this.unitID = unitID;
        this.price = price;
    }

    public Integer getArticle() {
        return article;
    }

    public void setArticle(Integer article) {
        this.article = article;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUnitID() {
        return unitID;
    }

    public void setUnitID(Integer unitID) {
        this.unitID = unitID;
    }
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
