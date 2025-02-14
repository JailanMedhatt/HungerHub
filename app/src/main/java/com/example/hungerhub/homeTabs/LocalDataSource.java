package com.example.hungerhub.homeTabs;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import com.example.hungerhub.SharedPref;
import com.example.hungerhub.homeTabs.db.AppDb;
import com.example.hungerhub.homeTabs.db.MealDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class LocalDataSource {
public  static  LocalDataSource localDataSource;
Context context;
 public MealDao dao;
private LocalDataSource(Context context){
    dao= AppDb.getInstance(context).getMealDao();
//    favouriteMeals=dao.getAlFavMeals();
    this.context=context;
}
public static LocalDataSource getInstance(Context context){
    if(localDataSource==null){
        localDataSource= new LocalDataSource(context);
    }
    return  localDataSource;
}
public Completable insertMealToFav(MealModel mealModel){
    return dao.insertMealToFav(mealModel);
}
public Completable deleteMealToFav(MealModel mealModel){
        return dao.deleteMealFromFav(mealModel);
    }
  public Observable<List<MealModel>> getFavouriteMeals(){
    return dao.getAllFavMeals(SharedPref.getInstance(context).getUSERID());
  }


}
