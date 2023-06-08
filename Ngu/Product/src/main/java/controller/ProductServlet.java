package controller;

import service.CategorySevice;
import service.ProductSevice;
import model.Category;
import model.Product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = "/products")
public class ProductServlet extends HttpServlet {
    private final ProductSevice productSevice = ProductSevice.getInstance();
    private final CategorySevice categorySevice = CategorySevice.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null){
            action = "";
        }
        switch (action) {
            case "create":
                createGet(request, response);
                break;
            case "update":
                updateGet(request, response);
                break;
            case "delete":
                delete(request, response);
                break;
            default:
                findAdd(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null){
            action = "";
        }
        switch (action) {
            case "create":
                createPost(request, response);
                break;
            case "update":
                updatePost(request, response);
                break;
            case "search":
                search(request, response);
                break;
        }
    }
    private void findAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products", productSevice.getProduct());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/product/home.jsp");
        requestDispatcher.forward(request,response);
    }
    private void createGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("categories", categorySevice.getCategories());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/product/create.jsp");
        requestDispatcher.forward(request, response);
    }
    private void createPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        LocalDate date = LocalDate.parse(request.getParameter("date"));
        Long categoryId = Long.parseLong(request.getParameter("categories"));

        Category category = categorySevice.getById(categoryId);
        if (category != null) {
            Product product = new Product(id, name, price, date, category);
            productSevice.addProduct(product);
            response.sendRedirect("/products");
        } else {
            response.sendRedirect("/404.jsp");
        }
    }

    private void updateGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Product product = productSevice.getById(id);
        if (product != null) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/product/update.jsp");
            request.setAttribute("products", product);
            request.setAttribute("categories", categorySevice.getCategories());
            requestDispatcher.forward(request, response);
        } else {
            response.sendRedirect("/404.jsp");
        }
    }

    private void updatePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        LocalDate date = LocalDate.parse(request.getParameter("date"));
        Long categoryId = Long.parseLong(request.getParameter("categories"));

        Category category = categorySevice.getById(categoryId);
        Product product = productSevice.getById(id);

        if (product != null && category != null) {
            product.setName(name);
            product.setPrice(price);
            product.setDate(date);
            product.setCategory(category);
            response.sendRedirect("/products");
        } else {
            response.sendRedirect("/404.jsp");
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        productSevice.deleteById(id);
        response.sendRedirect("/products");
    }

    private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        List<Product> products = productSevice.searchByName(search);
        request.setAttribute("products", products );
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/product/home.jsp");
        requestDispatcher.forward(request, response);
    }
}
