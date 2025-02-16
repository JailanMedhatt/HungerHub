package com.example.hungerhub.homeTabs.network;

import com.example.hungerhub.homeTabs.model.MealResponse;
import com.example.hungerhub.homeTabs.search.model.areaModels.AreaResponse;
import com.example.hungerhub.homeTabs.search.model.categoryModels.CategoryResponse;
import com.example.hungerhub.homeTabs.search.model.ingredientModels.IngredientResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchRetrofitHandler {
    @GET("categories.php")
   public Single<CategoryResponse> getCategories();
    @GET("list.php?i=list")
    public Single<IngredientResponse> getIngredients();
    @GET("list.php?a=list")
    public Single<AreaResponse> getAreas();
    @GET("filter.php")
    public Single<MealResponse> getMealsByCategory(@Query("c") String catName);
    @GET("filter.php")
    public Single<MealResponse> getMealsByIngredient(@Query("i") String ingredient);
    @GET("filter.php")
    public Single<MealResponse> getMealsByArea(@Query("a") String area);
    @GET("lookup.php")
    public Single<MealResponse> getMealDetailsByid(@Query("i" ) String id);
   @GET("random.php")
   public Single<MealResponse> getDailyMeal();
}
