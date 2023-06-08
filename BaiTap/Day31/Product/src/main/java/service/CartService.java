package service;

import DAO.CartDAO;
import DAO.CategoryDAO;
import DAO.ProductDAO;
import controller.CartServlet;
import model.Cart;
import model.Category;
import model.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CartService {
    private final ProductDAO productDAO;
    private final CartDAO cartDAO;
    private static CartService cartService;
    private static ProductService productService;

    private CartService() {
        productDAO = new ProductDAO();
        cartDAO = new CartDAO();
        productService = ProductService.getInstance();
    }

    public static CartService getInstance() {
        if (cartService == null) {
            cartService = new CartService();
        }
        return cartService;
    }

    public List<Cart> findAll() {
        return cartDAO.findAll();
    }

    public Cart getById(int id) {
        return cartDAO.findById(id);
    }

    public void save(HttpServletRequest request) {
        String id = request.getParameter("id");
        int productId = Integer.parseInt(request.getParameter("productId"));
        Product product = productService.getById(productId);
        int quantity = Integer.parseInt("quantity");
        if (id != null) {
            int idUpdate = Integer.parseInt(id);
            cartDAO.updateCart(new Cart(idUpdate, product,quantity));
        } else {
            cartDAO.addCart(new Cart(product,quantity));
        }
    }

    public void deleteById(int id) {
        cartDAO.deleteById(id);
    }

    public boolean checkById(int id) {
        Cart cart = cartService.getById(id);
        return cart != null;
    }
    public void addToCart(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Product product = productService.getById(id);
        Cart cart = new Cart(product, quantity);
        boolean check = true;
        int cartId = -1;
        for (Cart c : CartDAO.getInstance().findAll()) {
            if (c.getProduct().getId() == product.getId()) {
                check = false;
                cartId = c.getId();
                break;           }
        }
        if (check) {
            cartDAO.addCart(cart);
        } else {
            cartDAO.updateCartQuantity(cartId, quantity);
        }
        productDAO.updateQuantityDecrease(id,quantity);
    }
    public void updateQuantity(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
       productDAO.updateQuantityDecrease(CartDAO.getInstance().findById(id).getProduct().getId(), quantity);
    }
}
