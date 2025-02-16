package com.example.hungerhub.homeTabs.search.model.ingredientModels;

import java.util.List;

public class IngredientResponse {
private List<IngredientModel> meals;

    public IngredientResponse(List<IngredientModel> meals) {
        this.meals = meals;
    }

    public List<IngredientModel> getMeals() {
        return meals;
    }

    public void setMeals(List<IngredientModel> meals) {
        this.meals = meals;
    }
}
