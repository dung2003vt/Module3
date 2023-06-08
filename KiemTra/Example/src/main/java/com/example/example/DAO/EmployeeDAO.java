package com.example.example.DAO;

import com.example.example.DAO.connection.MyConnection;
import com.example.example.model.Department;
import com.example.example.model.Employee;
import com.example.example.service.DepartmentService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private final Connection connection;
    private final DepartmentService departmentService = DepartmentService.getInstance();
    private final String SELECT_ALL = "select * from employee;";
    private final String SELECT_BY_ID = "select * from employee where id = ?;";
    private final String INSERT_INTO = "insert into employee(name,email,address,phone_number,salary,department_id) value (?,?,?,?,?,?);";
    private final String UPDATE_BY_ID = "update employee set name = ?,email=?,address=?,phone_number=?,salary=?, department_id=? where id = ?;";
    private final String DELETE_BY_ID = "delete from employee where id = ?";
    private final String SEARCH_BY_NAME = "select * from employee where name like ?;";

    public EmployeeDAO() {
        connection = MyConnection.getConnection();
    }
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            convertResultSetToList(employees, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
    public Employee findById(int id) {
        Employee employee = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String names = resultSet.getString("name");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String phoneNumber= resultSet.getString("phone_number");
                double salary = resultSet.getDouble("salary");
                int department_id = resultSet.getInt("department_id");
                Department department = departmentService.getById(department_id);
                employee = new Employee(id, names,email,address, phoneNumber, salary,department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public void addEmployee(Employee employee) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO)) {
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setString(3, employee.getAddress());
            preparedStatement.setString(4, employee.getPhoneNumber());
            preparedStatement.setDouble(5, employee.getSalary());
            preparedStatement.setDouble(6,employee.getDepartment().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(Employee employee) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID)) {
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setString(3, employee.getAddress());
            preparedStatement.setString(4, employee.getPhoneNumber());
            preparedStatement.setDouble(5, employee.getSalary());
            preparedStatement.setInt(6,employee.getDepartment().getId());
            preparedStatement.setInt(7, employee.getId());
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

    public List<Employee> searchByName(String name) {
        List<Employee> employees = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BY_NAME)) {
            preparedStatement.setString(1, "%" + name + "%");
            convertResultSetToList(employees, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
    private void convertResultSetToList(List<Employee> employees, PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String names = resultSet.getString("name");
            String email = resultSet.getString("email");
            String address = resultSet.getString("address");
            String phoneNumber= resultSet.getString("phone_number");
            double salary = resultSet.getDouble("salary");
            int department_id = resultSet.getInt("department_id");
            Department department = departmentService.getById(department_id);
            Employee employee = new Employee(id,email, names, address, phoneNumber, salary,department);
            employees.add(employee);
        }
    }
}
