package com.example.ss05.controller;

import com.example.ss05.model.Task;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "TaskController", value = "/tasks")
public class TaskController extends HttpServlet {
    private ArrayList<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add":
                request.getRequestDispatcher("view/taskForm.jsp").forward(request, response);
                break;
            case "edit":
                int editId = Integer.parseInt(request.getParameter("id"));
                for (Task task : tasks) {
                    if (task.getId() == editId) {
                        request.setAttribute("task", task);
                        break;
                    }
                }
                request.getRequestDispatcher("view/taskForm.jsp").forward(request, response);
                break;
            default:
                request.setAttribute("tasks", tasks);
                request.getRequestDispatcher("view/taskList.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        switch (action) {
            case "create":
                Task newTask = new Task(
                        nextId++,
                        request.getParameter("description"),
                        request.getParameter("dueDate"),
                        request.getParameter("completed") != null
                );
                tasks.add(newTask);
                response.sendRedirect("tasks");
                break;
            case "update":
                int id = Integer.parseInt(request.getParameter("id"));
                for (Task task : tasks) {
                    if (task.getId() == id) {
                        task.setDescription(request.getParameter("description"));
                        task.setDueDate(request.getParameter("dueDate"));
                        task.setCompleted(request.getParameter("completed") != null);
                        break;
                    }
                }
                response.sendRedirect("tasks");
                break;
            case "delete":
                int deleteId = Integer.parseInt(request.getParameter("id"));
                tasks.removeIf(task -> task.getId() == deleteId);
                response.sendRedirect("tasks");
                break;
        }
    }
}