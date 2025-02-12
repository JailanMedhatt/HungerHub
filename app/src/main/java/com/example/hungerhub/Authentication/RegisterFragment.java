package com.example.hungerhub.Authentication;

import android.content.Intent;
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

public class RegisterFragment extends Fragment implements OnResponseHandler {
    EditText name;
    EditText email;
    EditText pass;
    ImageView google;
    EditText conPass;
    Button registerBtn;
    TextView goToLoginText;
    TextView emailError;
    TextView passErrorMatch1;
    TextView passErrorMatch2;
   FirebaseAuth firebaseAuth;
    private static final int RC_SIGN_IN = 100;
    private GoogleSignInClient googleSignInClient;
    SharedPref sharedPref;
    FireBaseAuthHandler fireBaseAuthHandler;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      fireBaseAuthHandler=FireBaseAuthHandler.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Get Web Client ID from Firebase
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        sharedPref=SharedPref.getInstance(getActivity());
//        googleSignInClient.signOut();
//        firebaseAuth.signOut();

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
        google=view.findViewById(R.id.google);
        email.setText("j@yahoo.com");
        name.setText("jailan");
        pass.setText("123456");
                conPass.setText("123456");
        registerBtn.setOnClickListener(v->{
            hideAllErrors();
            if(!isAnyFieldEmpty()){
                if(
            checkFieldsValidity()){
                    fireBaseAuthHandler.signUp(email.getText().toString().trim(),pass.getText().toString().trim(),this,view);
                }
            }
        });
        goToLoginText.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
        });
        google.setOnClickListener(v->{
            //countinueWithGoogle();
            signInWithGoogle();
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
        if(pass.getText().toString().trim().length()<6){
            passErrorMatch1.setText("Password must be not less than 6 charcters");
            passErrorMatch1.setVisibility(View.VISIBLE);
            valid= false;

        }
        return  valid;
    }
    void hideAllErrors(){
        passErrorMatch1.setVisibility(View.INVISIBLE);
        passErrorMatch2.setVisibility(View.INVISIBLE);
        emailError.setVisibility(View.INVISIBLE);
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
        if(idToken==null){
            Toast.makeText(getActivity(), "You didn't choose an email!", Toast.LENGTH_SHORT).show();
            return;
        }
    AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
    firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(getActivity(), task -> {
                if (task.isSuccessful()) {
                    // Sign-in successful
                    FirebaseUser user = firebaseAuth.getCurrentUser();
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
        AlertDialouge alertDialouge = new AlertDialouge(getActivity(),
                "Warning",message,"ok",null);
        alertDialouge.showAlert();
    }

    @Override
    public void onSuccessResponse(View view,String uid) {
        sharedPref.setLogged(true);
        Intent intent = new Intent(getActivity(), MainTabsActivity.class);
        startActivity(intent);
        Toast.makeText(getActivity(),"logged in", Toast.LENGTH_LONG).show();
    }
}