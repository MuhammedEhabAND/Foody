package inc.moe.foody.model;

import androidx.lifecycle.LiveData;

import java.util.List;

import inc.moe.foody.network.DatedMealNetworkCallback;
import inc.moe.foody.network.FavCallBack;
import inc.moe.foody.network.FullDetailedNetworkCallback;
import inc.moe.foody.network.HomeNetworkCallback;
import inc.moe.foody.network.SearchNetworkCallback;
import inc.moe.foody.plan_feature.view.PlansFragment;

public interface IRepo {

    void makeNetworkCallForCategories(HomeNetworkCallback homeNetworkCallback);
    void makeNetworkCallForCategories(SearchNetworkCallback searchNetworkCallback);
    void makeNetworkCallForRandomMeal(HomeNetworkCallback homeNetworkCallback);
    void makeNetworkCallForSearchByCategoryName(SearchNetworkCallback searchNetworkCallback, String categoryName);
    void makeNetworkCallForSearchByCountryName(SearchNetworkCallback searchNetworkCallback, String countryName);
    void makeNetworkCallForSearchByIngredientName(SearchNetworkCallback searchNetworkCallback, String ingredientName);
    void makeNetworkCallForGettingAllCountries(HomeNetworkCallback homeNetworkCallback);
    void makeNetworkCallForGettingAllCountries(SearchNetworkCallback searchNetworkCallback);
    void makeNetworkCallForGettingAllMealsWithLetter(HomeNetworkCallback homeNetworkCallback , String letter);
    void makeNetworkCallForGettingAllMealsWithLetter(SearchNetworkCallback searchNetworkCallback , String letter);
    void makeNetworkCallForGetFullDetailedMeal(FullDetailedNetworkCallback homeNetworkCallback , String idMeal);

    void makeNetworkCallForGetAllIngredients(SearchNetworkCallback searchNetworkCallback );



    void removeMealFromFav(Meal meal);
    void insertMealToFav(Meal meal);
    LiveData<List<Meal>> getFavMeals();
    void onAddingPlansToFB(DatedMealNetworkCallback datedMealNetworkCallback , PlannedMeal meal);
    void onGettingPlansFromFB(PlansNetworkCallback plansNetworkCallback );

    void onRemovePlansToFB(DatedMealNetworkCallback datedMealNetworkCallback , PlannedMeal meal);
    void addPlannedMeal(PlannedMeal plannedMeal);

    void removePlannedMeal(PlannedMeal plannedMeal);

    LiveData<List<PlannedMeal>> getAllPlannedMeal();

    void getFavMealsFB(FavCallBack favCallBack);
    void addFavMealToFB(HomeNetworkCallback homeNetworkCallback , Meal meal );
    void addFavMealToFB(FullDetailedNetworkCallback fullDetailedNetworkCallback , Meal meal );
    void removeFavMealFromFB(FavCallBack favCallBack, Meal meal );

}
