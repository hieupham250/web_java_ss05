package com.example.ss05.controller;

import com.example.ss05.model.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "ProductController", value = "/productController")
public class ProductController extends HttpServlet {
    private List<Product> products = new ArrayList<>();

    public void init() throws ServletException {
        products.add(new Product(1, "Laptop", 1500.0, "Laptop hiệu năng cao"));
        products.add(new Product(2, "Điện thoại", 800.0, "Điện thoại thông minh"));
        products.add(new Product(3, "Máy tính bảng", 600.0, "Thiết bị di động tiện lợi"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("productList", products);
        request.getRequestDispatcher("view/productList.jsp").forward(request, response);
    }
}