package com.example.degoogle.model;

import java.util.ArrayList;

public class AllCategories {

    ArrayList<CategoryChild> categoryChildren;
    String categoryTitle;

    public AllCategories(ArrayList<CategoryChild> categoryChildren, String categoryTitle) {
        this.categoryChildren = categoryChildren;
        this.categoryTitle = categoryTitle;
    }

    public ArrayList<CategoryChild> getCategoryChildren() {
        return categoryChildren;
    }

    public void setCategoryChildren(ArrayList<CategoryChild> categoryChildren) {
        this.categoryChildren = categoryChildren;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }
}
