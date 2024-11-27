package com.pam.articleapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "articles")
public class ArticleModel {
    @PrimaryKey
    int id;
    String title;
    String content;

    public ArticleModel(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public ArticleModel() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    };

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}