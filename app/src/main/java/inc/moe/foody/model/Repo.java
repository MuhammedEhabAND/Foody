package inc.moe.foody.model;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import inc.moe.foody.db.LocalSource;
import inc.moe.foody.network.FavCallBack;
import inc.moe.foody.network.FullDetailedNetworkCallback;
import inc.moe.foody.network.HomeNetworkCallback;
import inc.moe.foody.network.RemoteSource;
import inc.moe.foody.network.SearchNetworkCallback;

public class Repo implements IRepo{
    RemoteSource remoteSource;
    LocalSource localSource;

    private static Repo instance = null;



    private Repo(RemoteSource remoteSource,LocalSource localSource) {
        this.remoteSource = remoteSource;
        this.localSource = localSource;
    }

    public static Repo getInstance(RemoteSource remoteSource,LocalSource localSource) {
        if (instance == null) return new Repo(remoteSource ,localSource);
        return instance;
    }


    @Override
    public void makeNetworkCallForCategories(HomeNetworkCallback homeNetworkCallback) {
        remoteSource.makeNetworkCallForCategories(homeNetworkCallback);
    }

    @Override
    public void makeNetworkCallForCategories(SearchNetworkCallback searchNetworkCallback) {
        remoteSource.makeNetworkCallForCategories(searchNetworkCallback);
    }

    @Override
    public void makeNetworkCallForRandomMeal(HomeNetworkCallback homeNetworkCallback) {
        remoteSource.makeMultipleRandomMealRequests( 5 , homeNetworkCallback);
    }

    @Override
    public void makeNetworkCallForSearchByCategoryName(SearchNetworkCallback searchNetworkCallback, String categoryName) {
        Log.i("TAG", "makeNetworkCallForSearchByCategoryName: ");
        remoteSource.makeNetworkCallForSearchByCategoryName(searchNetworkCallback,categoryName);
    }

    @Override
    public void makeNetworkCallForSearchByCountryName(SearchNetworkCallback searchNetworkCallback, String countryName) {
        remoteSource.makeNetworkCallForSearchByCountryName(searchNetworkCallback,countryName);
    }

    @Override
    public void makeNetworkCallForSearchByIngredientName(SearchNetworkCallback searchNetworkCallback, String ingredientName) {
        remoteSource.makeNetworkCallForSearchByIngredientName( searchNetworkCallback,ingredientName);
    }

    @Override
    public void makeNetworkCallForGettingAllCountries(HomeNetworkCallback homeNetworkCallback) {
        remoteSource.makeNetworkCallForAllCountries(homeNetworkCallback);
    }

    @Override
    public void makeNetworkCallForGettingAllCountries(SearchNetworkCallback searchNetworkCallback) {
        remoteSource.makeNetworkCallForAllCountries(searchNetworkCallback);
    }

    @Override
    public void makeNetworkCallForGettingAllMealsWithLetter(HomeNetworkCallback homeNetworkCallback, String letter) {
        remoteSource.makeNetworkCallForSearchByFirstLetter(homeNetworkCallback, letter);
    }

    @Override
    public void makeNetworkCallForGettingAllMealsWithLetter(SearchNetworkCallback searchNetworkCallback, String letter) {
        remoteSource.makeNetworkCallForSearchByFirstLetter(searchNetworkCallback ,letter);
    }

    @Override
    public void makeNetworkCallForGetFullDetailedMeal(FullDetailedNetworkCallback fullDetailedNetworkCallback, String idMeal) {
        remoteSource.makeNetworkCallForGetFullDetailedMeal(fullDetailedNetworkCallback ,idMeal);
    }

    @Override
    public void makeNetworkCallForGetAllIngredients(SearchNetworkCallback searchNetworkCallback) {
        remoteSource.makeNetworkCallForAllIngredients(searchNetworkCallback);
    }

    @Override
    public void removeMealFromFav(Meal meal) {
        localSource.removeMeal(meal);
    }

    @Override
    public void insertMealToFav(Meal meal) {
        localSource.addMeal(meal);
    }

    @Override
    public LiveData<List<Meal>> getFavMeals() {
        return localSource.getFavMealsLiveData();
    }

    @Override
    public void getFavMealsFB(FavCallBack favCallBack) {
         remoteSource.onGettingFavFromFB(favCallBack);
    }

    @Override
    public void addFavMealToFB(HomeNetworkCallback homeNetworkCallback, Meal meal) {
        remoteSource.onAddingFavToFB(homeNetworkCallback , meal);
    }

    @Override
    public void addFavMealToFB(FullDetailedNetworkCallback fullDetailedNetworkCallback, Meal meal) {
        remoteSource.onAddingFavToFB(fullDetailedNetworkCallback , meal);

    }

    @Override
    public void removeFavMealFromFB(FavCallBack favCallBack, Meal meal) {
        remoteSource.onRemoveFavFromFB(favCallBack , meal);
    }
}
