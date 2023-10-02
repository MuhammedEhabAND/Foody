package inc.moe.foody.model;

import androidx.lifecycle.LiveData;

import java.util.List;

import inc.moe.foody.network.HomeNetworkCallback;
import inc.moe.foody.network.SearchNetworkCallback;

public interface IRepo {

    void makeNetworkCallForCategories(HomeNetworkCallback homeNetworkCallback);
    void makeNetworkCallForRandomMeal(HomeNetworkCallback homeNetworkCallback);
    void makeNetworkCallForSearchByCategoryName(SearchNetworkCallback searchNetworkCallback, String categoryName);

    void removeMealFromFav(Meal meal);
    void insertMealToFav(Meal meal);
    LiveData<List<Meal>> getFavMeals();

}
