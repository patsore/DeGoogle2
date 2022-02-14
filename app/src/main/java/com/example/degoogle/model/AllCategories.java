package com.example.degoogle.model;

import com.google.firebase.firestore.PropertyName;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
public class AllCategories {


    @SerializedName("categoryname")
    String categoryTitle;
    @PropertyName("Messaging Apps")
    @SerializedName("children")
    ArrayList<CategoryChild> categoryChildren;


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
