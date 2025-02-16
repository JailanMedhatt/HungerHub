package com.example.hungerhub.homeTabs.home.presenter;


import com.example.hungerhub.homeTabs.home.view.IhomeView;
import com.example.hungerhub.Repo;
import com.example.hungerhub.homeTabs.model.MealModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
public class HomePresenter {

Repo repo;
IhomeView ihomeView;
public HomePresenter(Repo repo, IhomeView ihomeView){
    this.repo=repo;
    this.ihomeView= ihomeView;
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
     CompositeDisposable compositeDisposable = new CompositeDisposable();
     List<MealModel> mealList = new ArrayList<>();

     compositeDisposable.add(
             Single.defer(() -> repo.getDailyMeal())  // Ensures a fresh request each time
                     .repeat(10)  // Calls the API 10 times
                     .subscribeOn(Schedulers.io()).map(obj->obj.meals)
                     .observeOn(AndroidSchedulers.mainThread())
                     .subscribe(
                             mealResponse -> {mealList.addAll(mealResponse); // Collect results
                            ihomeView.SetMeals(mealList);}
                     )
     );

 }
}
