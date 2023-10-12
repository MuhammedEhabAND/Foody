package inc.moe.foody.auth_feature.presenter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import inc.moe.foody.model.User;
import inc.moe.foody.auth_feature.view.IAuth;
import inc.moe.foody.model.IRepo;
import inc.moe.foody.utils.UserModel;

public class AuthPresenter implements IAuthPresenter {
    private UserModel userModel;

    private IRepo iRepo;
    IAuth iAuth;

    public AuthPresenter (IRepo iRepo , IAuth iAuth ){
        this.iRepo = iRepo;
        this.iAuth = iAuth;
        this.userModel = new UserModel();
    }

    @Override
    public void LoginWithEmailAndPassword(String email, String password) {

        userModel.setEmail(email);
        userModel.setPassword(password);


        if(userModel.getEmail().isEmpty() &&userModel.getPassword().isEmpty()){
            iAuth.onLoginValidationFailure("Email Cannot be Empty" ,true);
            iAuth.onLoginValidationFailure("Password Cannot be Empty" ,false);

        }
        else if (!userModel.isEmailValid()) {
            iAuth.onLoginValidationFailure("Invalid email address" , true);
        } else if (!userModel.isPasswordValid()) {
            iAuth.onLoginValidationFailure("Password must be at least 8 characters" , false);
        } else {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                iAuth.onLoginSuccess();

                            } else {
                                iAuth.onLoginFailure(task.getException().getMessage());
                                //updateUI(null);
                            }
                        }
                    });


        }



    }

    @Override
    public void SignUpWithEmailAndPassword(String email, String password , String confirmPassword) {
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setConfirmPassword(confirmPassword);

        if(userModel.getEmail().isEmpty() &&userModel.getPassword().isEmpty()){
            iAuth.onSignUpValidationFailure("Email Cannot be Empty" ,true);
            iAuth.onSignUpValidationFailure("Password Cannot be Empty" ,false);

        }
        else if (!userModel.isEmailValid()) {
            iAuth.onSignUpValidationFailure("Invalid email address" ,true);
        } else if (!userModel.isPasswordValid()) {
            iAuth.onSignUpValidationFailure("Password must be at least 8 characters" , false);
        } else if (!userModel.passwordsMatch()) {
            iAuth.onSignUpValidationFailure("Passwords do not match", false);
        } else {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(userModel.getEmail(), userModel.getPassword())
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                iAuth.onSignUpSuccess();

                            } else {
                                iAuth.onSignUpFailure(task.getException().getMessage());

                            }
                        }
                    });

        }

    }

    @Override
    public void LoginWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken , null);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();

                            User users = new User();
                            users.setUserId(user.getUid());
                            users.setName(user.getDisplayName());
                            users.setProfile(user.getPhotoUrl().toString());
                            firebaseDatabase.getReference().child("Users").child(user.getUid()).setValue(users);
                            iAuth.onLoginWithGoogleSuccess();

                        }else{
                            iAuth.onLoginWithGoogleFailure(task.getException().toString());
                        }
                    }
                });

    }
}
