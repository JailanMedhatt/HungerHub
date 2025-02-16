package com.example.hungerhub.homeTabs;

import android.content.Context;

import com.example.hungerhub.SharedPref;
import com.example.hungerhub.homeTabs.db.AppDb;
import com.example.hungerhub.homeTabs.db.FavMealDao;
import com.example.hungerhub.homeTabs.db.PlanMealDao;
import com.example.hungerhub.homeTabs.model.MealModel;
import com.example.hungerhub.homeTabs.plan.models.PlanMealModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class LocalDataSource {
public  static  LocalDataSource localDataSource;
Context context;
 public FavMealDao favMealDao;
 public PlanMealDao planMealDao;
private LocalDataSource(Context context){
    favMealDao = AppDb.getInstance(context).getMealDao();
    planMealDao=AppDb.instance.getPlanMealDao();
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
    return favMealDao.insertMealToFav(mealModel);
}
public Completable deleteMealToFav(MealModel mealModel){
        return favMealDao.deleteMealFromFav(mealModel);
    }
  public Observable<List<MealModel>> getFavouriteMeals(){
    return favMealDao.getAllFavMeals(SharedPref.getInstance(context).getUSERID());
  }
  //
  public Completable insertMealToPlan(PlanMealModel mealModel){
    return  planMealDao.insertMealToPlan(mealModel);
  }
    public Completable deleteMealfromPlan(PlanMealModel mealModel){
    return  planMealDao.deleteMealFromPlan(mealModel);
    }
    public Observable<List<PlanMealModel>> getPlanMeals(String date){
    return  planMealDao.getAllplanMeals(SharedPref.getInstance(context).getUSERID(),date);
    }


}
