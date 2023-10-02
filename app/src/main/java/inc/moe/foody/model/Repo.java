package inc.moe.foody.model;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import inc.moe.foody.db.LocalSource;
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
    public void makeNetworkCallForRandomMeal(HomeNetworkCallback homeNetworkCallback) {
        remoteSource.makeMultipleRandomMealRequests( 5 , homeNetworkCallback);
    }

    @Override
    public void makeNetworkCallForSearchByCategoryName(SearchNetworkCallback searchNetworkCallback, String categoryName) {
        Log.i("TAG", "makeNetworkCallForSearchByCategoryName: ");
        remoteSource.makeNetworkCallForSearchByCategoryName(searchNetworkCallback,categoryName);
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
}
