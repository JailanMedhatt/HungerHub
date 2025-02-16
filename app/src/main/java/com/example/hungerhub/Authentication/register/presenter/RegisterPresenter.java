package com.example.hungerhub.Authentication.register.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import com.example.hungerhub.Authentication.FireBaseAuthHandler;
import com.example.hungerhub.Authentication.interfaces.OnResponseHandler;
import com.example.hungerhub.Authentication.register.view.Registeriview;
import com.example.hungerhub.SharedPref;

public class RegisterPresenter implements OnResponseHandler{
    Registeriview registeriview;
    FireBaseAuthHandler fireBaseAuthHandler;
    SharedPref sharedPref;
   public RegisterPresenter(Registeriview registeriview, Context context){
       fireBaseAuthHandler=FireBaseAuthHandler.getInstance();
       this.registeriview = registeriview;
       sharedPref= SharedPref.getInstance(context);
   }
    public boolean checkFieldsValidity(String pass, String conPass, String email){
   Boolean isValid=true;
        if(!pass.equals(conPass)){
            registeriview.onMatchingPassError();
            isValid=false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            registeriview.onEmailError("Invalid Email Address");
            isValid=false;
        }
        if(pass.length()<6){

           registeriview.onPassError("Password is less than 6 characters");
            isValid=false;
        }
        return isValid;
    }
    boolean isAnyFieldEmpty(String pass, String conPass, String email,String name){
        boolean anyEmptyField= false;
        if(TextUtils.isEmpty(email)){
            registeriview.onEmailError("Email Required");
            anyEmptyField=true;
        }
        if(TextUtils.isEmpty(pass)){
            registeriview.onPassError("Password required");
            anyEmptyField=true;
        }
        if(TextUtils.isEmpty(name)){
            registeriview.onNameError();
            anyEmptyField=true;
        }
        if(TextUtils.isEmpty(conPass)){
            registeriview.onCinPassError();
            anyEmptyField=true;
        }
        if(!checkFieldsValidity(pass,conPass,email)){
            anyEmptyField=true;
        }
        return anyEmptyField;
    }
    public void register(String pass, String conPass, String email, String name){
       if(!isAnyFieldEmpty( pass,  conPass,  email, name)){
           fireBaseAuthHandler.signUp(email,pass, this);
       }

    }

    @Override
    public void onSuccessResponse(String uid) {
        sharedPref.setLogged(true);
        registeriview.onSuccessResponse();
    }

    @Override
    public void onFailureResponse(String message) {
       registeriview.onFailureResponse(message);

    }
}
