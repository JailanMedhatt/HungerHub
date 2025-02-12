package com.example.hungerhub.homeTabs;

import com.example.hungerhub.FirebaseHelper;
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

  public Repo(LocalDataSource localDataSource,Boolean flag){
        this.localDataSource=localDataSource;
        this.isNetworkAccessed=flag;
        firebaseHelper= new FirebaseHelper();
    }
   public Completable insertMealTofav(MealModel mealModel){
      if(isNetworkAccessed){
          firebaseHelper.addMealToFireStore(mealModel);
      }
     return localDataSource.insertMealToFav(mealModel);

    }
    public Completable deleteMealFromFav(MealModel mealModel){
      return localDataSource.deleteMealToFav(mealModel);
    }
    public Observable<List<MealModel>> getAllFavProducts(){
     return localDataSource.getFavouriteMeals();
    }
    public Single<MealResponse> getDailyMeal(RemoteRandomMealDataSource dataSource){
      return dataSource.getDailyMeal();
    }
}
