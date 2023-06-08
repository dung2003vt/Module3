package com.example.baitap;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "StudentServlet", value = "/")
public class StudentServlet extends HttpServlet {
   public static List<Student> students = new ArrayList<>();
    Student student = new Student(1,"Dung");
    Student student1 = new Student(2,"Thai");

    @Override
    public void init() throws ServletException {
        students.add(student);
        students.add(student1);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        request.setAttribute("key",students);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("Home.jsp");
        requestDispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
