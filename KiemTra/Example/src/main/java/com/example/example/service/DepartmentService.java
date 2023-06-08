package com.example.example.service;

import com.example.example.DAO.DepartmentDAO;
import com.example.example.model.Department;

import java.util.List;

public class DepartmentService {
    private final DepartmentDAO departmentDAO;
    private static DepartmentService departmentService;

    private DepartmentService() {
        departmentDAO = new DepartmentDAO();
    }

    public static DepartmentService getInstance() {
        if (departmentService == null) {
            departmentService = new DepartmentService();
        }
        return departmentService;
    }

    public List<Department> findAll() {
        return departmentDAO.findAll();
    }
    public Department getById(int id) {
        return departmentDAO.findById(id);
    }
    public boolean checkById(int id) {
        Department department = departmentService.getById(id);
        return department != null;
    }
}
