package inc.moe.foody.auth_feature.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import inc.moe.foody.HomeActivity;
import inc.moe.foody.R;
import inc.moe.foody.auth_feature.presenter.AuthPresenter;
import inc.moe.foody.db.ConcreteLocalSource;
import inc.moe.foody.model.Repo;
import inc.moe.foody.network.MealClient;
import inc.moe.foody.utils.GoogleOptions;

public class MainActivity extends AppCompatActivity implements IAuth {
    private RadioGroup authRadioGroup;
    private FirebaseAuth mAuth;
    private EditText loginUserNameEt , loginPasswordEt ,signUpUsernameEt ,signUpPasswordEt ,signUpConfirmPasswordEt;
    private Button googleBtn;
    public static  FirebaseUser currentUser;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private FirebaseDatabase firebaseDatabase;
    private GoogleSignInClient googleSignInClient;
    private ProgressDialog progressDialog ;
    private AuthPresenter authPresenter;
    private ConstraintLayout loginLayout ,signUpLayout;
    private TextInputLayout emailTextLayout , passwordTextLayout , emailSignUpLayout , passwordSignUpLayout , confirmPasswordSignUpLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        authPresenter = new AuthPresenter(
                Repo.getInstance( MealClient.getInstance() , ConcreteLocalSource.getInstance(this) ) ,this);


        //firebase
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        googleSignInClient = GoogleSignIn.getClient(this ,GoogleOptions.getInstance().getGso() );

        authRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedButton = group.findViewById(checkedId);
                switch (checkedButton.getText().toString()){
                    case "Login":
                        loginLayout.setVisibility(View.VISIBLE);
                        signUpLayout.setVisibility(View.INVISIBLE);
                        break;
                    case "Sign Up":
                        loginLayout.setVisibility(View.INVISIBLE);
                        signUpLayout.setVisibility(View.VISIBLE);
                        break;

                }


            }
        });
    }


    public void onSignInWithGoogleClicked(View view){
       signInWithGoogle();
    }

    int  RC_SIGN_IN =40 ;

    private void signInWithGoogle(){
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent , RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                authPresenter.LoginWithGoogle(account.getIdToken());

            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    public void onLoginClicked(View view){
        authPresenter.LoginWithEmailAndPassword(loginUserNameEt.getText().toString() ,loginPasswordEt.getText().toString() );
    }
    public void onSignUpClicked(View view){

        authPresenter.SignUpWithEmailAndPassword(signUpUsernameEt.getText().toString(), signUpPasswordEt.getText().toString() , signUpConfirmPasswordEt.getText().toString());

   }

    @Override
    protected void onResume() {
        super.onResume();
        clearData();
    }


    public void initUI(){
        loginLayout = findViewById(R.id.login_layout);
        signUpLayout = findViewById(R.id.sign_up_layout);
        authRadioGroup = findViewById(R.id.auth_radio_group);
        loginUserNameEt = findViewById(R.id.usernameEditText);
        signUpUsernameEt = findViewById(R.id.usernameEditTextSignUp);
        loginPasswordEt = findViewById(R.id.passwordEditText);
        signUpPasswordEt = findViewById(R.id.passwordEditTextSignUp);
        signUpConfirmPasswordEt =findViewById(R.id.confirmPasswordEditText);
        googleBtn = findViewById(R.id.google_btn);

        emailTextLayout = findViewById(R.id.usernameTextInputLayout);
        emailSignUpLayout = findViewById(R.id.usernameTextInputLayoutSignUp);
        passwordTextLayout = findViewById(R.id.passwordTextInputLayout);
        passwordSignUpLayout = findViewById(R.id.passwordTextInputLayoutSignUp);
        confirmPasswordSignUpLayout = findViewById(R.id.confirmPasswordTextInputLayout);
//        facebookBtn = findViewById(R.id.facebook_btn);
//        instaBtn = findViewById(R.id.insta_btn);
        loginLayout.setVisibility(View.VISIBLE);
    }

    public void clearData(){
        signUpUsernameEt.setText("");
        loginUserNameEt.setText("");
        loginPasswordEt.setText("");
        signUpConfirmPasswordEt.setText("");
        signUpPasswordEt.setText("");
        emailTextLayout.setError(null);
        emailSignUpLayout.setError(null);
        passwordTextLayout.setError(null);
        confirmPasswordSignUpLayout.setError(null);
        passwordSignUpLayout.setError(null);

    }
    public void onGuestClicked(View view){
        Intent intent = new Intent(MainActivity.this ,HomeActivity.class);

        startActivity(intent);
    }
    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);

        startActivity(intent);

    }

    @Override
    public void onLoginValidationFailure(String errorMessage, boolean isEmail) {
        if(isEmail){
            emailTextLayout.setError(errorMessage);
        }else{
            passwordTextLayout.setError(errorMessage);
        }
    }

    @Override
    public void onLoginFailure(String errorMessage) {
        Snackbar snackbar = Snackbar.make(loginLayout, errorMessage , Snackbar.LENGTH_SHORT);
        snackbar.show();

    }

    @Override
    public void onSignUpSuccess() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);

    }

    @Override
    public void onSignUpFailure(String errorMessage) {
        Snackbar snackbar = Snackbar.make(loginLayout, errorMessage , Snackbar.LENGTH_SHORT);
        snackbar.show();

    }

    @Override
    public void onSignUpValidationFailure(String errorMessage, boolean isEmail) {
        if(isEmail){
            emailSignUpLayout.setError(errorMessage);
        }else{
            passwordSignUpLayout.setError(errorMessage);
            confirmPasswordSignUpLayout.setError(errorMessage);
        }
    }

    @Override
    public void onLoginWithGoogleSuccess() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);

    }

    @Override
    public void onLoginWithGoogleFailure(String errorMessage) {
        Snackbar snackbar = Snackbar.make(loginLayout, errorMessage , Snackbar.LENGTH_SHORT);
        snackbar.show();

    }
}