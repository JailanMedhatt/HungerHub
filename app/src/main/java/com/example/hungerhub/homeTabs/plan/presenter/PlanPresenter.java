package com.example.hungerhub.homeTabs.plan.presenter;

import com.example.hungerhub.Repo;
import com.example.hungerhub.homeTabs.model.MealModel;
import com.example.hungerhub.homeTabs.plan.models.PlanMealModel;
import com.example.hungerhub.homeTabs.plan.view.Planiview;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanPresenter {
    Repo repo;
    Planiview planiview;

    public PlanPresenter(Repo repo, Planiview planiview) {
        this.repo = repo;
        this.planiview = planiview;
    }

    public List<String> getCurrentWeek(){

        List<String> week= new ArrayList<>();
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM", Locale.getDefault());
        calendar.set(Calendar.DAY_OF_WEEK,calendar.get(Calendar.DAY_OF_WEEK));
        for (int i=0;i<7;i++){
            String dayName= sdf.format(calendar.getTime());
            week.add(dayName);
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }
        return  week;
    }
    public void getMealsByDate(String date){
       repo.getPlanMeals(date).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
              list-> planiview.setMealList(list));
    }

    public void deleteMealFromPlan(PlanMealModel mealModel){
        repo.deleteMealfromPlan(mealModel).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()->planiview.SetMessage("Meal is deleted"),
                        e->planiview.SetMessage(e.getMessage())
                );

    }

}
