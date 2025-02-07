package com.example.hungerhub.Authentication;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hungerhub.R;
import com.example.hungerhub.SharedPref;
import com.example.hungerhub.Authentication.interfaces.OnResponseHandler;
import com.example.hungerhub.homeTabs.MainTabsActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
public class LoginFragment extends Fragment implements OnResponseHandler {
    EditText email;
    EditText pass;
    Button loginBtn;
    TextView goToSignUpText;
    FirebaseAuth firebaseAuth;
    ImageView google;
    private static final int RC_SIGN_IN = 100;
    private GoogleSignInClient googleSignInClient;
    FireBaseAuthHandler fireBaseAuthHandler;
    SharedPref sharedPref;
    GoogleAuthHelper googleAuthHelper;
    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        googleAuthHelper = new GoogleAuthHelper(getActivity());
        fireBaseAuthHandler= FireBaseAuthHandler.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        sharedPref= SharedPref.getInstance(getActivity());
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Get Web Client ID from Firebase
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        if(sharedPref.isLogged()){
            Intent intent = new Intent(getActivity(), MainTabsActivity.class);
            startActivity(intent);

        }

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
        email = view.findViewById(R.id.email);
        pass = view.findViewById(R.id.pass);
        loginBtn = view.findViewById(R.id.loginBtn);
        google = view.findViewById(R.id.google);
        google.setOnClickListener(v -> signInWithGoogle());
        loginBtn.setOnClickListener(v -> {
            if (checkEmptyFields()) {
                fireBaseAuthHandler.login(email.getText().toString().trim(), pass.getText().toString().trim(),this,view);
            }
        });
        goToSignUpText = view.findViewById(R.id.goToSignUp);
        goToSignUpText.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
        });
    }

    boolean checkEmptyFields() {
        boolean isValid = true;
        if (TextUtils.isEmpty(email.getText().toString().trim())) {
            email.setError("Email required");
            isValid = false;
        }
        if (TextUtils.isEmpty(pass.getText().toString().trim())) {
            pass.setError("Password required");
            isValid = false;
        }
        return isValid;
    }

    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign-In successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(getActivity(), "Google Sign-In failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign-in successful
                        sharedPref.setLogged(true);
                        Intent intent = new Intent(getActivity(), MainTabsActivity.class);
                        startActivity(intent);
                        Toast.makeText(getActivity(),"logged in", Toast.LENGTH_LONG).show();

                    } else {
                        // Sign-in failed
                        Toast.makeText(getActivity(), "Authentication Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public void onFailureResponse(String message) {

    AlertDialouge alertDialouge= new AlertDialouge(getActivity(),
        "warning",message,"ok",null);
    alertDialouge.showAlert();
    }
    @Override
    public void onSuccessResponse(View view) {

     sharedPref.setLogged(true);
     Intent intent = new Intent(getActivity(), MainTabsActivity.class);
     startActivity(intent);
     Toast.makeText(getActivity(),"logged in", Toast.LENGTH_LONG).show();
    }

}