package inc.moe.foody.home_feature.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

import inc.moe.foody.R;
import inc.moe.foody.auth_feature.view.MainActivity;
import inc.moe.foody.db.ConcreteLocalSource;
import inc.moe.foody.home_feature.presenter.HomePresenter;
import inc.moe.foody.model.Category;
import inc.moe.foody.model.Country;
import inc.moe.foody.model.Meal;
import inc.moe.foody.model.Repo;
import inc.moe.foody.network.MealClient;
import inc.moe.foody.utils.NetworkConnection;
import inc.moe.foody.utils.ShowingSnackbar;
import io.reactivex.rxjava3.core.Observer;


public class HomeFragment extends Fragment implements IHome, OnRandomMealClickListener , OnCategoryClickListener ,OnImageClickListener ,OnCountryClickListener{
    private RecyclerView allCategoriesRV ,randomMealRV ,allMealsRV ,allCountriesRV;
    private ShimmerFrameLayout randomMealShimmer ,categoryMealShimmer , allMealsShimmer , allCountriesShimmer;
    private HomePresenter homePresenter;
    private CategoryAdapter categoryAdapter;
    private RandomMealAdapter randomMealAdapter;
    private AllMealsAdapter allMealsAdapter;
    private AllCountriesAdapter allCountriesAdapter ;
    private LinearLayoutManager categoryLayoutManager , randomMealsLayoutManager , allMealsLayoutManager ,allCountriesLayoutManger;
    private View myView ;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    boolean isUser = false;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myView =view;
        if(FirebaseAuth.getInstance().getCurrentUser()!= null){
            firebaseAuth = FirebaseAuth.getInstance();
            currentUser = firebaseAuth.getCurrentUser();
            isUser = true;
        }else{
            isUser = false;
        }

        randomMealShimmer = view.findViewById(R.id.random_meal_shimmer_layout);
        categoryMealShimmer = view.findViewById(R.id.category_shimmer);
        allMealsShimmer =view.findViewById(R.id.all_meals_shimmer);
        allCountriesShimmer =view.findViewById(R.id.countries_shimmer);
        allMealsShimmer.startShimmerAnimation();
        allCountriesShimmer.startShimmerAnimation();
        randomMealShimmer.startShimmerAnimation();
        categoryMealShimmer.startShimmerAnimation();


        allCategoriesRV = view.findViewById(R.id.categories_rv);
        randomMealRV = view.findViewById(R.id.random_meal_rv);
        allMealsRV = view.findViewById(R.id.all_meals_rv);
        allCountriesRV = view.findViewById(R.id.countries_rv);

        allCountriesLayoutManger = new LinearLayoutManager(getContext());
        allCountriesLayoutManger.setOrientation(RecyclerView.HORIZONTAL);
        allMealsLayoutManager = new LinearLayoutManager(getContext());
        allMealsLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        categoryLayoutManager = new LinearLayoutManager(getContext()  );
        categoryLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        randomMealsLayoutManager = new LinearLayoutManager(getContext()  );
        randomMealsLayoutManager.setOrientation(RecyclerView.HORIZONTAL);


        categoryAdapter = new CategoryAdapter(this::searchByCategoryName);
        randomMealAdapter = new RandomMealAdapter(this , this);
        allMealsAdapter = new AllMealsAdapter(this);
        allCountriesAdapter = new AllCountriesAdapter(this);
        homePresenter = new HomePresenter(this ,
                Repo.getInstance( MealClient.getInstance() , ConcreteLocalSource.getInstance(getContext())));


        allCategoriesRV.setHasFixedSize(true);
        allCategoriesRV.setLayoutManager(categoryLayoutManager);
        allMealsRV.setHasFixedSize(true);
        allMealsRV.setLayoutManager(allMealsLayoutManager);
        randomMealRV.setHasFixedSize(true);
        randomMealRV.setLayoutManager(randomMealsLayoutManager);
        allCountriesRV.setHasFixedSize(true);
        allCountriesRV.setLayoutManager(allCountriesLayoutManger);
        if(NetworkConnection.isConnected(getContext())){
            homePresenter.getRandomMeal();
            homePresenter.getAllCategories();
            homePresenter.getAllMeals();
            homePresenter.getAllCountries();

        } else{
            Toast.makeText(getContext(), "No Network", Toast.LENGTH_SHORT).show();
            }

        }


    @Override
    public void onCategoryFetch(List<Category> categories) {
        categoryAdapter.setCategoryList(categories);
        categoryMealShimmer.setVisibility(View.GONE);
        allCategoriesRV.setVisibility(View.VISIBLE);
        allCategoriesRV.setAdapter(categoryAdapter);
    }

    @Override
    public void onRandomMealFetch(List<Meal> meals) {
        randomMealAdapter.setMealList(meals);
        randomMealShimmer.setVisibility(View.GONE);
        randomMealRV.setVisibility(View.VISIBLE);
        randomMealRV.setAdapter(randomMealAdapter);

    }

    @Override
    public void onCategoryFailed(String errorMessage) {
        ShowingSnackbar.showSnackbar(getView() , errorMessage);
    }

    @Override
    public void onRandomMealFailed(String errorMessage) {
        ShowingSnackbar.showSnackbar(getView() , errorMessage);
    }

    @Override
    public void onAllMealsFetch(List<Meal> meals) {
        allMealsAdapter.setMeals(meals);
        allMealsShimmer.setVisibility(View.GONE);
        allMealsRV.setVisibility(View.VISIBLE);
        allMealsRV.setAdapter(allMealsAdapter);

    }

    @Override
    public void onAllMealsFailed(String errorMessage) {

        ShowingSnackbar.showSnackbar(getView() ,errorMessage);
    }

    @Override
    public void onAllCountriesFetch(List<Meal> countries) {
        allCountriesAdapter.setCountries(countries);
        allCountriesShimmer.setVisibility(View.GONE);
        allCountriesRV.setVisibility(View.VISIBLE);
        allCountriesRV.setAdapter(allCountriesAdapter);

    }

    @Override
    public void onAllCountriesFailed(String errorMessage) {
        ShowingSnackbar.showSnackbar(getView(),errorMessage);
    }

    @Override
    public void onAddedToFavFBSuccess(String addedMessage) {
     ShowingSnackbar.showSnackbar(getView(),addedMessage);
    }


    @Override
    public void insertToDatabase(Meal meal) {
        if(isUser){
            homePresenter.addRandomMealToFav(meal).subscribe();
        }else{
            new MaterialAlertDialogBuilder(getContext())
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
    public void searchByCategoryName(String categoryName) {
        HomeFragmentDirections.ActionHomeFragmentToSearchFragment action = HomeFragmentDirections
                .actionHomeFragmentToSearchFragment();

        action.setCategoryName(categoryName);
        Navigation.findNavController(myView).navigate((NavDirections) action);





    }

    @Override
    public void navigateToFullDetailedMeal(String idMeal) {
        HomeFragmentDirections.ActionHomeFragmentToDetailedMeal action = HomeFragmentDirections
                .actionHomeFragmentToDetailedMeal(idMeal);
        action.setIdMeal(idMeal);
        Navigation.findNavController(myView).navigate((NavDirections) action);
    }

    @Override
    public void searchByCountryName(String countryName) {
        HomeFragmentDirections.ActionHomeFragmentToSearchFragment action = HomeFragmentDirections
                .actionHomeFragmentToSearchFragment();
        action.setCountryName(countryName);
        Navigation.findNavController(getView()).navigate((NavDirections) action);
    }

}