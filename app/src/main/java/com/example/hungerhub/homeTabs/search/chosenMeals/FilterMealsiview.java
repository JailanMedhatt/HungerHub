package com.example.hungerhub.homeTabs.search.chosenMeals;

import com.example.hungerhub.homeTabs.MealModel;

import java.util.List;

public interface FilterMealsiview {
    public void onMealClicked(MealModel mealModel);
    public void setMealsList(List<MealModel> meals);
}
