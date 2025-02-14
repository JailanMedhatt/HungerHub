package com.example.hungerhub.Authentication.register.presenter;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import com.example.hungerhub.Authentication.FireBaseAuthHandler;
import com.example.hungerhub.Authentication.interfaces.OnResponseHandler;
import com.example.hungerhub.Authentication.register.view.OnErrorHandling;

public class RegisterPresenter {
    OnErrorHandling onErrorHandling;
    FireBaseAuthHandler fireBaseAuthHandler;
   public RegisterPresenter( OnErrorHandling onErrorHandling){
       fireBaseAuthHandler=FireBaseAuthHandler.getInstance();
       this.onErrorHandling=onErrorHandling;
   }
    public boolean checkFieldsValidity(String pass, String conPass, String email){
   Boolean isValid=true;
        if(!pass.equals(conPass)){
            onErrorHandling.onMatchingPassError();
            isValid=false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            onErrorHandling.onEmailError("Invalid Email Address");
            isValid=false;
        }
        if(pass.length()<6){

           onErrorHandling.onPassError("Password is less than 6 characters");
            isValid=false;
        }
        return isValid;
    }
    boolean isAnyFieldEmpty(String pass, String conPass, String email,String name){
        boolean anyEmptyField= false;
        if(TextUtils.isEmpty(email)){
            onErrorHandling.onEmailError("Email Required");
            anyEmptyField=true;
        }
        if(TextUtils.isEmpty(pass)){
            onErrorHandling.onPassError("Password required");
            anyEmptyField=true;
        }
        if(TextUtils.isEmpty(name)){
            onErrorHandling.onNameError();
            anyEmptyField=true;
        }
        if(TextUtils.isEmpty(conPass)){
            onErrorHandling.onCinPassError();
            anyEmptyField=true;
        }
        if(!checkFieldsValidity(pass,conPass,email)){
            anyEmptyField=true;
        }
        return anyEmptyField;
    }
    public void register(String pass, String conPass, String email, String name, OnResponseHandler onResponseHandler){
       if(!isAnyFieldEmpty( pass,  conPass,  email, name)){
           fireBaseAuthHandler.signUp(email,pass,onResponseHandler);
       }

    }
}
