package com.example.bai1;

import java.util.ArrayList;
import java.util.List;

public class ProductManage {
    private final List<Product> products;
    private static ProductManage productManage;
    private ProductManage(){
        products = new ArrayList<>();
        products.add(new Product(1,"Ip12prm",20,"gold",new Brand(1,"IP")));
        products.add(new Product(2,"SS12prm",19.9,"brown",new Brand(2,"SS")));
        products.add(new Product(3,"XM12prm",19.8,"gray",new Brand(3,"XM")));
    }
    public static ProductManage productManage(){
        if (productManage == null){
            productManage = new ProductManage();
        }
        return productManage;
    }
    public List<Product> getProducts(){
      return products;
    }
}
