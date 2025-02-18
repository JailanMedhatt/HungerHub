package com.example.hungerhub.Authentication.login.view;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hungerhub.Authentication.AlertDialouge;
import com.example.hungerhub.Authentication.GoogleAuthService;
import com.example.hungerhub.Authentication.login.presenter.LoginPresenter;
import com.example.hungerhub.R;
import com.example.hungerhub.homeTabs.MainTabsActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginFragment extends Fragment implements Loginiview{
    EditText email;
    EditText pass;
    Button loginBtn;
    TextView goToSignUpText;
    TextView emailError;
    TextView passError;
    Button guestBtn;
    ImageView google;
    private static final int RC_SIGN_IN = 100;
    LoginPresenter presenter;
    GoogleAuthService googleAuthService;

    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        googleAuthHelper = new GoogleAuthHelper(getActivity());
         googleAuthService= new GoogleAuthService(getActivity());
        presenter= new LoginPresenter(this,getActivity());



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
        ((MainTabsActivity)getActivity()).showNavigationBar(false);
        email = view.findViewById(R.id.email);
        pass = view.findViewById(R.id.pass);
        guestBtn= view.findViewById(R.id.guestButton);
        loginBtn = view.findViewById(R.id.loginBtn);
        emailError=view.findViewById(R.id.emailError);
        passError=view.findViewById(R.id.passError);
        google = view.findViewById(R.id.google);
        google.setOnClickListener(v ->googleAuthService.signIn(this));
        guestBtn.setOnClickListener(v->{
            presenter.ensureGuestMode();
            Navigation.findNavController(getView()).navigate(R.id.action_loginFragment2_to_homeFragment);
        });
        loginBtn.setOnClickListener(v -> {
            hideErrors();
           presenter.login(email.getText().toString().trim(),pass.getText().toString().trim());
        });
        goToSignUpText = view.findViewById(R.id.goToSignUp);
        goToSignUpText.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment2_to_registerFragment2);
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign-In successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                presenter.firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(getActivity(), "Google Sign-In failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public  void hideErrors(){
        emailError.setVisibility(INVISIBLE);
        passError.setVisibility(INVISIBLE);
    }
    @Override
    public void onFailureResponse(String message) {
    AlertDialouge alertDialouge= new AlertDialouge(getActivity(),
        "warning",message,"ok",null);
    alertDialouge.showAlert();
    }
    @Override
    public void onSuccessResponse() {
    Navigation.findNavController(getView()).navigate(R.id.action_loginFragment2_to_homeFragment);
     Toast.makeText(getActivity(),"logged in", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEmailError(String msg) {
        emailError.setVisibility(VISIBLE);
        emailError.setText(msg);
    }

    @Override
    public void onPassError() {
        passError.setVisibility(VISIBLE);

    }


}