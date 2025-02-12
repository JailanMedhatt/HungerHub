package com.example.hungerhub.homeTabs.selectedItem.presenter;
import android.content.Context;
import com.example.hungerhub.SharedPref;
import com.example.hungerhub.homeTabs.MealModel;
import com.example.hungerhub.homeTabs.MessageReciever;
import com.example.hungerhub.homeTabs.Repo;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ItemDetailsPresenter {
    Repo repo;
    SharedPref sharedPref;
    MessageReciever messageReciever;
    public ItemDetailsPresenter(Repo repo,MessageReciever messageReciever){
        this.repo=repo;
        this.messageReciever=messageReciever;


    }
 public List<String> getIngredientsList(MealModel mealModel){
    List <String> ingredientsList= Stream.of(mealModel.getStrIngredient1(),mealModel.getStrIngredient2(),
            mealModel.getStrIngredient3(),mealModel.getStrIngredient4(),mealModel.getStrIngredient5(),mealModel.getStrIngredient6(),
            mealModel.getStrIngredient7(),mealModel.getStrIngredient8(),mealModel.getStrIngredient9(),mealModel.getStrIngredient10(),
            mealModel.getStrIngredient11(),mealModel.getStrIngredient12(),mealModel.getStrIngredient13(),mealModel.getStrIngredient14(),
            mealModel.getStrIngredient15(),mealModel.getStrIngredient16(),mealModel.getStrIngredient17(),mealModel.getStrIngredient18(),
            mealModel.getStrIngredient19(),mealModel.getStrIngredient20()
            ).filter(s->s!=null&&!s.trim().isEmpty()).collect(Collectors.toList());
    return  ingredientsList;
}
public  void  addMealToFav(MealModel mealModel, Context context){
        MealModel meal=mealModel;
        sharedPref=SharedPref.getInstance(context);
        meal.setuId(sharedPref.getUSERID());
    repo.insertMealTofav(mealModel).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(()->messageReciever.onMessageReceived("Meal is added to fav"),
                    e->messageReciever.onMessageReceived(e.getMessage()));

}

    public String getVideoId(String videoUrl){
        String video="";
        if(videoUrl!=null &&videoUrl.contains("v=")){
            String videoId= videoUrl.substring(videoUrl.indexOf(videoUrl.substring(videoUrl.indexOf("v=")+2)));
             video= "<iframe width=\"570\" height=\"300\" src=\"https://www.youtube.com/embed/"+videoId+"\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>";
        }
        return video;
    }

}
