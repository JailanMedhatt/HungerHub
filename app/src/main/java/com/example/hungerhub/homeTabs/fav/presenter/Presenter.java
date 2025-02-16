package com.example.hungerhub.homeTabs.fav.presenter;

import com.example.hungerhub.Repo;
import com.example.hungerhub.homeTabs.fav.view.FavIview;
import com.example.hungerhub.homeTabs.model.MealModel;
import com.example.hungerhub.homeTabs.commonView.CardIview;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Presenter implements PresenterMethodsCaller{
    Repo repo;
    FavIview iview;

    public Presenter(Repo repo, FavIview iview){
        this.repo=repo;
        this.iview=iview;

    }
    public void deleteMealFromFav(MealModel mealModel){
        repo.deleteMealFromFav(mealModel).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(
                        ()-> iview.SetMessage("The meal is removed"),
                        t-> iview.SetMessage(t.getMessage())
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
