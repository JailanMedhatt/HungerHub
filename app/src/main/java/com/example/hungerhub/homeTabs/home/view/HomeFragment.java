package com.example.hungerhub.homeTabs.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hungerhub.R;
import com.example.hungerhub.SharedPref;
import com.example.hungerhub.homeTabs.LocalDataSource;
import com.example.hungerhub.homeTabs.MealModel;
import com.example.hungerhub.homeTabs.Repo;
import com.example.hungerhub.homeTabs.fav.presenter.Presenter;
import com.example.hungerhub.homeTabs.home.network.RemoteRandomMealDataSource;
import com.example.hungerhub.homeTabs.home.presenter.HomePresenter;


public class HomeFragment extends Fragment implements IhomeView {

     ImageView mealPhoto;
     TextView title;
     MealModel mealModel;
     CardView card;
     HomePresenter homePresenter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePresenter= new HomePresenter(new Repo(LocalDataSource.getInstance(getActivity()),true,getActivity()),this);
//        SharedPref sharedPref= SharedPref.getInstance(getActivity());
//        sharedPref.setDataLoaded(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        if(savedInstanceState!=null){
//            mealModel= (MealModel) savedInstanceState.getSerializable("meal");
//        }
        homePresenter.getDailyMeal(RemoteRandomMealDataSource.getInstance());
       mealPhoto= view.findViewById(R.id.mealPhoto);
       title= view.findViewById(R.id.title);
       card= view.findViewById(R.id.card);
       card.setOnClickListener(v->{
           Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeFragmentToDetailedMealFragment(mealModel));
       });
    }
    //Navigation.findNavController(view).navigate(HomeDemoDirections.actionHomeDemoToSelectedItem2(productModel));

    @Override
    public void onSuccess(MealModel meal) {
        mealModel=meal;
        Toast.makeText(getActivity(),meal.getTitle(),Toast.LENGTH_LONG);
        Glide.with(getActivity()).
                load(meal.getImgUrl()).apply(new RequestOptions()).
            placeholder(R.drawable.ic_launcher_background).
            error(R.drawable.ic_launcher_foreground).into(mealPhoto);
           title.setText(meal.getTitle());
        Log.i("TAG", meal.getStrYoutube());
    }
  //https://www.themealdb.com/images/ingredients/Lime.png
    @Override
    public void onFailure(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG);
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putSerializable("meal",mealModel);
//    }
}