package com.example.hungerhub.homeTabs.search.chosenMeals.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hungerhub.R;
import com.example.hungerhub.homeTabs.MealModel;
import com.example.hungerhub.homeTabs.Repo;
import com.example.hungerhub.homeTabs.fav.presenter.Presenter;
import com.example.hungerhub.homeTabs.fav.view.MyAdapter;
import com.example.hungerhub.homeTabs.search.FilterObj;
import com.example.hungerhub.homeTabs.search.SearchRepo;
import com.example.hungerhub.homeTabs.search.chosenMeals.ChosenMealsPresenter;
import com.example.hungerhub.homeTabs.search.chosenMeals.FilterMealsiview;
import com.example.hungerhub.homeTabs.search.network.RemoteSearchlDataSource;
import com.example.hungerhub.homeTabs.selectedItem.view.DetailedMealFragmentArgs;

import java.util.List;

public class FilteredMealsFragment extends Fragment implements FilterMealsiview {

    RecyclerView rc;
    LinearLayoutManager layoutManager;
    EditText searchEditText;
    MealsAdapter adapter;
    TextView pageTitle;
    FilterObj filter;
    ChosenMealsPresenter presenter;
    public FilteredMealsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     filter = FilteredMealsFragmentArgs.fromBundle(getArguments()).getFilterObj();
     presenter=new ChosenMealsPresenter(new SearchRepo(RemoteSearchlDataSource.getInstance()),this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filtered_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pageTitle=view.findViewById(R.id.titleFilter);
        presenter.getAllMeals(filter);
        rc=view.findViewById(R.id.rv);
        layoutManager= new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rc.setLayoutManager(layoutManager);
        searchEditText=view.findViewById(R.id.searchEditText);
      setTitle(filter);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              presenter.filterMeals(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    public  void  setTitle(FilterObj filterObj){
        if(filterObj.isFilterByIngredients()){
            pageTitle.setText("Meals that include "+filterObj.getFilter());
        } else if (filterObj.isFilterByCat()||filterObj.isFilterByArea()) {
            pageTitle.setText(filterObj.getFilter()+" Meals");
        }
    }

    @Override
    public void onMealClicked(MealModel mealModel) {

    }

    @Override
    public void setMealsList(List<MealModel> meals) {
        adapter= new MealsAdapter(getActivity(),meals,this);
        rc.setAdapter(adapter);

    }
}