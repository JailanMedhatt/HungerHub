package com.example.hungerhub.Authentication;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.hungerhub.Authentication.login.view.Loginiview;
import com.example.hungerhub.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class GoogleAuthService {

  public    static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private Activity activity;
    Loginiview loginiview;

    public GoogleAuthService(Activity activity,Loginiview loginiview) {
        this.activity = activity;
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id)) // Add your Web Client ID here
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
        this.loginiview=loginiview;
    }

    // Start Google Sign-In intent
    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // Handle sign-in result (with Task<GoogleSignInAccount>)
    public void handleSignInResult(Intent data, SignInResultCallback callback) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        task.addOnCompleteListener(activity, new OnCompleteListener<GoogleSignInAccount>() {
            @Override
            public void onComplete(Task<GoogleSignInAccount> task) {
                if (task.isSuccessful()) {
                    // Google Sign-In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult();
                    if (account != null) {
                        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                        mAuth.signInWithCredential(credential).addOnCompleteListener(activity, signInTask -> {
                            if (signInTask.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                loginiview.onSuccessResponse();
                            } else {
                                callback.onSignInFailed(signInTask.getException());
                            }
                        });
                    }
                } else {
                    // Google Sign-In failed
                    loginiview.onFailureResponse(task.getException().toString());
                }
            }
        });
    }

    // Sign out the user
    public void signOut() {
        mAuth.signOut();
        mGoogleSignInClient.signOut();
    }

    public interface SignInResultCallback {
        void onSignInSuccess(FirebaseUser user);
        void onSignInFailed(Exception exception);
    }
}
