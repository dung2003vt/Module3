package com.example.baitap;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.baitap.StudentServlet.students;

@WebServlet(name = "StudentManage", value = "/creat")
public class StudentManage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        Student student = new Student(id,name);
        students.add(student);
        response.sendRedirect("/");
    }
}
