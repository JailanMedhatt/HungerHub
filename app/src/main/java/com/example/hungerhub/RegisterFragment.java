package com.example.hungerhub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class RegisterFragment extends Fragment {
    EditText name;
    EditText email;
    EditText pass;
    EditText conPass;
    Button registerBtn;
    TextView goToLoginText;
    TextView emailError;
    TextView passErrorMatch1;
    TextView passErrorMatch2;

    public RegisterFragment() {
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name= view.findViewById(R.id.name);
        email= view.findViewById(R.id.email);
        pass= view.findViewById(R.id.pass);
        conPass= view.findViewById(R.id.conPass);
        registerBtn= view.findViewById(R.id.registerBtn);
        goToLoginText= view.findViewById(R.id.goToSignIn);
        emailError = view.findViewById(R.id.emailError);
        passErrorMatch1 = view.findViewById(R.id.matchPassError1);
        passErrorMatch2 = view.findViewById(R.id.matchPassError2);
        registerBtn.setOnClickListener(v->{
            hideAllErrors();
            if(!isAnyFieldEmpty()){
            checkFieldsValidity();}
        });
        goToLoginText.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
        });
    }
    boolean isAnyFieldEmpty(){
        boolean anyEmptyField= false;
        if(TextUtils.isEmpty(email.getText().toString().trim())){
            email.setError("Email required");
            anyEmptyField=true;
        }
        if(TextUtils.isEmpty(pass.getText().toString().trim())){
            pass.setError("Password required");
            anyEmptyField=true;
        }
        if(TextUtils.isEmpty(name.getText().toString().trim())){
            name.setError("Email required");
            anyEmptyField=true;
        }
        if(TextUtils.isEmpty(conPass.getText().toString().trim())){
            conPass.setError("Password required");
            anyEmptyField=true;
        }
        return anyEmptyField;
    }
     boolean checkFieldsValidity(){
        boolean valid= true;
        if(!pass.getText().toString().equals(conPass.getText().toString())){
            passErrorMatch1.setVisibility(View.VISIBLE);
            passErrorMatch2.setVisibility(View.VISIBLE);
            valid=false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()){
            emailError.setVisibility(View.VISIBLE);
            valid=false;
        }
        return  valid;
    }
    void hideAllErrors(){
        passErrorMatch1.setVisibility(View.INVISIBLE);
        passErrorMatch2.setVisibility(View.INVISIBLE);
        emailError.setVisibility(View.INVISIBLE);
    }
}