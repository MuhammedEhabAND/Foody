package inc.moe.foody.profile_feature.view;


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
import inc.moe.foody.utils.GoogleOptions;


public class ProfileFragment extends Fragment  {
    private ListView settingsList;
    private TextView userName , changeUserName;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    TextView yourFav ,yourPlan , deleteAcc , logout;
    private FirebaseDatabase firebaseDatabase;

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
        userName = view.findViewById(R.id.userNameTxtViewId);
        yourFav = view.findViewById(R.id.your_favourites_txt_view);
        yourPlan = view.findViewById(R.id.your_plans_txt_view);
        deleteAcc = view.findViewById(R.id.delete_account_txt_view);
        logout = view.findViewById(R.id.login_txt_view);
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
                mAuth.signOut();
                GoogleSignIn.getClient(getContext(), GoogleOptions.getInstance().getGso()).signOut();
                getActivity().finish();

            }
        });
        deleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                GoogleSignIn.getClient(getContext() , GoogleOptions.getInstance().getGso()).signOut();
                currentUser.delete();
                getActivity().finish();

            }
        });
        if (currentUser.getDisplayName() != null) {
            if (currentUser.getDisplayName().isEmpty())
                userName.setText(currentUser.getEmail());
            else
                userName.setText(currentUser.getDisplayName());

        }
        profileImage = view.findViewById(R.id.profileImage);
        Glide.with(view).load(currentUser.getPhotoUrl())
                .error(R.drawable.ic_baseline_person_24).into(profileImage);

    }
}
