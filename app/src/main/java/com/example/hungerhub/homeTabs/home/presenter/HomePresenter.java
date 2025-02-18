package com.example.hungerhub.homeTabs.home.presenter;


import android.content.Context;

import com.example.hungerhub.NetworkConnectivity.NetworkResponse;
import com.example.hungerhub.NetworkConnectivity.NetworkUtils;
import com.example.hungerhub.homeTabs.home.view.IhomeView;
import com.example.hungerhub.homeTabs.Repo;
import com.example.hungerhub.homeTabs.model.MealModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
public class HomePresenter implements NetworkResponse {

Repo repo;
IhomeView ihomeView;
Context context;
public HomePresenter(Repo repo, IhomeView ihomeView,Context context){
    this.repo=repo;
    this.ihomeView= ihomeView;
    this.context=context;


}
 public void getDailyMeal(){
    repo.getDailyMeal().subscribeOn(Schedulers.io()).map(
            object->object.meals
            )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    list->ihomeView.onSuccess(list.get(0))
            );
 }

 public void getAllRandomMeals(){

     List<MealModel> mealList = new ArrayList<>();


             Single.defer(() -> repo.getDailyMeal())  // Ensures a fresh request each time
                     .repeat(10)
                     .subscribeOn(Schedulers.io()).map(obj->obj.meals)
                     .observeOn(AndroidSchedulers.mainThread())
                     .subscribe(
                             mealResponse -> {mealList.addAll(mealResponse);
                            ihomeView.SetMeals(mealList);}
                     );


 }

 public void loadData(){
    if(NetworkUtils.isInternetAvailable(context)){
    getDailyMeal();
    getAllRandomMeals();}
    else {
        ihomeView.onNetworkDisconnected();
    }
     NetworkUtils.registerNetworkCallback(context,this);
}

    @Override
    public void onNetworkConncted() {
    getDailyMeal();
    getAllRandomMeals();
    ihomeView.onNetworkConncted();
    }

    @Override
    public void onNetworkDisconnected() {
    ihomeView.onNetworkDisconnected();

    }
}
