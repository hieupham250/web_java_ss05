package com.example.ss05.model;

public class Post {
    private int id;
    private String title;
    private String content;
    private String author;
    private String publishDate;

    public Post(int id, String title, String content, String author, String publishDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.publishDate = publishDate;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public String getPublishDate() { return publishDate; }
}
