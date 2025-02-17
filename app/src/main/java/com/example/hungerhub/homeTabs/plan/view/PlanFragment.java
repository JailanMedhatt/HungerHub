package com.example.hungerhub.homeTabs.plan.view;

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
import android.widget.Toast;

import com.example.hungerhub.R;
import com.example.hungerhub.Repo;
import com.example.hungerhub.homeTabs.LocalDataSource;
import com.example.hungerhub.homeTabs.model.MealModel;
import com.example.hungerhub.homeTabs.network.RemoteDataSource;
import com.example.hungerhub.homeTabs.plan.models.PlanMealModel;
import com.example.hungerhub.homeTabs.plan.presenter.PlanPresenter;

import java.util.List;


public class PlanFragment extends Fragment implements Planiview{
    RecyclerView rv;
    LinearLayoutManager linearLayoutManager;
    LinearLayoutManager linearLayoutManager2;
    CalenderAdapter calenderAdapter;
    PlanPresenter presenter;
    PlanAdapter adapter;
    RecyclerView mealsRv;

    public PlanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter= new PlanPresenter(new Repo(RemoteDataSource.getInstance(), LocalDataSource.getInstance(getActivity()),true),this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> week= presenter.getCurrentWeek();
        linearLayoutManager= new LinearLayoutManager(getActivity());
        linearLayoutManager2=new LinearLayoutManager(getActivity());
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv= view.findViewById(R.id.rv);
        mealsRv=view.findViewById(R.id.mealRv);
        mealsRv.setLayoutManager(linearLayoutManager2);
        rv.setLayoutManager(linearLayoutManager);
        calenderAdapter= new CalenderAdapter(getActivity(),week,this);
        rv.setAdapter(calenderAdapter);
    }

    @Override
    public void onDayClicked(String date) {
        presenter.getMealsByDate(date);

    }

    @Override
    public void setMealList(List<PlanMealModel> meals) {
        adapter= new PlanAdapter(getActivity(),meals,this);
        mealsRv.setAdapter(adapter);

    }

    @Override
    public void deleteMeal(PlanMealModel meal) {
        presenter.deleteMealFromPlan(meal);

    }

    @Override
    public void SetMessage(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMealClicked(MealModel mealModel) {
        Navigation.findNavController(getView()).navigate(PlanFragmentDirections.actionPlanFragmentToDetailedMealFragment(mealModel));
    }
}