package DAO;


import DAO.connection.MyConnection;
import model.Cart;
import model.Product;
import service.CategoryService;
import service.ProductService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    private static Connection connection;
    private static CartDAO cartDAO;
    private final ProductService productService = ProductService.getInstance();
    private final String SELECT_ALL = "select * from cart;";
    private final String INSERT_INTO = "insert into cart(product_id,quantity) value (?);";
    private final String UPDATE_BY_ID = "update cart set product_id = ?, quantity = ? where id = ?;";
    private final String DELETE_BY_ID = "delete from cart where id = ?";
    private static final String TOTAL = "select sum(price * cart.quantity) as total_price from cart join product p on p.id = cart.product_id;";
    private final String QUANTITY = "update cart set quantity = quantity + ? where id = ? ";

    public CartDAO() {
        connection = MyConnection.getConnection();
    }

    public static CartDAO getInstance() {
        if (cartDAO == null) {
            cartDAO = new CartDAO();
        }
        return cartDAO;
    }

    public List<Cart> findAll() {
        List<Cart> cartList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int productId = resultSet.getInt("product_id");
                Product product = productService.getById(productId);
                int quantity = resultSet.getInt("quantity");
                cartList.add(new Cart(id, product, quantity));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cartList;
    }

    public Cart findById(int id) {
        Cart cart = null;
        String query = "select * from cart where id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                Product product = productService.getById(productId);
                int quantity = resultSet.getInt("quantity");
                cart = new Cart(id, product, quantity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cart;
    }

    public void addCart(Cart cart) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO)) {
            preparedStatement.setInt(1, cart.getProduct().getId());
            preparedStatement.setInt(2, cart.getQuantity());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCart(Cart cart) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID)) {
            preparedStatement.setInt(1, cart.getProduct().getId());
            preparedStatement.setInt(2, cart.getQuantity());
            preparedStatement.setInt(3, cart.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double getTotalPrice() {
        double totalPrice = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(TOTAL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                totalPrice = resultSet.getDouble("total_price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalPrice;
    }

    public void updateCartQuantity(int id, int quantity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUANTITY)) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
