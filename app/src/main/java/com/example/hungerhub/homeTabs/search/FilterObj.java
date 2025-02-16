package com.example.hungerhub.homeTabs.search;

import java.io.Serializable;

public class FilterObj implements Serializable {
    String filter;
    boolean filterByCat;
    boolean filterByIngredients;
    boolean filterByArea;

    public FilterObj(String filter, boolean filterByCat, boolean filterByIngredients, boolean filterByArea) {
        this.filter = filter;
        this.filterByCat = filterByCat;
        this.filterByIngredients = filterByIngredients;
        this.filterByArea = filterByArea;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public boolean isFilterByCat() {
        return filterByCat;
    }

    public void setFilterByCat(boolean filterByCat) {
        this.filterByCat = filterByCat;
    }

    public boolean isFilterByIngredients() {
        return filterByIngredients;
    }

    public void setFilterByIngredients(boolean filterByIngredients) {
        this.filterByIngredients = filterByIngredients;
    }

    public boolean isFilterByArea() {
        return filterByArea;
    }

    public void setFilterByArea(boolean filterByArea) {
        this.filterByArea = filterByArea;
    }
}
