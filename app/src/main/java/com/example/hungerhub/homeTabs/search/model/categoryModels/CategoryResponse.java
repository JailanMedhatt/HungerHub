package com.example.hungerhub.homeTabs.search.model.categoryModels;

import java.util.List;

public class CategoryResponse {
    List<CategoryModel> categories;

    public CategoryResponse(List<CategoryModel> categories) {
        this.categories = categories;
    }

    public List<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryModel> categories) {
        this.categories = categories;
    }
}
