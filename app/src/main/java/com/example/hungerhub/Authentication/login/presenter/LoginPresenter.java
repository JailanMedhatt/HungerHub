package com.example.hungerhub.Authentication.login.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import com.example.hungerhub.Authentication.FireBaseAuthHandler;
import com.example.hungerhub.Authentication.interfaces.OnResponseHandler;
import com.example.hungerhub.Authentication.login.view.Loginiview;
import com.example.hungerhub.homeTabs.Repo;
import com.example.hungerhub.SharedPref;
import com.example.hungerhub.homeTabs.db.LocalDataSource;
import com.example.hungerhub.homeTabs.model.MealModel;
import com.example.hungerhub.homeTabs.network.RemoteDataSource;
import com.example.hungerhub.homeTabs.plan.models.PlanMealModel;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
public class LoginPresenter implements OnResponseHandler {
    SharedPref sharedPref;
    Loginiview loginiview;
    FireBaseAuthHandler fireBaseAuthHandler;
    FirebaseAuth firebaseAuth;
    Repo repo;
    public  LoginPresenter(Loginiview loginiview, Context context){
        this.loginiview=loginiview;
        sharedPref= SharedPref.getInstance(context);
        firebaseAuth = FirebaseAuth.getInstance();
        fireBaseAuthHandler= FireBaseAuthHandler.getInstance();
        repo= new Repo(RemoteDataSource.getInstance(), LocalDataSource.getInstance(context),true);
    }
    public boolean checkFieldsValidity(String email, String pass) {
        boolean isValid = true;
        if (TextUtils.isEmpty(email)) {
           loginiview.onEmailError("Email is required");
            isValid = false;

        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loginiview.onEmailError("Invalid Email Address");
        }
        if (TextUtils.isEmpty(pass) ){
         loginiview.onPassError();
            isValid = false;
        }
        return isValid;
    }
    public void login(String email, String pass){
        if(checkFieldsValidity(email,pass)){
        fireBaseAuthHandler.login(email, pass,this);}
    }
    @Override
    public void onSuccessResponse(String uid) {
        sharedPref.setLogged(true);
        sharedPref.setUSERID(uid);
        loadBackUpData(uid);
        loginiview.onSuccessResponse();
    }
    @Override
    public void onFailureResponse(String message) {
        loginiview.onFailureResponse(message);
    }
    public  void loadBackUpData(String uid){
        repo.getAllFavFromFireStore(uid).subscribeOn(Schedulers.io()).subscribe(
                list->{
                    for(MealModel meal: list){

                        repo.insertMealToLocalFav(meal).subscribeOn(Schedulers.io()).
                                observeOn(AndroidSchedulers.mainThread()).subscribe();
                    }
                }

        );
        repo.getAllplansFromFireStore(uid).subscribeOn(Schedulers.io()).subscribe(
                list->{
                    for(PlanMealModel meal: list){

                        repo.insertMealToPlanLocal(meal).subscribeOn(Schedulers.io()).
                                observeOn(AndroidSchedulers.mainThread()).subscribe();
                    }
                }
        );

    }
    public void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener( task -> {
                    if (task.isSuccessful()) {
                        // Sign-in successful
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                       onSuccessResponse(user.getUid());

                    } else {
                        onFailureResponse("Authentication Failed!");
                    }
                });
    }
    public void ensureGuestMode(){
        sharedPref.setLogged(false);
        sharedPref.setUSERID(null);
    }

}
