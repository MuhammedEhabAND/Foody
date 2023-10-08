package inc.moe.foody.favourite_feature.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import inc.moe.foody.R;
import inc.moe.foody.auth_feature.view.MainActivity;
import inc.moe.foody.db.ConcreteLocalSource;
import inc.moe.foody.favourite_feature.presenter.FavouritePresenter;
import inc.moe.foody.favourite_feature.presenter.IFavouritePresenter;
import inc.moe.foody.home_feature.view.HomeFragmentDirections;
import inc.moe.foody.home_feature.view.OnImageClickListener;
import inc.moe.foody.home_feature.view.OnRandomMealClickListener;
import inc.moe.foody.model.Meal;
import inc.moe.foody.model.Repo;
import inc.moe.foody.network.MealClient;


public class FavFragment extends Fragment implements OnFavMealClickListener , OnImageClickListener  , IFav{
    RecyclerView favMealsRV;
    FavAdapter adapter;
    FavouritePresenter favPresenter;
    GridLayoutManager layoutManager;
    FirebaseAuth firebaseAuth ;
    FirebaseUser currentUser;
    boolean isUser = false;
    public FavFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            firebaseAuth =FirebaseAuth.getInstance();
            currentUser = firebaseAuth.getCurrentUser();
            isUser = true;
        }else{
            isUser = false;
        }
        favMealsRV = view.findViewById(R.id.fav_rv);
        layoutManager = new GridLayoutManager(getContext() ,2 );
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        adapter = new FavAdapter(this,this);
        favPresenter= new FavouritePresenter(
                Repo.getInstance( MealClient.getInstance() , ConcreteLocalSource.getInstance(getContext())), this);


        favMealsRV.setHasFixedSize(true);
        favMealsRV.setLayoutManager(layoutManager);

        if(isUser) {
//
            Log.i("TAG", "onViewCreated: ");
            ArrayList<Meal> mealArrayList = new ArrayList<>();
            favPresenter.getFavMeals().observe(getViewLifecycleOwner(), new Observer<List<Meal>>() {

                @Override
                public void onChanged(List<Meal> meals) {

                    for(Meal meal : meals){

                        if(meal.getUserId().equals(currentUser.getUid())){
                            mealArrayList.add(meal);
                        }
                    }
                    adapter.setMeals(mealArrayList);
                    adapter.notifyDataSetChanged();
                    favMealsRV.setAdapter(adapter);

                }
            });
            if(mealArrayList.size()==0){
                favPresenter.getMealsFromFirebase();
            }
        }else{ new MaterialAlertDialogBuilder(getContext())
                .setTitle("Oops")
                .setMessage("It seems that you haven't logged in yet ,Umm what are waiting for?")
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Respond to negative button press
                    Navigation.findNavController(getView()).navigateUp();
                })
                .setPositiveButton("Log in", (dialog, which) -> {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                })
                .setOnDismissListener(dialogInterface -> Navigation.findNavController(getView()).navigateUp())
                .show();

        }

    }

    @Override
    public void removeFromDB(Meal meal) {

        Snackbar snackbar = Snackbar.make(getView() ,meal.getStrMeal()+" deleted." ,Snackbar.LENGTH_SHORT);
        snackbar.show();
        favPresenter.removeFromDataBase(meal);

    }

    @Override
    public void navigateToFullDetailedMeal(String idMeal) {
        FavFragmentDirections.ActionFavFragmentToDetailedMeal action = FavFragmentDirections
                .actionFavFragmentToDetailedMeal(idMeal);
        action.setIdMeal(idMeal);
        Navigation.findNavController(getView()).navigate(action);

    }

    @Override
    public void onGettingFavDataFromDBSuccess(List<Meal> mealList) {
        adapter.setMeals(mealList);
        adapter.notifyDataSetChanged();
        favMealsRV.setAdapter(adapter);

    }

    @Override
    public void onGettingFavDataFromDBFailure(String errorMessage) {
        Snackbar snackbar = Snackbar.make(getView() , errorMessage ,Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void onRemoveFromCloud(String message) {

    }
}