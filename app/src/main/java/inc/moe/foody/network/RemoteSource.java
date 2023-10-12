package inc.moe.foody.network;

import inc.moe.foody.model.ListOfCategories;
import inc.moe.foody.model.ListOfIngredients;
import inc.moe.foody.model.ListOfMeals;
import inc.moe.foody.model.Meal;
import inc.moe.foody.model.PlannedMeal;
import inc.moe.foody.model.PlansNetworkCallback;
import io.reactivex.rxjava3.core.Single;

public interface RemoteSource {

    Single<ListOfCategories> makeNetworkCallForCategories();
    Single<ListOfMeals> getRandomMeal();
    Single<ListOfMeals> makeNetworkCallForSearchByCategoryName(String query);
    Single<ListOfMeals> makeNetworkCallForSearchByCountryName( String query);
    Single<ListOfMeals> makeNetworkCallForSearchByIngredientName(String ingredientName);
    Single<ListOfMeals> makeNetworkCallForGetFullDetailedMeal(String idMeal);
    Single<ListOfMeals> makeNetworkCallForAllCountries();
    Single<ListOfIngredients> makeNetworkCallForAllIngredients();

    Single<ListOfMeals> makeNetworkCallForSearchByFirstLetter( String letter);
    void onGettingFavFromFB(FavCallBack favCallBack );
    void onAddingFavToFB(FullDetailedNetworkCallback fullDetailedNetworkCallback , Meal meal);
    void onRemoveFavFromFB(FavCallBack favCallBack , Meal meal);

    void onAddingPlansToFB(DatedMealNetworkCallback datedMealNetworkCallback , PlannedMeal meal);
    void onRemovePlansToFB(DatedMealNetworkCallback datedMealNetworkCallback , PlannedMeal meal);
    void onGettingPlansFromFB(PlansNetworkCallback plansNetworkCallback );


}
