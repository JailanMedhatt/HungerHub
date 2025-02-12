package com.example.hungerhub.homeTabs.fav.presenter;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.hungerhub.homeTabs.MealModel;
import com.example.hungerhub.homeTabs.Repo;
import com.example.hungerhub.homeTabs.fav.view.FavIview;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Presenter implements PresenterMethodsCaller{
    Repo repo;
    FavIview iview;
    public Presenter(Repo repo,FavIview iview){
        this.repo=repo;
        this.iview=iview;
    }
    public void deleteMealFromFav(MealModel mealModel){
        repo.deleteMealFromFav(mealModel).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(
                        ()->iview.SetMessage("The meal is removed"),
                        t->iview.SetMessage(t.getMessage())
                );
    }
    public void getAllFavProducts(){
        repo.getAllFavProducts().subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(
                        list->iview.setfavMeals(list)
                );
    }

    @Override
    public void onSuccess(List<MealModel> meals) {

    }

    @Override
    public void onFailure(String msg) {

    }
}
