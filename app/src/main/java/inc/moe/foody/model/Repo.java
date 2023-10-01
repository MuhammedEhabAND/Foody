package inc.moe.foody.model;

import androidx.lifecycle.LiveData;

import java.util.List;

import inc.moe.foody.db.LocalSource;
import inc.moe.foody.network.MyNetworkCallBack;
import inc.moe.foody.network.RemoteSource;

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
    public void makeNetworkCallForCategories(MyNetworkCallBack myNetworkCallBack) {
        remoteSource.makeNetworkCallForCategories(myNetworkCallBack);
    }

    @Override
    public void makeNetworkCallForRandomMeal(MyNetworkCallBack myNetworkCallBack) {
        remoteSource.makeMultipleRandomMealRequests( 5 ,myNetworkCallBack);
    }

    @Override
    public void makeNetworkCallForSearchByCategoryName(MyNetworkCallBack myNetworkCallBack, String categoryName) {
        remoteSource.makeNetworkCallForSearchByCategoryName(myNetworkCallBack  ,categoryName);
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
