package controller;

import DAO.CartDAO;
import model.Cart;
import model.Product;
import service.CartService;
import service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CartServlet", value = "/carts")
public class CartServlet extends HttpServlet {
    private final CartDAO cartDAO = new CartDAO();
    private final CartService cartService = CartService.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "delete":
                deleteCart(request, response);
                break;
            case "add":
                addToCart(request, response);
                break;
            case "updateQuantity":
                updateQuantity(request, response);
                break;
            default:
                findAll(request, response);
                break;
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
                createPost(response);
                break;
        }
    }
    private void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("carts", cartService.findAll());
        request.setAttribute("total", cartDAO.getTotalPrice());
       RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cart/home.jsp");
       requestDispatcher.forward(request, response);
    }
    private void createPost(HttpServletResponse response) throws IOException {
        response.sendRedirect("/carts");
    }

    private void deleteCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        if (cartService.checkById(id)) {
            cartService.deleteById(id);
            response.sendRedirect("/carts");
        } else {
            response.sendRedirect("/404.jsp");
        }
    }
    private void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cartService.addToCart(request);
        response.sendRedirect("/carts");
    }
    private void updateQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        if (quantity == 0) {
            response.sendRedirect("/carts");
        } else if (quantity > 0) {
           cartService.updateQuantity(request);
            response.sendRedirect("/carts");
        } else {
            cartService.updateQuantity(request);
            response.sendRedirect("/carts");
        }
    }
}
