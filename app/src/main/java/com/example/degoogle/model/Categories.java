package com.example.degoogle.model;

import java.util.ArrayList;

public class Categories {

    ArrayList<CategoryNames> values;

    public Categories(){

    }
    public Categories(ArrayList<CategoryNames> values) {
        this.values = values;
    }

    public ArrayList<CategoryNames> getValues() {
        return values;
    }

    public void setValues(ArrayList<CategoryNames> values) {
        this.values = values;
    }
}
