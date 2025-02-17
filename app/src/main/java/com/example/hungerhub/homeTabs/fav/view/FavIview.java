package com.example.hungerhub.homeTabs.fav.view;

import com.example.hungerhub.homeTabs.model.MealModel;

import java.util.List;

public interface FavIview {
    public void setfavMeals(List<MealModel> meals);
    public void deleteFromFav(MealModel mealModel);
    public void SetMessage(String msg);
    public void onMealClicked(MealModel mealModel);
}
