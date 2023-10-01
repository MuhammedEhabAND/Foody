package inc.moe.foody.model;

import androidx.lifecycle.LiveData;

import java.util.List;

import inc.moe.foody.network.MyNetworkCallBack;

public interface IRepo {

    void makeNetworkCallForCategories(MyNetworkCallBack myNetworkCallBack);
    void makeNetworkCallForRandomMeal(MyNetworkCallBack myNetworkCallBack);
    void makeNetworkCallForSearchByCategoryName(MyNetworkCallBack myNetworkCallBack , String categoryName);

    void removeMealFromFav(Meal meal);
    void insertMealToFav(Meal meal);
    LiveData<List<Meal>> getFavMeals();

}
