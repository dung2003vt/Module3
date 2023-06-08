package controller;

import service.CategoryService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CategoryServlet", value = "/categories")
public class CategoryServlet extends HttpServlet {
    private final CategoryService categoryService = CategoryService.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null){
            action = "";
        }
        switch (action){
            case "create":
                createGet(request,response);
                break;
            case "update":
                updateGet(request,response);
                break;
            case "delete":
                delete(request,response);
                break;
            default:
                findAdd(request,response);
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
                createPost(request,response);
                break;
            case "update":
                updatePost(request,response);
                break;
        }
    }
    private void findAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("category/home.jsp");
        request.setAttribute("categories",categoryService.findAll());
        requestDispatcher.forward(request,response);
    }
    private void createGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.sendRedirect("category/create.jsp");
    }
    private void createPost(HttpServletRequest request, HttpServletResponse response) throws  IOException{
        categoryService.save(request);
        response.sendRedirect("/categories");
    }
    private void updateGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int id = Integer.parseInt(request.getParameter("id"));
        if (categoryService.checkById(id)){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("category/update.jsp");
            request.setAttribute("categories",categoryService.getById(id));
            requestDispatcher.forward(request,response);
        } else {
            response.sendRedirect("404.jsp");
        }
    }
    private void updatePost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        int id = Integer.parseInt(request.getParameter("id"));
        if (categoryService.checkById(id)) {
            categoryService.save(request);
            response.sendRedirect("/categories");
        } else {
            response.sendRedirect("/404.jsp");
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
        int id = Integer.parseInt(request.getParameter("id"));
        if (categoryService.checkById(id)) {
            categoryService.deleteById(id);
            response.sendRedirect("/categories");
        } else {
            response.sendRedirect("/404.jsp");
        }
    }
}
