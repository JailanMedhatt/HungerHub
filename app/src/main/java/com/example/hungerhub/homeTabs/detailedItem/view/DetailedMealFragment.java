package com.example.hungerhub.homeTabs.detailedItem.view;

import android.app.DatePickerDialog;
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
import com.example.hungerhub.SharedPref;
import com.example.hungerhub.homeTabs.LocalDataSource;
import com.example.hungerhub.homeTabs.MainTabsActivity;
import com.example.hungerhub.homeTabs.model.MealModel;
import com.example.hungerhub.Repo;
import com.example.hungerhub.homeTabs.network.RemoteDataSource;
import com.example.hungerhub.homeTabs.detailedItem.presenter.ItemDetailsPresenter;
import com.example.hungerhub.homeTabs.plan.models.PlanMealModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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
    Button addToPlanBtn;
    PlanMealModel planMeal=new PlanMealModel();



    public DetailedMealFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new ItemDetailsPresenter(this,
                new Repo(RemoteDataSource.getInstance(),LocalDataSource.getInstance(getActivity()),true),getActivity());

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
        addToPlanBtn= view.findViewById(R.id.addToPlan);
        MealModel mealModel = DetailedMealFragmentArgs.fromBundle(getArguments()).getMeal();

        category=view.findViewById(R.id.category);
        recyclerView = view.findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        presenter.getItemDetails(mealModel);
        addToPlanBtn.setOnClickListener(v->showDatePicker());

    }
    public void loadVideo(String videoId){

            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebChromeClient(new WebChromeClient());
            webView.loadData(videoId,"text/html","utf-8");
        }
        private  void showDatePicker(){
        if(!presenter.isGuestMood()&&presenter.isNetworkConnected()){
            Calendar calendar = Calendar.getInstance();

            // Get the start of the current week (Monday)
            calendar.set(Calendar.DAY_OF_WEEK,calendar.get(Calendar.DAY_OF_WEEK));
            long startOfWeek = calendar.getTimeInMillis();

            // Get the end of the current week (Sunday)
            calendar.add(Calendar.DAY_OF_WEEK, 6);
            long endOfWeek = calendar.getTimeInMillis();

            // Reset to today’s date for default selection
            calendar.setTimeInMillis(System.currentTimeMillis());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Show DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(),
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(selectedYear, selectedMonth, selectedDay);
                        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM", Locale.getDefault());
                        String selectedDate = sdf.format(selectedCalendar.getTime());

                        planMeal.setDate(selectedDate);
                        presenter.addMealToPlan(planMeal, getActivity());
                    },
                    year, month, day
            );

            // Set min and max dates (restrict to the current week)
            datePickerDialog.getDatePicker().setMinDate(startOfWeek);
            datePickerDialog.getDatePicker().setMaxDate(endOfWeek);
            datePickerDialog.show();}
        else {
            onMessageReceived("This feature is not available in guest mode");
        }
        }
    @Override
    public void onMessageReceived(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
    }
    @Override
    public void setMeal(MealModel meal) {
        instructions.setText(meal.getStrInstructions());
        planMeal.setMealModel(meal);
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
    @Override
    public void onResume() {
        super.onResume();
        ((MainTabsActivity)getActivity()).showNavigationBar(true);
    }
}

