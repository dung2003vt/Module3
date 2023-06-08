package service;

import model.Category;
import model.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductSevice {
    private final List<Product> products;
    private static ProductSevice productSevice;
    public ProductSevice(){
        products = new ArrayList<>();
        products.add(new Product(1L, "th", 5.5, LocalDate.now(), new Category(1L, "a")));
    }
    public static ProductSevice getInstance(){
        if (productSevice == null){
            productSevice = new ProductSevice();
        }
        return productSevice;
    }
    public List<Product> getProduct(){
        return products;
    }
    public void addProduct(Product product){ products.add(product);}
    public Product getById(Long id){
        for (Product product: products) {
            if(product.getId().equals(id)){
                return product;
            }
        }
        return null;
    }
    public void deleteById(Long id){
        Product product = getById(id);
        if (product != null){
            products.remove(product);
        }
    }
    public void deleteByCategory(Category category) {
        List<Product> productsDelete = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategory().equals(category)) {
                productsDelete.add(product);
            }
        }
        products.removeAll(productsDelete);
    }

    public List<Product> searchByName(String name) {
        List<Product> productsSearch = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().contains(name)) {
                productsSearch.add(product);
            }
        }
        return productsSearch;
    }
}
