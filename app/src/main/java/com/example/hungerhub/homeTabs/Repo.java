package com.example.hungerhub.homeTabs;

import com.example.hungerhub.homeTabs.db.LocalDataSource;
import com.example.hungerhub.homeTabs.model.MealModel;
import com.example.hungerhub.homeTabs.model.MealResponse;
import com.example.hungerhub.homeTabs.network.FirebaseHelper;
import com.example.hungerhub.homeTabs.plan.models.PlanMealModel;
import com.example.hungerhub.homeTabs.search.model.areaModels.AreaResponse;
import com.example.hungerhub.homeTabs.search.model.categoryModels.CategoryResponse;
import com.example.hungerhub.homeTabs.search.model.ingredientModels.IngredientResponse;
import com.example.hungerhub.homeTabs.network.RemoteDataSource;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class Repo {
    RemoteDataSource remoteSearchlDataSource;
    LocalDataSource localDataSource;
    Boolean isNetworkAccessed;
    FirebaseHelper firebaseHelper;

    public Repo(RemoteDataSource remoteSearchlDataSource, LocalDataSource localDataSource, Boolean isNetworkAccessed) {
        this.remoteSearchlDataSource = remoteSearchlDataSource;
        this.localDataSource = localDataSource;
        this.isNetworkAccessed = isNetworkAccessed;
        firebaseHelper=new FirebaseHelper();
    }

    public Single<CategoryResponse> getCategories(){
        return  remoteSearchlDataSource.getCategories();
    };
    public Single<AreaResponse> getAreas(){
        return  remoteSearchlDataSource.getAreas();
    };
    public Single<IngredientResponse> getIngredients(){
        return  remoteSearchlDataSource.getIngredients();
    };
    public Single<MealResponse>getMealsByCat(String catName){
        return remoteSearchlDataSource.getMealsByCat(catName);
    }
    public Single<MealResponse>getMealsByIng(String ing){
        return remoteSearchlDataSource.getMealsByIng(ing);
    }
    public Single<MealResponse>getMealsByArea(String area){
        return remoteSearchlDataSource.getMealsByArea(area);
    }
    public Single<MealResponse>getMealById(String id){
        return remoteSearchlDataSource.getMealsById(id);
    }
    public Single<MealResponse> getDailyMeal(){
        return remoteSearchlDataSource.getDailyMeal();
    }
    //room and firbase
    public Completable insertMealTofav(MealModel mealModel){
        if(isNetworkAccessed){
            firebaseHelper.addMealToFireStore(mealModel);
        }
        return localDataSource.insertMealToFav(mealModel);

    }
    public Completable insertMealToLocalFav(MealModel mealModel){

        return localDataSource.insertMealToFav(mealModel);

    }
    public Completable deleteMealFromFav(MealModel mealModel){
        firebaseHelper.deleteMeal(mealModel);
        return localDataSource.deleteMealToFav(mealModel);
    }
    public Observable<List<MealModel>> getAllFavProducts(){
        return localDataSource.getFavouriteMeals();
    }

    public Observable<List<MealModel>> getAllFavFromFireStore(String uid){
        return firebaseHelper.getAllMeals(uid);
    }

    public Completable insertMealToPlan(PlanMealModel mealModel){
        firebaseHelper.addMealToPlanFireStore(mealModel);
        return  localDataSource.insertMealToPlan(mealModel);
    }
    public Completable insertMealToPlanLocal(PlanMealModel mealModel){

        return  localDataSource.insertMealToPlan(mealModel);
    }
    public Observable<List<PlanMealModel>> getAllplansFromFireStore(String uid){
        return firebaseHelper.getAllPlansMeals(uid);
    }

    public Completable deleteMealfromPlan(PlanMealModel mealModel){
        firebaseHelper.deleteMealFromPlan(mealModel);
        return  localDataSource.deleteMealfromPlan(mealModel);
    }
    public Observable<List<PlanMealModel>> getPlanMeals(String date){
        return  localDataSource.getPlanMeals(date);
    }
}
