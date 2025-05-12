package com.example.ss05.dao;

import com.example.ss05.model.Categories;

import java.util.List;

public interface CategoriesDAO {
    List<Categories> findAll();
    boolean save(Categories catalog);
}
