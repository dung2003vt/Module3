package controller;

import service.CategorySevice;
import service.ProductSevice;
import model.Category;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CategoryServlet", value = "/category")
public class CategoryServlet extends HttpServlet {
    private final CategorySevice categorySevice = CategorySevice.getInstance();
    private final ProductSevice productSevice = ProductSevice.getInstance();
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
        }
    }
    private void findAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/category/home.jsp");
        request.setAttribute("categories", categorySevice.getCategories());
        requestDispatcher.forward(request, response);
    }

    private void createGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/category/create.jsp");
    }

    private void createPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");

        Category category = new Category(id, name);
        categorySevice.addCategory(category);
        response.sendRedirect("/categories");
    }

    private void updateGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));

        Category category = categorySevice.getById(id);

        if (category != null) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/category/update.jsp");
            request.setAttribute("categories", category);
            requestDispatcher.forward(request, response);
        } else {
            response.sendRedirect("/404.jsp");
        }
    }

    private void updatePost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");

        Category category = categorySevice.getById(id);

        if (category != null) {
            category.setName(name);
            response.sendRedirect("/categories");
        } else {
            response.sendRedirect("/404.jsp");
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));

        Category category = categorySevice.getById(id);
        if (category != null) {
            categorySevice.deleteById(id);
            productSevice.deleteByCategory(category);
            response.sendRedirect("/categories");
        } else {
            response.sendRedirect("/404.jsp");
        }
    }
}
