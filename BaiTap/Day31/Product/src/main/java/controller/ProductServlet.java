package controller;

import service.CategoryService;
import service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ProductServlet", value = "/products")
public class ProductServlet extends HttpServlet {
    private final ProductService productService = ProductService.getInstance();
    private final CategoryService categoryService = CategoryService.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
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
        if (action == null) {
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
        request.setAttribute("products", productService.getProduct());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/product/home.jsp");
        requestDispatcher.forward(request, response);
    }

    private void createGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("categories", categoryService.findAll());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/product/create.jsp");
        requestDispatcher.forward(request, response);
    }

    private void createPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int categoryId = Integer.parseInt(request.getParameter("categories"));
        if (categoryService.checkById(categoryId)) {
            productService.save(request);
            response.sendRedirect("/products");
        } else {
            response.sendRedirect("/404.jsp");
        }
    }

    private void updateGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        if (productService.checkById(id)) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/product/update.jsp");
            request.setAttribute("product", productService.getById(id));
            request.setAttribute("categories", categoryService.findAll());
            requestDispatcher.forward(request, response);
        } else {
            response.sendRedirect("/404.jsp");
        }
    }

    private void updatePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int classId = Integer.parseInt(request.getParameter("classes"));
        int id = Integer.parseInt(request.getParameter("id"));
        if (productService.checkById(id) && categoryService.checkById(classId)) {
            productService.save(request);
            response.sendRedirect("/products");
        } else {
            response.sendRedirect("/404.jsp");
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        productService.deleteById(request);
        response.sendRedirect("/products");
    }

    private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {;
        request.setAttribute("products", productService.searchByName(request));
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/product/home.jsp");
        requestDispatcher.forward(request, response);
    }
}
