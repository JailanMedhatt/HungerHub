package com.example.hungerhub.homeTabs.detailedItem.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hungerhub.R;
import com.example.hungerhub.homeTabs.LocalDataSource;
import com.example.hungerhub.homeTabs.model.MealModel;
import com.example.hungerhub.Repo;
import com.example.hungerhub.homeTabs.network.RemoteDataSource;
import com.example.hungerhub.homeTabs.detailedItem.presenter.ItemDetailsPresenter;

import java.util.List;

public class DetailedMealFragment extends Fragment implements DetailedMeal_iView {
    WebView webView;
    TextView title;
    TextView category;
    TextView instructions;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    MyRecyclerAdapter adapter;
    TextView area;
    ItemDetailsPresenter presenter;
    Button addToFavBtn;



    public DetailedMealFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new ItemDetailsPresenter(this,
                new Repo(RemoteDataSource.getInstance(),LocalDataSource.getInstance(getActivity()),true));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView= view.findViewById(R.id.video);
        instructions=view.findViewById(R.id.instructions);
        title= view.findViewById(R.id.title);
        addToFavBtn= view.findViewById(R.id.addToFav);
        area= view.findViewById(R.id.area);
        MealModel mealModel = DetailedMealFragmentArgs.fromBundle(getArguments()).getMeal();
        presenter.getItemDetails(mealModel.getIdMeal());
        category=view.findViewById(R.id.category);

        recyclerView = view.findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);



    }
    public void loadVideo(String videoId){

            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebChromeClient(new WebChromeClient());
            webView.loadData(videoId,"text/html","utf-8");
        }

    @Override
    public void onMessageReceived(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void setMeal(MealModel meal) {
        instructions.setText(meal.getStrInstructions());
        loadVideo(presenter.getVideoId(meal.getStrYoutube()));
        area.setText(meal.getStrArea());
        title.setText(meal.getTitle());
        category.setText(meal.getStrCategory());
        List<String> ingredients= presenter.getIngredientsList(meal);
        adapter = new MyRecyclerAdapter(getActivity(),ingredients);
        recyclerView.setAdapter(adapter);
        addToFavBtn.setOnClickListener(v->{
            presenter.addMealToFav(meal,getActivity());
        });
    }
}

