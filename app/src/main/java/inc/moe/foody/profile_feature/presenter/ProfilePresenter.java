package inc.moe.foody.profile_feature.presenter;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import inc.moe.foody.model.IRepo;
import inc.moe.foody.utils.GoogleOptions;

public class ProfilePresenter implements IProfilePresenter{
    private IRepo iRepo;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    private FirebaseDatabase firebaseDatabase;

    public ProfilePresenter(IRepo iRepo){
        this.iRepo =iRepo;

    }

    @Override
    public void deleteAccount() {
        mAuth.signOut();
        currentUser.delete();
    }

    @Override
    public void logout() {
        mAuth.signOut();

    }
}
