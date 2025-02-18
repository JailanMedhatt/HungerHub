package com.example.hungerhub.homeTabs.search.FilteredMeals;
import android.text.TextUtils;
import com.example.hungerhub.homeTabs.model.MealModel;
import com.example.hungerhub.homeTabs.search.FilterObj;
import com.example.hungerhub.homeTabs.Repo;
import java.util.List;
import java.util.stream.Collectors;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
public class FilteredMealsPresenter {
    Repo repo;
    FilterMealsiview iview;
    List<MealModel> mealsBackUp;
    public FilteredMealsPresenter(Repo repo, FilterMealsiview iview) {
        this.repo = repo;
        this.iview = iview;
    }

    public  void getAllMealsByCat(String cat){
        repo.getMealsByCat(cat).subscribeOn(Schedulers.io()).map(
                i->i.meals
        ).observeOn(AndroidSchedulers.mainThread()).subscribe(
                list->{
                    mealsBackUp=list;
                    iview.setMealsList(list);
                });
    }
    public  void  getAllMealsByIng(String ing){
        repo.getMealsByIng(ing).subscribeOn(Schedulers.io()).map(
                i->i.meals
        ).observeOn(AndroidSchedulers.mainThread()).subscribe(
                list->{
                    mealsBackUp=list;
                    iview.setMealsList(list);
                }
        );
    }
    public  void  getAllMealsByArea(String area){
        repo.getMealsByArea(area).subscribeOn(Schedulers.io()).map(
                i->i.meals
        ).observeOn(AndroidSchedulers.mainThread()).subscribe(
                list->{
                    mealsBackUp=list;
                    iview.setMealsList(list);
                }
        );}

    public void getAllMeals(FilterObj filterObj){
        if(filterObj.isFilterByCat()){
            getAllMealsByCat(filterObj.getFilter());
        }
        else if(filterObj.isFilterByIngredients()){
            getAllMealsByIng(filterObj.getFilter());
        } else if (filterObj.isFilterByArea()) {
            getAllMealsByArea(filterObj.getFilter());

        }
    }
    public void filterMeals(String text){
        if(text==null|| TextUtils.isEmpty(text)){
            iview.setMealsList(mealsBackUp);
        }
        else{
          List<MealModel> filteredList= mealsBackUp.stream().filter(
                  meal-> meal.getTitle().toLowerCase().startsWith(text.toLowerCase().trim())
           ).collect(Collectors.toList());
            iview.setMealsList(filteredList);
        }

    }
}
