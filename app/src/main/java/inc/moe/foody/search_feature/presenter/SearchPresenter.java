package inc.moe.foody.search_feature.presenter;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import inc.moe.foody.model.Category;
import inc.moe.foody.model.IRepo;
import inc.moe.foody.model.Ingredient;
import inc.moe.foody.model.Meal;
import inc.moe.foody.network.HomeNetworkCallback;
import inc.moe.foody.network.SearchNetworkCallback;
import inc.moe.foody.search_feature.view.ISearch;
import inc.moe.foody.utils.Cache;
import io.reactivex.rxjava3.internal.util.ArrayListSupplier;

public class SearchPresenter implements ISearchPresenter , SearchNetworkCallback  {

    ISearch iSearch;
    private IRepo iRepo;
    char currentChar =' ';
    public SearchPresenter(ISearch iSearch, IRepo iRepo) {
        this.iSearch = iSearch;
        this.iRepo = iRepo;
    }

    @Override
    public void getMealsByCategoryOf(String categoryName) {
        Log.i("TAG", "getMealsByCategoryOf: search presenter");
        iRepo.makeNetworkCallForSearchByCategoryName(this , categoryName);

    }

    @Override
    public void getMealsByCountryOf(String countryName) {
        iRepo.makeNetworkCallForSearchByCountryName(this , countryName);
    }

    @Override
    public void getMealsByIngredientOf(String ingredientName) {
        iRepo.makeNetworkCallForSearchByIngredientName(this ,ingredientName);
    }

    @Override
    public void getAllCategories() {
        if(Cache.getInstance().getCategories() != null)
            iSearch.allCategoriesFetched(Cache.instance.getCategories());
        else
            iSearch.allCategoriesFailed("Categories not found");
    }

    @Override
    public void getAllMeals() {
        if(Cache.getInstance().getAllMeals() != null)
            iSearch.allMealsFetched(Cache.instance.getAllMeals());
        else
            iSearch.allMealsFailed("Meals not found");

    }

    @Override
    public void getAllIngredients() {
        if(Cache.getInstance().getAllIngredients() == null)
            iRepo.makeNetworkCallForGetAllIngredients(this);
        else
            iSearch.allIngredientsFetched(Cache.getInstance().getAllIngredients());
    }

    @Override
    public void getAllCountries() {
        if(Cache.getInstance().getCountries() != null)
            iSearch.allCountriesFetched(Cache.instance.getCountries());
        else
            iSearch.allCountriesFailed("Countries not found");


    }

    @Override
    public void getFilteredCategories(String searchCategories) {
        List<Category> categories = Cache.getInstance().getCategories().stream().filter(category ->
                    category.getStrCategory().toLowerCase().contains(searchCategories.toLowerCase())).collect(Collectors.toCollection(ArrayList::new));
        iSearch.showFilteredCategories(categories);

    }

    @Override
    public void getFilteredCountries(String searchCountries) {
        List<Meal> countries = Cache.getInstance().getCountries().stream().filter(country ->
                country.getStrArea().toLowerCase().contains(searchCountries.toLowerCase())).collect(Collectors.toCollection(ArrayList::new));
        iSearch.showFilteredCountries(countries);


    }

    @Override
    public void getFilteredIngredients(String searchIngredients) {
        List<Ingredient> ingredients = Cache.getInstance().getAllIngredients().stream().filter(ingredient ->
                ingredient.getStrIngredient().toLowerCase().contains(searchIngredients.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new ));
        iSearch.showFilteredIngredients(ingredients);

    }

    @Override
    public void getFilteredMeals(String searchMeal) {
        if (currentChar != ' ' && searchMeal.charAt(0) == currentChar) {
            ArrayList<Meal> filteredMeals = Cache.getInstance().getSearchedMeals().stream()
                    .filter(meal -> meal.getStrMeal().toLowerCase().contains(searchMeal.toLowerCase()))
                    .collect(Collectors.toCollection(ArrayList::new));

            iSearch.showFilteredMeals(filteredMeals);
        } else {
            currentChar = searchMeal.charAt(0);
            iRepo.makeNetworkCallForGettingAllMealsWithLetter( this,String.valueOf(currentChar));
        }
    }

    @Override
    public void onSearchByCategoryNameFromHomeSuccess(List<Meal> meals) {
        Log.i("TAG", "onSearchByCategoryNameFromHomeSuccess: ");
        iSearch.searchByCategoryGotFromHome(meals);

    }

    @Override
    public void onSearchByCategoryNameFromHomeFailure(String errorMessage) {
        Log.i("TAG", "onSearchByCategoryNameFromHomeFailure: ");
    }

    @Override
    public void onGettingAllIngredientsSuccess(List<Ingredient> ingredients) {
        iSearch.allIngredientsFetched(ingredients);
        Cache.getInstance().setAllIngredients(ingredients);
    }

    @Override
    public void onGettingAllIngredientsFailure(String errorMessage) {
        iSearch.allCountriesFailed(errorMessage);
    }

    @Override
    public void onSearchByCountryNameFromHomeSuccess(List<Meal> meals) {
        iSearch.searchByCountryGotFromHome(meals);
    }

    @Override
    public void onSearchByCountryNameFromHomeFailure(String errorMessage) {
        iSearch.searchByCountryFromHomeFailed(errorMessage);
    }

    @Override
    public void onSearchByIngredientNameSuccess(List<Meal> meals) {
        iSearch.searchByIngredientSuccess(meals);
    }

    @Override
    public void onSearchByIngredientNameFailure(String errorMessage) {
        iSearch.searchByIngredientFailure(errorMessage);
    }

    @Override
    public void onSearchByLetterForMealsSuccess(List<Meal> meals) {
        if(meals!=null){
            iSearch.showFilteredMeals(meals);
            Cache.getInstance().setSearchedMeals(meals);

        }else{
            List<Meal> emptyMeals = new ArrayList<Meal>();
            iSearch.showFilteredMeals(emptyMeals);
        }
    }

    @Override
    public void onSearchByLetterForMealsFailure(String errorMessage) {
        iSearch.showFilteredMealsFailure(errorMessage);
    }
}
