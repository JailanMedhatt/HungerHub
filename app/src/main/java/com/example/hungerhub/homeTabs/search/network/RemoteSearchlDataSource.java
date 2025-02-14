package com.example.hungerhub.homeTabs.search.network;

import com.example.hungerhub.homeTabs.home.model.MealResponse;
import com.example.hungerhub.homeTabs.search.model.areaModels.AreaResponse;
import com.example.hungerhub.homeTabs.search.model.categoryModels.CategoryResponse;
import com.example.hungerhub.homeTabs.search.model.ingredientModels.IngredientResponse;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteSearchlDataSource {
    Retrofit retrofit;
    static RemoteSearchlDataSource instance;
    SearchRetrofitHandler retrofitHandler;
    String baseUrl="https://www.themealdb.com/api/json/v1/1/";
  private RemoteSearchlDataSource(){
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava3CallAdapterFactory.create()).baseUrl(baseUrl).build();
        retrofitHandler= retrofit.create(SearchRetrofitHandler.class);
    }
    public static RemoteSearchlDataSource getInstance(){
      if(instance==null){
          instance= new RemoteSearchlDataSource();
      }
      return  instance;
    }
    public Single<CategoryResponse> getCategories(){
     return   retrofitHandler.getCategories();
    }
    public Single<AreaResponse> getAreas(){
        return   retrofitHandler.getAreas();
    }
    public Single<IngredientResponse> getIngredients(){
        return   retrofitHandler.getIngredients();
    }
    public Single<MealResponse>getMealsByCat(String catName){
      return retrofitHandler.getMealsByCategory(catName);
    }
    public Single<MealResponse>getMealsByIng(String ing){
        return retrofitHandler.getMealsByIngredient(ing);
    }
    public Single<MealResponse>getMealsByArea(String area){
        return retrofitHandler.getMealsByArea(area);
    }

}
