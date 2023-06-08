package com.example.example.service;

import com.example.example.DAO.EmployeeDAO;
import com.example.example.model.Department;
import com.example.example.model.Employee;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class EmployeeService {
    private final EmployeeDAO employeeDAO;
    private static EmployeeService employeeService;
    private final DepartmentService departmentService;

    private EmployeeService() {
        employeeDAO = new EmployeeDAO();
        departmentService = DepartmentService.getInstance();
    }

    public static EmployeeService getInstance() {
        if (employeeService == null) {
            employeeService = new EmployeeService();
        }
        return employeeService;
    }

    public List<Employee> getEmployee() {
        return employeeDAO.findAll();
    }

    public void save(HttpServletRequest request) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phoneNumber");
        double salary = Double.parseDouble(request.getParameter("salary"));
        int departmentId = Integer.parseInt(request.getParameter("departments"));
        Department department = departmentService.getById(departmentId);
        if (id != null) {
            int idUpdate = Integer.parseInt(id);
            employeeDAO.updateEmployee(new Employee(idUpdate, name,email, address,phoneNumber, salary, department));
        } else {
            employeeDAO.addEmployee(new Employee(name,email, address, phoneNumber, salary,department));
        }
    }

    public Employee getById(int id) {
        return employeeDAO.findById(id);
    }

    public void deleteById(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        employeeDAO.deleteById(id);
    }

    public List<Employee> searchByName(HttpServletRequest request) {
        String search = request.getParameter("search");
        return employeeDAO.searchByName(search);
    }

    public boolean checkById(int id) {
        Employee employee = employeeDAO.findById(id);
        return employee != null;
    }
}
