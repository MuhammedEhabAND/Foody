package inc.moe.foody.model;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import inc.moe.foody.db.LocalSource;
import inc.moe.foody.network.DatedMealNetworkCallback;
import inc.moe.foody.network.FavCallBack;
import inc.moe.foody.network.FullDetailedNetworkCallback;
import inc.moe.foody.network.RemoteSource;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
    public Single<ListOfCategories> makeNetworkCallForCategories() {
       return remoteSource.makeNetworkCallForCategories();
    }

    @Override
    public Single<ListOfMeals> makeNetworkCallForRandomMeal() {
       return remoteSource.getRandomMeal();
    }

    @Override
    public Single<ListOfMeals> makeNetworkCallForSearchByCategoryName(String query) {
        Log.i("TAG", "makeNetworkCallForSearchByCategoryName: ");
        return remoteSource.makeNetworkCallForSearchByCategoryName(query).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<ListOfMeals> makeNetworkCallForSearchByCountryName(String countryName) {
        return remoteSource.makeNetworkCallForSearchByCountryName(countryName);
    }

    @Override
    public Single<ListOfMeals> makeNetworkCallForSearchByIngredientName(String ingredientName) {
       return remoteSource.makeNetworkCallForSearchByIngredientName(ingredientName);
    }

    @Override
    public Single<ListOfMeals> makeNetworkCallForGettingAllCountries() {
       return remoteSource.makeNetworkCallForAllCountries();
    }


    @Override
    public Single<ListOfMeals> makeNetworkCallForGettingAllMealsWithLetter(String letter) {
        return  remoteSource.makeNetworkCallForSearchByFirstLetter(letter);
    }

    @Override
    public Single<ListOfMeals> makeNetworkCallForGetFullDetailedMeal( String idMeal) {
        return remoteSource.makeNetworkCallForGetFullDetailedMeal( idMeal);
    }

    @Override
    public Single<ListOfIngredients> makeNetworkCallForGetAllIngredients() {
        return remoteSource.makeNetworkCallForAllIngredients();
    }

    @Override
    public void removeMealFromFav(Meal meal) {
        localSource.removeMeal(meal);
    }

    @Override
    public Completable insertMealToFav(Meal meal) {

        return  localSource.addMeal(meal).subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<Meal>> getFavMeals() {

        return localSource.getFavMealsLiveData().map(meals -> {
            List<Meal> filteredMeals = new ArrayList<>();
            for (Meal meal : meals) {
                if (meal.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    filteredMeals.add(meal);
                }
            }
            return filteredMeals;
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public void onAddingPlansToFB(DatedMealNetworkCallback datedMealNetworkCallback, PlannedMeal meal) {
        remoteSource.onAddingPlansToFB(datedMealNetworkCallback , meal);
    }

    @Override
    public void onGettingPlansFromFB(PlansNetworkCallback plansNetworkCallback) {
        remoteSource.onGettingPlansFromFB(plansNetworkCallback);
    }

    @Override
    public void onRemovePlansToFB(DatedMealNetworkCallback datedMealNetworkCallback, PlannedMeal meal) {
        remoteSource.onRemovePlansToFB(datedMealNetworkCallback , meal);
    }

    @Override
    public void addPlannedMeal(PlannedMeal plannedMeal) {
        localSource.addPlannedMeal(plannedMeal);
    }

    @Override
    public void removePlannedMeal(PlannedMeal plannedMeal) {
        localSource.removePlannedMeal(plannedMeal);
    }

    @Override
    public LiveData<List<PlannedMeal>> getAllPlannedMeal() {
        return localSource.getAllPlannedMeal();
    }

    @Override
    public void getFavMealsFB(FavCallBack favCallBack) {
         remoteSource.onGettingFavFromFB(favCallBack);
    }

    @Override
    public void addFavMealToFB(FullDetailedNetworkCallback fullDetailedNetworkCallback, Meal meal) {
        remoteSource.onAddingFavToFB(fullDetailedNetworkCallback, meal);
    }


    @Override
    public void removeFavMealFromFB(FavCallBack favCallBack, Meal meal) {
        remoteSource.onRemoveFavFromFB(favCallBack , meal);
    }
}
