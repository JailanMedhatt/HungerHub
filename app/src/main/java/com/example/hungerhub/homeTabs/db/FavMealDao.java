package com.example.hungerhub.homeTabs.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.hungerhub.homeTabs.model.MealModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface FavMealDao {
    @Query("SELECT * FROM meal_table WHERE uId = :uId")
    Observable<List<MealModel>> getAllFavMeals(String uId);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Completable insertMealToFav(MealModel mealModel);
    @Delete
    public Completable deleteMealFromFav(MealModel mealModel);

}
