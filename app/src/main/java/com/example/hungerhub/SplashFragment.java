package com.example.hungerhub;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.hungerhub.homeTabs.MainTabsActivity;


public class SplashFragment extends Fragment {
   ImageView logo;
   LottieAnimationView lottieAnimationView;
   SharedPref sharedPref;
    public SplashFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     sharedPref=SharedPref.getInstance(getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logo=view.findViewById(R.id.logo);
        lottieAnimationView=view.findViewById(R.id.lottie);
        logo.animate().translationY(-1600).setDuration(1000).setStartDelay(2000);
        lottieAnimationView.animate().translationY(-1600).setDuration(1000).setStartDelay(2000);
        new Handler().postDelayed(()->{
            if(sharedPref.isLogged()==true&&sharedPref.getUSERID()!=null){
                Intent intent= new Intent(getActivity(), MainTabsActivity.class);
                startActivity(intent);
            }
            else {
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment);
            }
        },3000);

    }
}