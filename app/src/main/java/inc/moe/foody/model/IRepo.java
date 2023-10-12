package inc.moe.foody.model;

import androidx.lifecycle.LiveData;

import java.util.List;

import inc.moe.foody.network.DatedMealNetworkCallback;
import inc.moe.foody.network.FavCallBack;
import inc.moe.foody.network.FullDetailedNetworkCallback;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface IRepo {

    Single<ListOfCategories> makeNetworkCallForCategories();
    Single<ListOfMeals> makeNetworkCallForRandomMeal();
    Single<ListOfMeals> makeNetworkCallForSearchByCategoryName( String query);
    Single<ListOfMeals> makeNetworkCallForSearchByCountryName(String query);
    Single<ListOfMeals> makeNetworkCallForSearchByIngredientName(String ingredientName);
    Single<ListOfMeals> makeNetworkCallForGettingAllCountries();
    Single<ListOfMeals> makeNetworkCallForGettingAllMealsWithLetter(String letter);
    Single<ListOfMeals> makeNetworkCallForGetFullDetailedMeal( String idMeal);

    Single<ListOfIngredients> makeNetworkCallForGetAllIngredients();



    void removeMealFromFav(Meal meal);
    Completable insertMealToFav(Meal meal);
    Flowable<List<Meal>> getFavMeals();
    void onAddingPlansToFB(DatedMealNetworkCallback datedMealNetworkCallback , PlannedMeal meal);
    void onGettingPlansFromFB(PlansNetworkCallback plansNetworkCallback );

    void onRemovePlansToFB(DatedMealNetworkCallback datedMealNetworkCallback , PlannedMeal meal);
    void addPlannedMeal(PlannedMeal plannedMeal);

    void removePlannedMeal(PlannedMeal plannedMeal);

    LiveData<List<PlannedMeal>> getAllPlannedMeal();

    void getFavMealsFB(FavCallBack favCallBack);
    void addFavMealToFB(FullDetailedNetworkCallback fullDetailedNetworkCallback, Meal meal );
    void removeFavMealFromFB(FavCallBack favCallBack, Meal meal );

}
