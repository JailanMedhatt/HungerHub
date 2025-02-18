package com.example.hungerhub.homeTabs.search.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.hungerhub.NetworkConnectivity.NetworkResponse;
import com.example.hungerhub.NetworkConnectivity.NetworkUtils;
import com.example.hungerhub.Repo;
import com.example.hungerhub.homeTabs.search.model.areaModels.AreaModel;
import com.example.hungerhub.homeTabs.search.model.categoryModels.CategoryModel;
import com.example.hungerhub.homeTabs.search.model.ingredientModels.IngredientModel;
import com.example.hungerhub.homeTabs.search.view.SearchResponseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenter implements NetworkResponse {
    Repo repo;
    SearchResponseHandler iview;
    List<CategoryModel> backupCategories= new ArrayList<>();
    List<IngredientModel> backupIngredients= new ArrayList<>();
    List<AreaModel> backupAreas=new ArrayList<>();
    boolean isIngredientSelcted=false;
    boolean isCategorySelcted=false;
    boolean isCountrySelected=false;
    Context context;

    public SearchPresenter(Repo repo, SearchResponseHandler iview, Context context) {
        this.repo = repo;
        this.iview = iview;
        this.context=context;


    }
    public  void isNetworkAccessed(){
        if( NetworkUtils.isInternetAvailable(context)){
            onNetworkConncted();
        }
        else {onNetworkDisconnected();}
        NetworkUtils.registerNetworkCallback(context,this);
    }
    public void getCatgories(){

        repo.getCategories().subscribeOn(Schedulers.io()).map(i->i.getCategories())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        list-> {
                            iview.setCategoriesList(list);
                            backupCategories=list;
                        }
                );
        isCategorySelcted=true;
        isCountrySelected=false;
        isIngredientSelcted=false;
        iview.onNetworkConncted();


    }
    public void getAreas(){

        repo.getAreas().subscribeOn(Schedulers.io()).map(i->i.getMeals())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        list-> {
                           iview.setAreasList(list);
                            backupAreas=list;
                        }
                );
        isCategorySelcted=false;
        isCountrySelected=true;
        isIngredientSelcted=false;
    }

    public void getIngredients(){

        repo.getIngredients().subscribeOn(Schedulers.io()).map(i->i.getMeals())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        list-> {
                           backupIngredients=list;
                           iview.setIngredientsList(list);
                        }
                );
        isIngredientSelcted=true;
        isCategorySelcted=false;
        isCountrySelected=false;
    }
    public void filterCategoriesList(String text){
        if(text==null|| TextUtils.isEmpty(text)){
            iview.setCategoriesList(backupCategories);
        }
        else{
            List<CategoryModel> filteredList= backupCategories.stream().filter(
                    category-> category.getStrCategory().toLowerCase().startsWith(text.toLowerCase().trim())
            ).collect(Collectors.toList());
            iview.setCategoriesList(filteredList);
        }

    }
    public void filterIngredientList(String text){
        if(text==null|| TextUtils.isEmpty(text)){
            iview.setIngredientsList(backupIngredients);
        }
        else{
            List<IngredientModel> filteredList= backupIngredients.stream().filter(
                    category-> category.getStrIngredient().toLowerCase().startsWith(text.toLowerCase().trim())
            ).collect(Collectors.toList());
            iview.setIngredientsList(filteredList);
        }

    }
    public void filterAreaList(String text){
        if(text==null|| TextUtils.isEmpty(text)){
            iview.setAreasList(backupAreas);
        }
        else{
            List<AreaModel> filteredList= backupAreas.stream().filter(
                    area-> area.getStrArea().toLowerCase().startsWith(text.toLowerCase().trim())
            ).collect(Collectors.toList());
            iview.setAreasList(filteredList);
        }

    }

    public void  filterList(String text){
        if(isCategorySelcted){
            filterCategoriesList(text);
        } else if (isIngredientSelcted) {
            filterIngredientList(text);
        }
        else if(isCountrySelected){
            filterAreaList(text);
        }
    }

    @Override
    public void onNetworkConncted() {
        iview.onNetworkConncted();

    }

    @Override
    public void onNetworkDisconnected() {
        iview.onNetworkDisconnected();

    }
}
