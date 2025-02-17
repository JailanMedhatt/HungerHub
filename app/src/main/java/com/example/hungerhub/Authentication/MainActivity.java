package com.example.hungerhub.Authentication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.hungerhub.Authentication.login.view.LoginFragment;
import com.example.hungerhub.R;

public class MainActivity extends AppCompatActivity {
Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Intent i= getIntent();
        String s=i.getStringExtra("frag");
        if(s=="LoginFragment"){
//        fragment=    LoginFragment();
        }

    }
}