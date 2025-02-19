package com.example.hungerhub.Authentication.register.view;

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
import com.example.hungerhub.Authentication.register.presenter.RegisterPresenter;
import com.example.hungerhub.R;
import com.example.hungerhub.SharedPref;
import com.example.hungerhub.homeTabs.MainTabsActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class RegisterFragment extends Fragment implements Registeriview {
    EditText name;
    EditText email;
    EditText pass;
    ImageView google;
    EditText conPass;
    Button registerBtn;
    TextView goToLoginText;
    TextView nameEroor;
    TextView emailError;
    TextView passErrorMatch1;
    TextView passErrorMatch2;
    private static final int RC_SIGN_IN = 100;
    GoogleAuthService googleAuthService;

    RegisterPresenter registerPresenter;
    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        googleAuthService= new GoogleAuthService(getActivity());
        registerPresenter=new RegisterPresenter(this,getActivity());

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
        ((MainTabsActivity)getActivity()).showNavigationBar(false);

        name= view.findViewById(R.id.name);
        email= view.findViewById(R.id.email);
        pass= view.findViewById(R.id.pass);
        conPass= view.findViewById(R.id.conPass);
        registerBtn= view.findViewById(R.id.registerBtn);
        goToLoginText= view.findViewById(R.id.goToSignIn);
        emailError = view.findViewById(R.id.emailError);
        passErrorMatch1 = view.findViewById(R.id.matchPassError1);
        nameEroor=view.findViewById(R.id.nameError);
        passErrorMatch2 = view.findViewById(R.id.matchPassError2);
        google=view.findViewById(R.id.google);
        email.setText("j@yahoo.com");
        name.setText("jailan");
        pass.setText("123456");
                conPass.setText("123456");
        registerBtn.setOnClickListener(v->{
            hideAllErrors();
           registerPresenter.register(
                   pass.getText().toString().trim(),
                   conPass.getText().toString().trim(),
                   email.getText().toString().trim(),
                   name.getText().toString().trim()
           );
        });
        goToLoginText.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_registerFragment2_to_loginFragment2);
        });
        google.setOnClickListener(v->{
            googleAuthService.signIn(this);
        });
    }


    void hideAllErrors(){
        passErrorMatch1.setVisibility(View.INVISIBLE);
       nameEroor.setVisibility(View.INVISIBLE);
        passErrorMatch2.setVisibility(View.INVISIBLE);
        emailError.setVisibility(View.INVISIBLE);
    }




@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RC_SIGN_IN) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            // Google Sign-In successful, authenticate with Firebase
            GoogleSignInAccount account = task.getResult(ApiException.class);
            registerPresenter.firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            Toast.makeText(getActivity(), "Google Sign-In failed!", Toast.LENGTH_SHORT).show();
        }
    }
}



    @Override
    public void onFailureResponse(String message) {
        AlertDialouge alertDialouge = new AlertDialouge(getActivity(),
                "Warning",message,"ok",null,"cancel");
        alertDialouge.showAlert();
    }

    @Override
    public void onSuccessResponse() {

        Navigation.findNavController(getView()).navigate(R.id.action_registerFragment2_to_homeFragment);
        Toast.makeText(getActivity(),"logged in", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNameError() {
        nameEroor.setVisibility(View.VISIBLE);
        nameEroor.setText("Name Required");
    }

    @Override
    public void onEmailError(String error) {
        emailError.setVisibility(View.VISIBLE);
        emailError.setText(error);
    }

    @Override
    public void onPassError(String msg) {
        passErrorMatch1.setVisibility(View.VISIBLE);
        passErrorMatch1.setText(msg);
    }

    @Override
    public void onMatchingPassError() {
        passErrorMatch1.setVisibility(View.VISIBLE);
        passErrorMatch2.setVisibility(View.VISIBLE);
        passErrorMatch1.setText("Password do not match");

    }

    @Override
    public void onCinPassError() {
        passErrorMatch2.setVisibility(View.VISIBLE);
        nameEroor.setText("Confirm Password Required");

    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainTabsActivity)getActivity()).showNavigationBar(false);
    }
}