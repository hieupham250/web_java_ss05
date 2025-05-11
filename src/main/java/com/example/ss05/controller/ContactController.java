package com.example.ss05.controller;

import com.example.ss05.model.Contact;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "ContactController", value = "/contacts")
public class ContactController extends HttpServlet {
    private List<Contact> contacts = new ArrayList<>();
    private int nextId = 1;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "add":
                request.getRequestDispatcher("view/contactForm.jsp").forward(request, response);
                break;
            case "edit":
                int editId = Integer.parseInt(request.getParameter("id"));
                for (Contact c : contacts) {
                    if (c.getId() == editId) {
                        request.setAttribute("contact", c);
                        request.getRequestDispatcher("view/contactForm.jsp").forward(request, response);
                        return;
                    }
                }
                response.sendRedirect("contacts");
                break;
            default:
                request.setAttribute("contacts", contacts);
                request.getRequestDispatcher("view/contactList.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "create":
                Contact newContact = new Contact(
                        nextId++,
                        request.getParameter("firstName"),
                        request.getParameter("lastName"),
                        request.getParameter("email"),
                        request.getParameter("phone")
                );
                contacts.add(newContact);
                response.sendRedirect("contacts");
                break;

            case "update":
                int updateId = Integer.parseInt(request.getParameter("id"));
                for (Contact c : contacts) {
                    if (c.getId() == updateId) {
                        c.setFirstName(request.getParameter("firstName"));
                        c.setLastName(request.getParameter("lastName"));
                        c.setEmail(request.getParameter("email"));
                        c.setPhone(request.getParameter("phone"));
                        break;
                    }
                }
                response.sendRedirect("contacts");
                break;

            case "delete":
                int deleteId = Integer.parseInt(request.getParameter("id"));
                contacts.removeIf(c -> c.getId() == deleteId);
                response.sendRedirect("contacts");
                break;
        }
    }
}