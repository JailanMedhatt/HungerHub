package com.example.hungerhub.homeTabs;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.example.hungerhub.Authentication.MainActivity;
import com.example.hungerhub.R;
import com.example.hungerhub.SharedPref;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;

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
            if(item.getItemId()==R.id.loginFragment2){
               sharedPref.setLogged(false);
               sharedPref.setUSERID(null);
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id)) // Get Web Client ID from Firebase
                        .requestEmail()
                        .build();
             GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(FirebaseApp.getInstance().getApplicationContext(), gso);
             googleSignInClient.signOut();

//                Intent i = new Intent(MainTabsActivity.this, MainActivity.class);
//                i.putExtra("frag","LoginFragment");
//                startActivity(i);
                return  NavigationUI.onNavDestinationSelected(item,navController);
            }
            if((item.getItemId()==R.id.favFragment||item.getItemId()==R.id.planFragment)&&sharedPref.getUSERID()==null){
                Toast.makeText(MainTabsActivity.this,"This feature is not available in guest mode",Toast.LENGTH_LONG).show();
              return false ;
            }

            return NavigationUI.onNavDestinationSelected(item,navController);

        }
    });

    }
    public void showNavigationBar(boolean shown){
        if(shown){
            bottomNavigationView.setVisibility(VISIBLE);
        }
        else{
            bottomNavigationView.setVisibility(GONE);
        }
    }
}