package com.example.hungerhub.Authentication;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.hungerhub.Authentication.interfaces.OnResponseHandler;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FireBaseAuthHandler {
    private static FireBaseAuthHandler instance;
    FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private FireBaseAuthHandler(){
        firebaseAuth = FirebaseAuth.getInstance();
    }
    public  static FireBaseAuthHandler getInstance(){
        if(instance== null){
            instance= new FireBaseAuthHandler();
        }
        return  instance;
    }
    void login(String email, String pass, OnResponseHandler handler, View view) {
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
               handler.onSuccessResponse(view,authResult.getUser().getUid());
                Log.i("zz", "onSuccess: yessss");
            //  Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                handler.onFailureResponse(e.getMessage());
            }
        });
    }
    void signUp(String email, String pass, OnResponseHandler handler, View view){
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                handler.onSuccessResponse(view,authResult.getUser().getUid());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                handler.onFailureResponse(e.getMessage());
            }
        });
    }
    public  void  logOut(){

        firebaseAuth.signOut();
    }

}



