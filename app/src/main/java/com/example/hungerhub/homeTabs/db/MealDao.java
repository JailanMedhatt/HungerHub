package com.example.hungerhub.homeTabs.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.hungerhub.homeTabs.MealModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface MealDao {
    @Query("select * from meal_table")
    Observable<List<MealModel>> getAlFavMeals();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public Completable insertMealToFav(MealModel mealModel);
    @Delete
    public Completable deleteMealFromFav(MealModel mealModel);

}
