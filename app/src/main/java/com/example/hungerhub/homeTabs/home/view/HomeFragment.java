package com.example.hungerhub.homeTabs.home.view;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

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
import com.example.hungerhub.homeTabs.LocalDataSource;
import com.example.hungerhub.homeTabs.MainTabsActivity;
import com.example.hungerhub.homeTabs.model.MealModel;

import com.example.hungerhub.homeTabs.home.presenter.HomePresenter;
import com.example.hungerhub.homeTabs.network.RemoteDataSource;
import com.example.hungerhub.Repo;
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview;

import java.util.List;


public class HomeFragment extends Fragment implements IhomeView {

     ImageView mealPhoto;
     TextView title;
     MealModel mealModel;
     CardView card;
     HomePresenter homePresenter;
     MyCarouselAdapter adapter;
     LinearLayoutManager linearLayoutManager;
     CarouselRecyclerview recyclerView;
     Group mainGroup ;
     Group netWorkFailedGroup;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homePresenter= new HomePresenter(new Repo(RemoteDataSource.getInstance(),
                LocalDataSource.getInstance(getActivity()),true),this,getContext());
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

        mainGroup= view.findViewById(R.id.group1);
        netWorkFailedGroup=view.findViewById(R.id.group2);
        recyclerView = view.findViewById(R.id.viewPager);
        linearLayoutManager= new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAlpha(true);
        recyclerView.setInfinite(false);
        homePresenter.loadData();
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

    @Override
    public void SetMeals(List<MealModel> meals) {
        adapter = new MyCarouselAdapter(getActivity(),meals,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onMealClicked(MealModel meal) {
        Navigation.findNavController(getView()).navigate(HomeFragmentDirections.actionHomeFragmentToDetailedMealFragment(meal));
    }

    @Override
    public void onNetworkDisconnected() {
        mainGroup.setVisibility(INVISIBLE);
        netWorkFailedGroup.setVisibility(VISIBLE);
    }

    @Override
    public void onNetworkConncted() {
        mainGroup.setVisibility(VISIBLE);
        netWorkFailedGroup.setVisibility(INVISIBLE);
    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainTabsActivity)getActivity()).showNavigationBar(true);
    }
}