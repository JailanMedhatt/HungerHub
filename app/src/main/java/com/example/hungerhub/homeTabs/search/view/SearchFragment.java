package com.example.hungerhub.homeTabs.search.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import com.example.hungerhub.R;
import com.example.hungerhub.homeTabs.LocalDataSource;
import com.example.hungerhub.homeTabs.search.FilterObj;
import com.example.hungerhub.Repo;
import com.example.hungerhub.homeTabs.search.model.areaModels.AreaModel;
import com.example.hungerhub.homeTabs.search.model.categoryModels.CategoryModel;
import com.example.hungerhub.homeTabs.search.model.ingredientModels.IngredientModel;
import com.example.hungerhub.homeTabs.network.RemoteDataSource;
import com.example.hungerhub.homeTabs.search.presenter.SearchPresenter;
import com.example.hungerhub.homeTabs.search.view.recyclyerAdapters.AreaRecyclerAdapter;
import com.example.hungerhub.homeTabs.search.view.recyclyerAdapters.CategoryRecyclerAdapter;
import com.example.hungerhub.homeTabs.search.view.recyclyerAdapters.IngredientsRecyclerAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchResponseHandler{
    RecyclerView rc;
    GridLayoutManager layoutManager;
    CategoryRecyclerAdapter categoryRecyclerAdapter;
    AreaRecyclerAdapter areaRecyclerAdapter;
    EditText searchEditText;
    IngredientsRecyclerAdapter ingredientsRecyclerAdapter;

    View view;
    ChipGroup chipGroup;
    SearchPresenter presenter;


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter= new SearchPresenter(new Repo(RemoteDataSource.getInstance(), LocalDataSource.getInstance(getActivity()),true),this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chipGroup= view.findViewById(R.id.chipGroup);
        rc=view.findViewById(R.id.rv);
        layoutManager= new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false);
        rc.setLayoutManager(layoutManager);
        setUpFilterChips();
        searchEditText=view.findViewById(R.id.searchEditText);
        this.view=view;
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                presenter.filterList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
    private void setUpFilterChips() {

        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        if(chip.getText().toString().equals("Category")){
                            presenter.getCatgories();
                        }
                        else  if(chip.getText().toString().equals("Ingredient")){
                            presenter.getIngredients();

                        }
                        else if(chip.getText().toString().equals("Country")){
                            presenter.getAreas();
                        }
                        else {

                        }
                    }
                }
            }
            );
        }
    }

    @Override
    public void setIngredientsList(List<IngredientModel> ingredients) {
        ingredientsRecyclerAdapter= new IngredientsRecyclerAdapter(getActivity(),ingredients,this);
        rc.setAdapter(ingredientsRecyclerAdapter);
    }

    @Override
    public void setCategoriesList(List<CategoryModel> categories) {
        categoryRecyclerAdapter = new CategoryRecyclerAdapter(getActivity(),categories,this);
        rc.setAdapter(categoryRecyclerAdapter);
    }
    @Override
    public void setAreasList(List<AreaModel> areas) {
        areaRecyclerAdapter= new AreaRecyclerAdapter(getActivity(),areas,this);
        rc.setAdapter(areaRecyclerAdapter);
    }

    @Override
    public void onCategoryClicked(String cat) {
        FilterObj filterObj=new FilterObj(cat,true,false,false);
        Navigation.findNavController(view).navigate(SearchFragmentDirections.actionSearchFragmentToFilteredMealsFragment(filterObj));
    }

    @Override
    public void onIngredientClicked(String ing) {
        FilterObj filterObj=new FilterObj(ing,false,true,false);
        Navigation.findNavController(view).navigate(SearchFragmentDirections.actionSearchFragmentToFilteredMealsFragment(filterObj));

    }



    @Override
    public void onAreaClicked(String ing) {
        FilterObj filterObj=new FilterObj(ing,false,false,true);
        Navigation.findNavController(view).navigate(SearchFragmentDirections.actionSearchFragmentToFilteredMealsFragment(filterObj));
    }
}