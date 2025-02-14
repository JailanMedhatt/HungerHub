package com.example.hungerhub.homeTabs;

import android.content.Context;

import com.example.hungerhub.FirebaseHelper;
import com.example.hungerhub.SharedPref;
import com.example.hungerhub.homeTabs.home.model.MealResponse;
import com.example.hungerhub.homeTabs.home.network.RemoteRandomMealDataSource;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class Repo {
    Boolean isNetworkAccessed;
    LocalDataSource localDataSource;
    FirebaseHelper firebaseHelper;
    SharedPref sharedPref;

  public Repo(LocalDataSource localDataSource, Boolean flag, Context context){
        this.localDataSource=localDataSource;
        this.isNetworkAccessed=flag;
        firebaseHelper= new FirebaseHelper();
        sharedPref= SharedPref.getInstance(context);

    }
   public Completable insertMealTofav(MealModel mealModel){
      if(isNetworkAccessed){
          firebaseHelper.addMealToFireStore(mealModel);
      }
     return localDataSource.insertMealToFav(mealModel);

    }
    public Completable deleteMealFromFav(MealModel mealModel){
      firebaseHelper.deleteMeal(mealModel);
      return localDataSource.deleteMealToFav(mealModel);
    }
    public Observable<List<MealModel>> getAllFavProducts(){
      if(!sharedPref.isDataLoaded()){
          sharedPref.setDataLoaded(true);
          return firebaseHelper.getAllMeals(sharedPref.getUSERID());
      }
     return localDataSource.getFavouriteMeals();
    }
    public Single<MealResponse> getDailyMeal(RemoteRandomMealDataSource dataSource){
      return dataSource.getDailyMeal();
    }
}
