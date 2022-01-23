package com.example.degoogle.model;

import java.util.List;

public class AllCategories {

    String categoryTitle;
    String Url;

    public AllCategories(String categoryTitle, String Url) {
        this.categoryTitle = categoryTitle;
        this.Url = Url;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }
}
