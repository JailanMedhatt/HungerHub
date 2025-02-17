package com.example.hungerhub.homeTabs.fav.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hungerhub.R;
import com.example.hungerhub.Repo;
import com.example.hungerhub.homeTabs.LocalDataSource;
import com.example.hungerhub.homeTabs.commonView.CardIview;
import com.example.hungerhub.homeTabs.commonView.MyAdapter;
import com.example.hungerhub.homeTabs.model.MealModel;
import com.example.hungerhub.homeTabs.fav.presenter.Presenter;
import com.example.hungerhub.homeTabs.network.RemoteDataSource;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class FavFragment extends Fragment implements CardIview,FavIview{

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    MyAdapter adapter;
    List<MealModel>meals= new ArrayList<>();

    Presenter presenter;
    View view;

    public FavFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter= new Presenter( new Repo(RemoteDataSource.getInstance(),LocalDataSource.getInstance(getActivity()),true),this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        presenter.getAllFavProducts();
        recyclerView = view.findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyAdapter(getActivity(),meals,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void deleteFromFav(MealModel mealModel) {
        presenter.deleteMealFromFav(mealModel);
    }

    @Override
    public void setfavMeals(List<MealModel> meals) {
        adapter.setMeals(meals);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onMealClicked(MealModel mealModel) {
        Navigation.findNavController(getView()).navigate(FavFragmentDirections.actionFavFragmentToDetailedMealFragment(mealModel));
    }

    @Override
    public void SetMessage(String msg) {
        Snackbar.make(view,msg,Snackbar.LENGTH_LONG).show();
    }
}