package com.example.degoogle.model;

public class CategoryChild {

    Integer childId;
    String url;

    public CategoryChild(Integer childId, String url) {
        this.url = url;
        this.childId = childId;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }
}
