package com.example.hungerhub.homeTabs.home.network;

import com.example.hungerhub.homeTabs.home.model.MealResponse;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteRandomMealDataSource {
    Retrofit retrofit;
   static RemoteRandomMealDataSource instance;
    RetrofitHandler retrofitHandler;
    String baseUrl="https://www.themealdb.com/api/json/v1/1/";
  private RemoteRandomMealDataSource(){
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava3CallAdapterFactory.create()).baseUrl(baseUrl).build();
        retrofitHandler= retrofit.create(RetrofitHandler.class);
    }
    public static RemoteRandomMealDataSource getInstance(){
      if(instance==null){
          instance= new RemoteRandomMealDataSource();
      }
      return  instance;
    }
    public Single<MealResponse> getDailyMeal(){
     return   retrofitHandler.getDailyMeal();
    }
}
