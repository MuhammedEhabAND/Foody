package inc.moe.foody.search_feature.presenter;

import android.util.Log;

import java.util.List;

import inc.moe.foody.model.IRepo;
import inc.moe.foody.model.Ingredient;
import inc.moe.foody.model.Meal;
import inc.moe.foody.network.HomeNetworkCallback;
import inc.moe.foody.network.SearchNetworkCallback;
import inc.moe.foody.search_feature.view.ISearch;
import inc.moe.foody.utils.Cache;

public class SearchPresenter implements ISearchPresenter , SearchNetworkCallback  {

    ISearch iSearch;
    private IRepo iRepo;

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
}
