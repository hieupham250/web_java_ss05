package com.example.ss05.controller;

import com.example.ss05.model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "StudentController", value = "/studentController")
public class StudentController extends HttpServlet {
    private static final List<Student> students = new ArrayList<>();
    private static final int STUDENTS_PER_PAGE = 5;

    @Override
    public void init() {
        students.add(new Student(1, "Nguyễn Văn A", 20, "Hà Nội"));
        students.add(new Student(2, "Trần Thị B", 22, "Đà Nẵng"));
        students.add(new Student(3, "Lê Văn C", 19, "TP. HCM"));
        students.add(new Student(4, "Phạm Thị D", 21, "Hải Phòng"));
        students.add(new Student(5, "Ngô Quang E", 23, "Cần Thơ"));
        students.add(new Student(6, "Bùi Thiên F", 24, "Bình Dương"));
        students.add(new Student(7, "Hoàng Minh G", 25, "Long An"));
        students.add(new Student(8, "Đặng Thu H", 26, "Quảng Ninh"));
        students.add(new Student(9, "Vũ Hoàng I", 27, "Thái Nguyên"));
        students.add(new Student(10, "Lý Minh J", 28, "Quảng Nam"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");

        if (loggedIn == null || !loggedIn) {
            response.sendRedirect("view/login.jsp");
            return;
        }

        String action = request.getParameter("action");

        int currentPage = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            currentPage = Integer.parseInt(pageParam);
        }

        int totalStudents = students.size();
        int totalPages = (int) Math.ceil((double) totalStudents / STUDENTS_PER_PAGE);

        if (currentPage > totalPages) {
            currentPage = totalPages;
        } else if (currentPage < 1) {
            currentPage = 1;
        }

        int startIndex = (currentPage - 1) * STUDENTS_PER_PAGE;
        int endIndex = Math.min(startIndex + STUDENTS_PER_PAGE, totalStudents);
        List<Student> studentsOnPage = students.subList(startIndex, endIndex);

        request.setAttribute("students", studentsOnPage);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Student student = getStudentById(id);
            if (student != null) {
                request.setAttribute("student", student);
                request.getRequestDispatcher("view/editStudent.jsp").forward(request, response);
            }
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            students.removeIf(student -> student.getId() == id);
            response.sendRedirect("studentController");
        } else {
            request.getRequestDispatcher("view/studentList.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String age = request.getParameter("age");
        String address = request.getParameter("address");
        String studentId = request.getParameter("id");

        if (studentId != null && !studentId.isEmpty()) {
            int id = Integer.parseInt(studentId);
            Student student = getStudentById(id);
            if (student != null) {
                student.setName(name);
                student.setAge(Integer.parseInt(age));
                student.setAddress(address);
            }
        } else {
            int newId = students.size() + 1;
            students.add(new Student(newId, name, Integer.parseInt(age), address));
        }
        response.sendRedirect("studentController");
    }


    private Student getStudentById(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }
}