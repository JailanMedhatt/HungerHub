package com.example.hungerhub.homeTabs.home.view;

import com.example.hungerhub.homeTabs.model.MealModel;
import com.example.hungerhub.homeTabs.model.MealResponse;

import java.util.List;

public interface IhomeView {
    public  void onSuccess(MealModel meal);
    public  void onFailure(String msg);
    public void  SetMeals(List<MealModel> meals);
    public void onMealClicked(MealModel meal);
}
