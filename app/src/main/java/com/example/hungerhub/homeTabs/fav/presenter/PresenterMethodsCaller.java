package com.example.hungerhub.homeTabs.fav.presenter;

import com.example.hungerhub.homeTabs.model.MealModel;

import java.util.List;

public interface PresenterMethodsCaller {
    public void onSuccess(List<MealModel> meals);
    public void onFailure(String msg);
}
