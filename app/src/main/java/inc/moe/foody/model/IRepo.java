package inc.moe.foody.model;

import androidx.lifecycle.LiveData;

import java.util.List;

import inc.moe.foody.network.FullDetailedNetworkCallback;
import inc.moe.foody.network.HomeNetworkCallback;
import inc.moe.foody.network.SearchNetworkCallback;

public interface IRepo {

    void makeNetworkCallForCategories(HomeNetworkCallback homeNetworkCallback);
    void makeNetworkCallForRandomMeal(HomeNetworkCallback homeNetworkCallback);
    void makeNetworkCallForSearchByCategoryName(SearchNetworkCallback searchNetworkCallback, String categoryName);
    void makeNetworkCallForSearchByCountryName(SearchNetworkCallback searchNetworkCallback, String countryName);
    void makeNetworkCallForGettingAllCountries(HomeNetworkCallback homeNetworkCallback);
    void makeNetworkCallForGettingAllMealsWithLetter(HomeNetworkCallback homeNetworkCallback , String letter);
    void makeNetworkCallForGetFullDetailedMeal(FullDetailedNetworkCallback homeNetworkCallback , String idMeal);

    void makeNetworkCallForGetAllIngredients(SearchNetworkCallback searchNetworkCallback );


    void removeMealFromFav(Meal meal);
    void insertMealToFav(Meal meal);
    LiveData<List<Meal>> getFavMeals();

}
