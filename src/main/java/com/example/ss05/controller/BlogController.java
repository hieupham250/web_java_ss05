package com.example.ss05.controller;

import com.example.ss05.model.Post;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "BlogController", value = "/blog")
public class BlogController extends HttpServlet {
    private List<Post> posts = new ArrayList<>();

    public void init() {
        posts.add(new Post(1, "Bài viết đầu tiên", "Nội dung bài viết 1...", "Hiếu", "2025-05-11"));
        posts.add(new Post(2, "Bài viết thứ hai", "Nội dung bài viết 2...", "Lan", "2025-05-10"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String postIdParam = request.getParameter("id");

        if (postIdParam != null) {
            int postId = Integer.parseInt(postIdParam);
            for (Post p : posts) {
                if (p.getId() == postId) {
                    request.setAttribute("post", p);
                    request.getRequestDispatcher("view/postDetail.jsp").forward(request, response);
                    return;
                }
            }
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            request.setAttribute("posts", posts);
            request.getRequestDispatcher("view/postList.jsp").forward(request, response);
        }
    }
}