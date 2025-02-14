package com.example.hungerhub.homeTabs.search.FilteredMeals;

import com.example.hungerhub.homeTabs.model.MealModel;

import java.util.List;

public interface FilterMealsiview {
    public void onMealClicked(MealModel mealModel);
    public void setMealsList(List<MealModel> meals);
}
