package com.example.degoogle.model;

import java.util.ArrayList;

public class CategoryChild {
    String mDescription;
    String mNames;
    String mImageUrls;

    public CategoryChild(String mDescription, String mNames, String mImageUrls) {
        this.mDescription = mDescription;
        this.mNames = mNames;
        this.mImageUrls = mImageUrls;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmNames() {
        return mNames;
    }

    public void setmNames(String mNames) {
        this.mNames = mNames;
    }

    public String getmImageUrls() {
        return mImageUrls;
    }

    public void setmImageUrls(String mImageUrls) {
        this.mImageUrls = mImageUrls;
    }
}
