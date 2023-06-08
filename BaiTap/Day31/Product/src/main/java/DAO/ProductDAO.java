package DAO;

import model.Category;
import service.CategoryService;
import DAO.connection.MyConnection;
import model.Product;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProductDAO {
    private final Connection connection;
    private final CategoryService categoryService = CategoryService.getInstance();
    private final String SELECT_ALL = "select * from product;";
    private final String SELECT_BY_ID = "select * from product where id = ?;";
    private final String INSERT_INTO = "insert into product(name,price,quantity,category_id) value (?,?,?,?);";
    private final String UPDATE_BY_ID = "update product set name = ?,price=?,quantity=?,category_id=? where id = ?;";
    private final String DELETE_BY_ID = "delete from product where id = ?";
    private final String SEARCH_BY_NAME = "select * from product where name like ?;";
    private final String QUANTITY_INCRESE = "update product set quantity = quantity + ? where id = ?";
    private final String QUANTITY_DECRESE = "update product set quantity = quantity - ? where id = ?";

    public ProductDAO() {
        connection = MyConnection.getConnection();
    }
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            convertResultSetToList(products, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    public Product findById(int id) {
        Product product = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String names = resultSet.getString("name");
                int price = resultSet.getInt("price");
                int quantity = resultSet.getInt("quantity");
                int categoryId = resultSet.getInt("category_id");
                Category category = categoryService.getById(categoryId);
                product = new Product(id, names,price, quantity, category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public void addProduct(Product product) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setInt(4, product.getCategory().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setInt(4, product.getCategory().getId());
            preparedStatement.setInt(5, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> searchByName(String name) {
        List<Product> productList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BY_NAME)) {
            preparedStatement.setString(1, "%" + name + "%");
            convertResultSetToList(productList, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
    private void convertResultSetToList(List<Product> productList, PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String names = resultSet.getString("name");
            int price = resultSet.getInt("price");
            int quantity = resultSet.getInt("quantity");
            int categoryId = resultSet.getInt("category_id");
            Category category = categoryService.getById(categoryId);
            Product product = new Product(id, names, price, quantity, category);
            productList.add(product);
        }
    }
    public void updateQuantityDecrease(int id, int quantity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUANTITY_DECRESE)) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateQuantityIncrease(int id, int quantity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUANTITY_INCRESE)) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
