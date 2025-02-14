package com.example.hungerhub.homeTabs.search.model.areaModels;

import java.util.List;

public class AreaResponse {
    List<AreaModel> meals;

    public AreaResponse(List<AreaModel> meals) {
        this.meals = meals;
    }

    public List<AreaModel> getMeals() {
        return meals;
    }

    public void setMeals(List<AreaModel> meals) {
        this.meals = meals;
    }
}
