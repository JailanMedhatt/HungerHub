package com.example.hungerhub.Authentication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.hungerhub.R;
import com.example.hungerhub.Authentication.interfaces.OnResponseHandler;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class GoogleAuthHelper {
    Context context;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;

    // Constructor
    public GoogleAuthHelper(Context activity) {
        this.context = activity;
        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id)) // Get Web Client ID from Firebase
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    // Start Google Sign-In Intent
    public void signInWithGoogle(int requestCode, Activity context) {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        context.startActivityForResult(signInIntent, requestCode);

    }

    //

    // Authenticate with Firebase
    public void firebaseAuthWithGoogle(String idToken, OnResponseHandler Handler) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) context, task -> {
                    if (task.isSuccessful()) {
                        // Sign-in successful
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Toast.makeText(context, "Welcome " + user.getDisplayName(), Toast.LENGTH_SHORT).show();

                    } else {
                        // Sign-in failed
                        Toast.makeText(context, "Authentication Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Sign out
    public void signOut() {
        googleSignInClient.signOut().addOnCompleteListener(task -> firebaseAuth.signOut());
    }

    // Callback Interface
    public interface GoogleAuthCallback {
        void onSuccess(FirebaseUser user);
        void onFailure(String errorMessage);
    }
}