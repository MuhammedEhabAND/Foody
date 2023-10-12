package inc.moe.foody.search_feature.view;

import static inc.moe.foody.network.MealClient.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.concurrent.TimeUnit;

import inc.moe.foody.R;
import inc.moe.foody.db.ConcreteLocalSource;
import inc.moe.foody.favourite_feature.view.FavFragmentDirections;
import inc.moe.foody.home_feature.view.OnCategoryClickListener;
import inc.moe.foody.home_feature.view.OnCountryClickListener;
import inc.moe.foody.home_feature.view.OnImageClickListener;
import inc.moe.foody.home_feature.view.OnRandomMealClickListener;
import inc.moe.foody.home_feature.view.RandomMealAdapter;
import inc.moe.foody.model.Category;
import inc.moe.foody.model.Ingredient;
import inc.moe.foody.model.Meal;
import inc.moe.foody.model.Repo;
import inc.moe.foody.network.MealClient;
import inc.moe.foody.search_feature.presenter.SearchPresenter;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SearchFragment extends Fragment implements ISearch  , OnIngredientClickListener , OnImageClickListener , OnCategoryClickListener , OnCountryClickListener {
    SearchPresenter searchPresenter ;
    ShimmerFrameLayout firstShimmer, secondShimmer;
    EditText searchEditText;
    RecyclerView searchRV;
    LinearLayoutManager linearLayoutManager ;
    SearchAdapter searchAdapter;
    String categoryName , countryName;
    RadioGroup searchRadioGroup;
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firstShimmer = view.findViewById(R.id.first_shimmer);
        secondShimmer = view.findViewById(R.id.second_shimmer);
        firstShimmer.startShimmerAnimation();
        secondShimmer.startShimmerAnimation();
        searchEditText = view.findViewById(R.id.search_text_value);

        searchRadioGroup = view.findViewById(R.id.radio_group_search);
        searchRV = view.findViewById(R.id.search_rv);
        linearLayoutManager = new LinearLayoutManager(getContext() );
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        searchAdapter = new SearchAdapter(this , this , this ,this);
        searchRV.setHasFixedSize(true);
        searchRV.setLayoutManager(linearLayoutManager);

        Log.i("TAG", "onViewCreated: "+ categoryName);

        searchPresenter = new SearchPresenter(this ,
                Repo.getInstance( MealClient.getInstance() , ConcreteLocalSource.getInstance(getContext())));
        categoryName = SearchFragmentArgs.fromBundle(getArguments()).getCategoryName();
        if(!categoryName.equals("default")){
            searchPresenter.getMealsByCategoryOf(categoryName);
            searchRadioGroup.check(R.id.meal_radio);
        }
        countryName = SearchFragmentArgs.fromBundle(getArguments()).getCountryName();
        if(!countryName.equals("default")) {
            searchPresenter.getMealsByCountryOf(countryName);
            searchRadioGroup.check(R.id.meal_radio);
        }
        if(categoryName.equals("default")&&countryName.equals("default")){
            searchPresenter.getAllCategories();
        }

        searchRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton checkedRadioButton = group.findViewById(checkedId);

                switch (checkedRadioButton.getText().toString()){
                    case "Category":
                        searchPresenter.getAllCategories();
                        break;
                    case "Meals":
                        searchPresenter.getAllMeals();
                        break;
                    case "Country":
                        searchPresenter.getAllCountries();
                        break;
                    case "Ingredient":
                        searchPresenter.getAllIngredients();
                        break;

                }
            }
        });
        Observable<String> searchObservable = Observable.create(emitter -> {
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String query =charSequence.toString();
                   emitter.onNext(query);

                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            };

            searchEditText.addTextChangedListener(textWatcher);

        });
        searchObservable
                .debounce(500 , TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        query->{

                    int checkedId =searchRadioGroup.getCheckedRadioButtonId();
                    switch(checkedId){
                        case R.id.country_radio:
                            if(!query.isEmpty())
                                searchPresenter.getFilteredCountries(query);
                            else
                                searchPresenter.getAllCountries();
                            break;
                        case R.id.category_radio:
                            if(!query.isEmpty())
                                searchPresenter.getFilteredCategories(query);
                            else
                                searchPresenter.getAllCategories();
                            break;
                        case R.id.meal_radio:
                            if(!query.isEmpty())
                                searchPresenter.getFilteredMeals(query);
                            else
                                searchPresenter.getAllMeals();
                            break;
                        case R.id.ingredient_radio:
                            if(!query.isEmpty())
                                searchPresenter.getFilteredIngredients(query);
                            else
                                searchPresenter.getAllIngredients();
                            break;

                    }},
                        onError->{
                            Snackbar snackbar =Snackbar.make(getView() , "View + " +onError.getMessage() , Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        });
    }

    @Override
    public void searchByCategoryGotFromHome(List<Meal> meals) {

           searchAdapter.setMeals(meals);
           searchAdapter.notifyDataSetChanged();
           searchRV.setAdapter(searchAdapter);
           secondShimmer.setVisibility(View.GONE);
           firstShimmer.setVisibility(View.GONE);
           searchRV.setVisibility(View.VISIBLE);


    }

    @Override
    public void searchByCategoryGotFromHomeFailed(String errorMessage) {
        Snackbar snackbar = Snackbar.make(requireView() , errorMessage , Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void searchByCountryGotFromHome(List<Meal> meals) {
        searchAdapter.setMeals(meals);
        searchAdapter.notifyDataSetChanged();
        searchRV.setAdapter(searchAdapter);
        secondShimmer.setVisibility(View.GONE);
        firstShimmer.setVisibility(View.GONE);
        searchRV.setVisibility(View.VISIBLE);

    }

    @Override
    public void searchByCountryFromHomeFailed(String errorMessage) {
        Snackbar snackbar = Snackbar.make(requireView() , errorMessage , Snackbar.LENGTH_SHORT);
        snackbar.show();

    }

    @Override
    public void allCategoriesFetched(List<Category> categories) {
        searchAdapter.setCategories(categories);
        searchAdapter.notifyDataSetChanged();
        searchRV.setAdapter(searchAdapter);
        secondShimmer.setVisibility(View.GONE);
        firstShimmer.setVisibility(View.GONE);
        searchRV.setVisibility(View.VISIBLE);
    }

    @Override
    public void allCategoriesFailed(String errorMessage) {
        Snackbar snackbar = Snackbar.make(getView() , errorMessage , Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void allMealsFetched(List<Meal> meals) {
        searchAdapter.setMeals(meals);
        searchAdapter.notifyDataSetChanged();
        searchRV.setAdapter(searchAdapter);
        secondShimmer.setVisibility(View.GONE);
        firstShimmer.setVisibility(View.GONE);
        searchRV.setVisibility(View.VISIBLE);
    }

    @Override
    public void allMealsFailed(String errorMessage) {
        Snackbar snackbar = Snackbar.make(getView() , errorMessage , Snackbar.LENGTH_SHORT);
        snackbar.show();

    }

    @Override
    public void allCountriesFetched(List<Meal> countries) {
        searchAdapter.setCountries(countries);
        searchAdapter.notifyDataSetChanged();
        searchRV.setAdapter(searchAdapter);
        secondShimmer.setVisibility(View.GONE);
        firstShimmer.setVisibility(View.GONE);
        searchRV.setVisibility(View.VISIBLE);
    }

    @Override
    public void allCountriesFailed(String errorMessage) {
        Snackbar snackbar = Snackbar.make(getView() , errorMessage , Snackbar.LENGTH_SHORT);
        snackbar.show();

    }

    @Override
    public void allIngredientsFetched(List<Ingredient> ingredients) {
        searchAdapter.setIngredients(ingredients);
        searchAdapter.notifyDataSetChanged();
        searchRV.setAdapter(searchAdapter);
        secondShimmer.setVisibility(View.GONE);
        firstShimmer.setVisibility(View.GONE);
        searchRV.setVisibility(View.VISIBLE);
    }

    @Override
    public void allIngredientsFailed(String errorMessage) {
        Snackbar snackbar = Snackbar.make(getView() , errorMessage , Snackbar.LENGTH_SHORT);
        snackbar.show();

    }

    @Override
    public void searchByIngredientSuccess(List<Meal> meals) {
        searchAdapter.setMeals(meals);
        searchAdapter.notifyDataSetChanged();
        searchRV.setAdapter(searchAdapter);
        secondShimmer.setVisibility(View.GONE);
        firstShimmer.setVisibility(View.GONE);
        searchRV.setVisibility(View.VISIBLE);

    }

    @Override
    public void searchByIngredientFailure(String errorMessage) {
        Snackbar snackbar = Snackbar.make(getView() , errorMessage , Snackbar.LENGTH_SHORT);
        snackbar.show();

    }

    @Override
    public void showFilteredCategories(List<Category> categories) {
        searchAdapter.setCategories(categories);
        searchAdapter.notifyDataSetChanged();
        searchRV.setAdapter(searchAdapter);
        secondShimmer.setVisibility(View.GONE);
        firstShimmer.setVisibility(View.GONE);
        searchRV.setVisibility(View.VISIBLE);

    }

    @Override
    public void showFilteredCountries(List<Meal> countries) {
        searchAdapter.setCountries(countries);
        searchAdapter.notifyDataSetChanged();
        searchRV.setAdapter(searchAdapter);
        secondShimmer.setVisibility(View.GONE);
        firstShimmer.setVisibility(View.GONE);
        searchRV.setVisibility(View.VISIBLE);

    }

    @Override
    public void showFilteredIngredients(List<Ingredient> ingredients) {
        searchAdapter.setIngredients(ingredients);
        searchAdapter.notifyDataSetChanged();
        searchRV.setAdapter(searchAdapter);
        secondShimmer.setVisibility(View.GONE);
        firstShimmer.setVisibility(View.GONE);
        searchRV.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFilteredMeals(List<Meal> filteredMeals) {
        searchAdapter.setMeals(filteredMeals);
        searchAdapter.notifyDataSetChanged();
        searchRV.setAdapter(searchAdapter);
        secondShimmer.setVisibility(View.GONE);
        firstShimmer.setVisibility(View.GONE);
        searchRV.setVisibility(View.VISIBLE);

    }

    @Override
    public void showFilteredMealsFailure(String errorMessage) {
        Snackbar snackbar = Snackbar.make(getView() ,errorMessage , Snackbar.LENGTH_SHORT);
        snackbar.show();
    }


    @Override
    public void navigateToFullDetailedMeal(String idMeal) {
        SearchFragmentDirections.ActionSearchFragmentToDetailedMeal action = SearchFragmentDirections
                .actionSearchFragmentToDetailedMeal(idMeal);
        action.setIdMeal(idMeal);
        Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void searchByCategoryName(String categoryName) {
        searchRadioGroup.check(R.id.meal_radio);

        searchPresenter.getMealsByCategoryOf(categoryName);
    }

    @Override
    public void searchByCountryName(String countryName) {
        searchRadioGroup.check(R.id.meal_radio);

        searchPresenter.getMealsByCountryOf(countryName);

    }

    @Override
    public void searchMealsByIngredient(String ingredientName) {
        searchRadioGroup.check(R.id.meal_radio);

        searchPresenter.getMealsByIngredientOf(ingredientName);

    }
}