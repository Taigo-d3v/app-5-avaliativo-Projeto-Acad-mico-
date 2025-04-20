package com.tiagogodinho.appavaliativo.RandomWord.Models;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<ValueItem> values = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ValueItem> getValues() {
        return values;
    }

    public void addValue(String value) {
        values.add(new ValueItem(value));
    }
}
