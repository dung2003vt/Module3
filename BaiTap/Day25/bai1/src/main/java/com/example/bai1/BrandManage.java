package com.example.bai1;

import java.util.ArrayList;
import java.util.List;

public class BrandManage {
    private final List<Brand> brands;
    private static BrandManage brandManage;
    private BrandManage(){
        brands = new ArrayList<>();
        brands.add(new Brand(1,"IP"));
        brands.add(new Brand(2,"SS"));
        brands.add(new Brand(3,"XM"));
    }
    public static BrandManage getInstance() {
        if (brandManage == null) {
            brandManage = new BrandManage();
        }
        return brandManage;
    }
    public List<Brand> getBrands() {
        return brands;
    }
}
