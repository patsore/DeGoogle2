package com.example.degoogle.model;

import androidx.recyclerview.widget.RecyclerView;

public class AllCategories {

    String categoryTitle;

    public AllCategories(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }
}
