package com.example.hungerhub.homeTabs.fav.view;

import com.example.hungerhub.homeTabs.MealModel;

import java.util.List;

public interface FavIview {
    public void deleteFromFav(MealModel mealModel);
    public void setfavMeals(List<MealModel> meals);
    public void SetMessage(String msg);
}
