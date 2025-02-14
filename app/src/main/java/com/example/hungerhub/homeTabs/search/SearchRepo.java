package com.example.hungerhub.homeTabs.search;
import com.example.hungerhub.homeTabs.home.model.MealResponse;
import com.example.hungerhub.homeTabs.search.model.areaModels.AreaResponse;
import com.example.hungerhub.homeTabs.search.model.categoryModels.CategoryResponse;
import com.example.hungerhub.homeTabs.search.model.ingredientModels.IngredientResponse;
import com.example.hungerhub.homeTabs.search.network.RemoteSearchlDataSource;
import io.reactivex.rxjava3.core.Single;

public class SearchRepo {
    RemoteSearchlDataSource remoteSearchlDataSource;
    public SearchRepo(RemoteSearchlDataSource remoteSearchlDataSource) {
        this.remoteSearchlDataSource = remoteSearchlDataSource;
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
}
