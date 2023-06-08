package service;

import DAO.ProductDAO;
import model.Category;
import model.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ProductService {

    private final ProductDAO productDAO;
    private final CategoryService categoryService;
    private static ProductService productService;

    private ProductService() {
        productDAO = new ProductDAO();
        categoryService = CategoryService.getInstance();
    }

    public static ProductService getInstance() {
        if (productService == null) {
            productService = new ProductService();
        }
        return productService;
    }

    public List<Product> getProduct() {
        return productDAO.findAll();
    }

    public void save(HttpServletRequest request) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int categoryId = Integer.parseInt(request.getParameter("categories"));
        Category category = categoryService.getById(categoryId);
        if (id != null) {
            int idUpdate = Integer.parseInt(id);
            productDAO.updateProduct(new Product(idUpdate, name, price, quantity, category));
        } else {
            productDAO.addProduct(new Product(name, price, quantity, category));
        }
    }

    public Product getById(int id) {
        return productDAO.findById(id);
    }

    public void deleteById(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        productDAO.deleteById(id);
    }

    public List<Product> searchByName(HttpServletRequest request) {
        String search = request.getParameter("search");
        return productDAO.searchByName(search);
    }

    public boolean checkById(int id) {
        Product product = productDAO.findById(id);
        return product != null;
    }
}
