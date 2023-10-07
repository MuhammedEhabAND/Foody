package inc.moe.foody.profile_feature.view;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import inc.moe.foody.HomeActivity;
import inc.moe.foody.R;
import inc.moe.foody.auth_feature.view.MainActivity;
import inc.moe.foody.db.ConcreteLocalSource;
import inc.moe.foody.model.Repo;
import inc.moe.foody.network.MealClient;
import inc.moe.foody.profile_feature.presenter.ProfilePresenter;
import inc.moe.foody.utils.GoogleOptions;


public class ProfileFragment extends Fragment  {
    private ListView settingsList;
    private TextView userName , changeUserName;
    TextView yourFav ,yourPlan , deleteAcc , logout;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    ProfilePresenter profilePresenter;
    private ImageView profileImage;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
        profilePresenter  = new ProfilePresenter(
                 Repo.getInstance(MealClient.getInstance() , ConcreteLocalSource.getInstance(getContext()))
        );
        yourFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(ProfileFragmentDirections.actionProfileFragmentToFavFragment());

            }
        });
        yourPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilePresenter.logout();
                GoogleSignIn.getClient(getContext(), GoogleOptions.getInstance().getGso()).signOut();
                Intent intent = new Intent(getActivity() , MainActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });
        deleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilePresenter.deleteAccount();
                GoogleSignIn.getClient(getContext() , GoogleOptions.getInstance().getGso()).signOut();
                Intent intent = new Intent(getActivity() , MainActivity.class);
                startActivity(intent);

                getActivity().finish();

            }
        });
        if (currentUser.getDisplayName() != null) {
                userName.setText(currentUser.getDisplayName());
        }else{
            userName.setText(currentUser.getEmail());

        }
        profileImage = view.findViewById(R.id.profileImage);
        Glide.with(view).load(currentUser.getPhotoUrl())
                .error(R.drawable.ic_baseline_person_24).into(profileImage);

    }
    public void initUI(){

        userName = getView().findViewById(R.id.userNameTxtViewId);
        yourFav = getView().findViewById(R.id.your_favourites_txt_view);
        yourPlan = getView().findViewById(R.id.your_plans_txt_view);
        deleteAcc = getView().findViewById(R.id.delete_account_txt_view);
        logout = getView().findViewById(R.id.login_txt_view);
    }
}
