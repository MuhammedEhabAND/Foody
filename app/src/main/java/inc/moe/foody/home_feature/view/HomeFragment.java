package inc.moe.foody.home_feature.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import inc.moe.foody.R;
import inc.moe.foody.db.ConcreteLocalSource;
import inc.moe.foody.home_feature.presenter.HomePresenter;
import inc.moe.foody.model.Category;
import inc.moe.foody.model.Country;
import inc.moe.foody.model.Meal;
import inc.moe.foody.model.Repo;
import inc.moe.foody.network.MealClient;


public class HomeFragment extends Fragment implements IHome, OnRandomMealClickListener , OnCategoryClickListener ,OnImageClickListener {
    RecyclerView allCategoriesRV ,randomMealRV ,allMealsRV ,allCountriesRV;
    ShimmerFrameLayout randomMealShimmer ,categoryMealShimmer , allMealsShimmer , allCountriesShimmer;
    HomePresenter homePresenter;
    CategoryAdapter categoryAdapter;
    RandomMealAdapter randomMealAdapter;
    AllMealsAdapter allMealsAdapter;
    AllCountriesAdapter allCountriesAdapter ;
    LinearLayoutManager categoryLayoutManager , randomMealsLayoutManager , allMealsLayoutManager ,allCountriesLayoutManger;
    View myView ;
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
        allCountriesAdapter = new AllCountriesAdapter();

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
        homePresenter.getRandomMeal();
        homePresenter.getAllCategories();
        homePresenter.getAllMeals();
        homePresenter.getAllCountries();
    }

    @Override
    public void onCategoryFetch(List<Category> categories) {
        categoryAdapter.setCategoryList(categories);
        categoryAdapter.notifyDataSetChanged();
        allCategoriesRV.setAdapter(categoryAdapter);
        categoryMealShimmer.setVisibility(View.GONE);
        allCategoriesRV.setVisibility(View.VISIBLE);

    }

    @Override
    public void onRandomMealFetch(List<Meal> meals) {

        randomMealAdapter.setMealList(meals);
        randomMealAdapter.notifyDataSetChanged();
        randomMealRV.setAdapter(randomMealAdapter);
        randomMealShimmer.setVisibility(View.GONE);
        randomMealRV.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCategoryFailed(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRandomMealFailed(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAllMealsFetch(List<Meal> meals) {
        allMealsAdapter.setMeals(meals);
        allMealsAdapter.notifyDataSetChanged();
        allMealsRV.setAdapter(allMealsAdapter);
        allMealsShimmer.setVisibility(View.GONE);
        allMealsRV.setVisibility(View.VISIBLE);

    }

    @Override
    public void onAllMealsFailed(String errorMessage) {

    }

    @Override
    public void onAllCountriesFetch(List<Meal> countries) {
        allCountriesAdapter.setCountries(countries);
        allCountriesAdapter.notifyDataSetChanged();
        allCountriesRV.setAdapter(allCountriesAdapter);
        allCountriesShimmer.setVisibility(View.GONE);
        allCountriesRV.setVisibility(View.VISIBLE);

    }

    @Override
    public void onAllCountriesFailed(String errorMessage) {
        Snackbar snackbar = Snackbar.make(getView() , errorMessage ,Snackbar.LENGTH_SHORT);
        snackbar.show();
    }


    @Override
    public void insertToDatabase(Meal meal) {
        homePresenter.addRandomMealToFav(meal);
        Snackbar snackbar = Snackbar.make(getView() ,meal.getStrMeal()+" saved." ,Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void searchByCategoryName(String categoryName) {
        HomeFragmentDirections.ActionHomeFragmentToSearchFragment action = HomeFragmentDirections
                .actionHomeFragmentToSearchFragment();

        action.setCategoryName(categoryName);

//        HomeActivity.navController.navigate(action);
        Navigation.findNavController(myView).navigate(action);





    }

    @Override
    public void navigateToFullDetailedMeal(String idMeal) {
        HomeFragmentDirections.ActionHomeFragmentToDetailedMeal action = HomeFragmentDirections
                .actionHomeFragmentToDetailedMeal(idMeal);
        action.setIdMeal(idMeal);
        Navigation.findNavController(myView).navigate(action);
    }
}