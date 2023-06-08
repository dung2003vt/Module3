package com.example.bai1;

public class Product {
    private int id;
    private String name;
    private double price;
    private String color;
    private Brand brand;

    public Product() {
    }

    public Product(int id, String name, double price, String color, Brand brand) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.color = color;
        this.brand = brand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
