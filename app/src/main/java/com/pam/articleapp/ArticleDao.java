package com.pam.articleapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ArticleDao {
    @Insert
    void insertAll(List<ArticleModel> articles);

    @Query("SELECT * FROM articles")
    List<ArticleModel> getAllArticles();
}