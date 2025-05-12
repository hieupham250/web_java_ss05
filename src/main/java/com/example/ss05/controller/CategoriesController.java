package com.example.ss05.controller;

import com.example.ss05.model.Categories;
import com.example.ss05.service.CategoriesService;
import com.example.ss05.service.CategoriesServiceImp;

import java.io.*;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "CategoriesController", value = "/CategoriesController")
public class CategoriesController extends HttpServlet {
    private final CategoriesService categoriesService;

    public CategoriesController() {
        categoriesService = new CategoriesServiceImp();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("findAll")) {
            findAllCategories(request, response);
        } else if (action.equals("initUpdate")) {
            int catalogId = Integer.parseInt(request.getParameter("catalogId"));
            Categories catalog = categoriesService.findById(catalogId);
            if (catalog != null) {
                request.setAttribute("catalog", catalog);
                request.getRequestDispatcher("view/updateCatalog.jsp").forward(request, response);
            }
        } else if (action.equals("delete")) {
            int catalogId = Integer.parseInt(request.getParameter("catalogId"));
            boolean result = categoriesService.delete(catalogId);
            redirectBasedOnResult(result, request, response);
        }
    }

    public void findAllCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Categories> listCategories = categoriesService.findAll();
        request.setAttribute("listCategories", listCategories);
        request.getRequestDispatcher("view/categories.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action.equals("Create")) {
            Categories catalog = new Categories();
            catalog.setCatalogName(request.getParameter("catalogName"));
            catalog.setDescription(request.getParameter("description"));
            catalog.setStatus(Boolean.parseBoolean(request.getParameter("status")));
            boolean result = categoriesService.save(catalog);
            redirectBasedOnResult(result, request, response);
        }else if (action.equals("Update")) {
            Categories catalog = new Categories();
            catalog.setCatalogId(Integer.parseInt(request.getParameter("catalogId")));
            catalog.setCatalogName(request.getParameter("catalogName"));
            catalog.setDescription(request.getParameter("description"));
            catalog.setStatus(Boolean.parseBoolean(request.getParameter("status")));
            boolean result = categoriesService.update(catalog);
            redirectBasedOnResult(result, request, response);
        }
    }

    private void redirectBasedOnResult(boolean result, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (result) {
            findAllCategories(request, response);
        } else {
            request.getRequestDispatcher("view/error.jsp").forward(request, response);
        }
    }
}