package com.example.hungerhub.homeTabs.home.network;

import com.example.hungerhub.homeTabs.home.model.MealResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitHandler {
    @GET("random.php")
   public Single<MealResponse> getDailyMeal();
}
