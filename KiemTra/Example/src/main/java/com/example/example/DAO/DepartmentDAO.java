package com.example.example.DAO;

import com.example.example.DAO.connection.MyConnection;
import com.example.example.model.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    private final Connection connection;
    private final String SELECT_ALL = "select * from department;";
    private final String SELECT_BY_ID = "select * from department where id = ?;";
    public DepartmentDAO() {
        connection = MyConnection.getConnection();
    }

    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Department department = new Department(id, name);
                departments.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    public Department findById(int id) {
        Department department = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                department = new Department(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return department;
    }

}
