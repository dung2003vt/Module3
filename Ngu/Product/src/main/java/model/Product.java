package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Product {
    private Long id;
    private String name;
    private double price;
    private LocalDate date;
    private Category category;

    public Product() {
    }

    public Product(Long id, String name, double price, LocalDate date, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
