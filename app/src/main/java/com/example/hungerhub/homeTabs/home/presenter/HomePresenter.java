package com.example.hungerhub.homeTabs.home.presenter;

import com.example.hungerhub.homeTabs.Repo;
import com.example.hungerhub.homeTabs.home.network.RemoteRandomMealDataSource;
import com.example.hungerhub.homeTabs.home.view.IhomeView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter {

Repo repo;
IhomeView ihomeView;
public HomePresenter(Repo repo, IhomeView ihomeView){
    this.repo=repo;
    this.ihomeView= ihomeView;
}
 public void getDailyMeal(RemoteRandomMealDataSource remoteRandomMealDataSource){
    repo.getDailyMeal(remoteRandomMealDataSource).subscribeOn(Schedulers.io()).map(
            l->l.meals
            )
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                    i->ihomeView.onSuccess(i.get(0))
            );
 }

}
