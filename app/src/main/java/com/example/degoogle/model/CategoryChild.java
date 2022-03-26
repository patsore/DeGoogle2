package com.example.degoogle.model;

import com.google.firebase.firestore.PropertyName;

public class CategoryChild {
    @PropertyName("description")
    String description;
    @PropertyName("name")
    String name;
    @PropertyName("icon")
    String icon;
    int appId;
    String category = "";
    String packageName = "";

    //Передавать айди и искать среди прочных
    public CategoryChild(){

    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public CategoryChild(String description, String name, String icon, int appId, String category) {
        this.description = description;
        this.name = name;
        this.icon = icon;
        this.appId = appId;
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
