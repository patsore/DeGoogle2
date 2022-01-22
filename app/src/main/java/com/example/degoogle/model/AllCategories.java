package com.example.degoogle.model;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AllCategories {

    String categoryTitle;
    List<CategoryChild> categoryChildList;

    public AllCategories(String categoryTitle, List<CategoryChild> categoryChildList) {
        this.categoryTitle = categoryTitle;
        this.categoryChildList = categoryChildList;
    }

    public List<CategoryChild> getCategoryChildList() {
        return categoryChildList;
    }

    public void setCategoryChildList(List<CategoryChild> categoryChildList) {
        this.categoryChildList = categoryChildList;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }
}
