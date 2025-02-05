package com.example.hungerhub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginFragment extends Fragment {
EditText email;
EditText pass;
Button loginBtn;
TextView goToSignUpText;


    public LoginFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email= view.findViewById(R.id.email);
        pass= view.findViewById(R.id.pass);
        loginBtn= view.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(v->{
           checkEmptyFields();
        });
        goToSignUpText= view.findViewById(R.id.goToSignUp);
        goToSignUpText.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
        });

    }
    void checkEmptyFields(){
        if(TextUtils.isEmpty(email.getText().toString().trim())){
            email.setError("Email required");
        }
        if(TextUtils.isEmpty(pass.getText().toString().trim())){
            pass.setError("Password required");
        }
    }
}