package com.example.hungerhub.homeTabs.plan.view;

import com.example.hungerhub.homeTabs.model.MealModel;
import com.example.hungerhub.homeTabs.plan.models.PlanMealModel;

import java.util.List;

public interface Planiview {
    public void onDayClicked(String date);
    public void setMealList(List<PlanMealModel> meals);
    public void deleteMeal(PlanMealModel meal);
    public void SetMessage(String msg);
    public  void onMealClicked(MealModel mealModel);
}
