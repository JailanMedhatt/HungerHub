package com.example.hungerhub.homeTabs;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

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
import com.google.android.material.snackbar.Snackbar;

public class MainTabsActivity extends AppCompatActivity {
 BottomNavigationView bottomNavigationView;
 FragmentManager manager;
 SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_tabs);
       sharedPref= SharedPref.getInstance(this);


        bottomNavigationView= findViewById(R.id.navBar);
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
            if((item.getItemId()==R.id.favFragment||item.getItemId()==R.id.planFragment)&&sharedPref.getUSERID()==null){
                Toast.makeText(MainTabsActivity.this,"This feature is not available in guest mode",Toast.LENGTH_LONG).show();
              return false ;
            }

            return NavigationUI.onNavDestinationSelected(item,navController);

        }
    });

    }
}