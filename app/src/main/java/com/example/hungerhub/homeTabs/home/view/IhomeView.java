package com.example.hungerhub.homeTabs.home.view;

import com.example.hungerhub.homeTabs.model.MealModel;

public interface IhomeView {
    public  void onSuccess(MealModel meal);
    public  void onFailure(String msg);
}
