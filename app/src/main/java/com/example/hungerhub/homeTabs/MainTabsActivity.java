package com.example.hungerhub.homeTabs;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.hungerhub.Authentication.MainActivity;
import com.example.hungerhub.R;
import com.example.hungerhub.SharedPref;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainTabsActivity extends AppCompatActivity {
 BottomNavigationView bottomNavigationView;
 FragmentManager manager;
 SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_tabs);
       bottomNavigationView= findViewById(R.id.navBar);
       sharedPref= SharedPref.getInstance(this);
       manager=getSupportFragmentManager();
        NavHostFragment navHostFragment= (NavHostFragment) manager.findFragmentById(R.id.nav_host_fragment);

         NavController navController= navHostFragment.getNavController();

    bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if(item.getItemId()==R.id.logOut){
               sharedPref.setLogged(false);
                Intent i = new Intent(MainTabsActivity.this, MainActivity.class);
                startActivity(i);
                return true;
            }
            return NavigationUI.onNavDestinationSelected(item,navController);
        }
    });

    }
}