package com.example.hungerhub.Authentication;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hungerhub.Authentication.login.view.LoginFragment;
import com.example.hungerhub.R;

public class MainActivity extends AppCompatActivity {
    FragmentManager manager;
Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        manager=getSupportFragmentManager();
//        NavHostFragment navHostFragment= (NavHostFragment) manager.findFragmentById(R.id.nav_host_fragment);
//        NavController navController= navHostFragment.getNavController();
//           // Check intent and navigate accordingly
//            Intent i = getIntent();
//            String s = i.getStringExtra("frag");
//            if ("LoginFragment".equals(s)) {
//               navController.navigate(R.id.loginFragment); // Navigate properly
//         }

    }
}