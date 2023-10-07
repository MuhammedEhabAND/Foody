package inc.moe.foody.network;

import inc.moe.foody.model.Meal;

public interface RemoteSource {
    void makeNetworkCallForCategories(HomeNetworkCallback homeNetworkCallback);
    void makeNetworkCallForCategories(SearchNetworkCallback searchNetworkCallback);
    void makeMultipleRandomMealRequests(int numberOfRequests, HomeNetworkCallback homeNetworkCallback);
    void makeNetworkCallForSearchByCategoryName(SearchNetworkCallback searchNetworkCallback, String categoryName);
    void makeNetworkCallForSearchByCountryName(SearchNetworkCallback searchNetworkCallback, String countryName);
    void makeNetworkCallForSearchByIngredientName(SearchNetworkCallback searchNetworkCallback, String ingredientName);
    void makeNetworkCallForGetFullDetailedMeal(FullDetailedNetworkCallback fullDetailedNetworkCallback , String idMeal);
    void makeNetworkCallForAllCountries(HomeNetworkCallback homeNetworkCallback);
    void makeNetworkCallForAllCountries(SearchNetworkCallback searchNetworkCallback);
    void makeNetworkCallForAllIngredients(SearchNetworkCallback searchNetworkCallback);

    void makeNetworkCallForSearchByFirstLetter(HomeNetworkCallback homeNetworkCallback, String letter);
    void makeNetworkCallForSearchByFirstLetter(SearchNetworkCallback searchNetworkCallback, String letter);
    void onGettingFavFromFB(FavCallBack favCallBack );
    void onAddingFavToFB(HomeNetworkCallback homeNetworkCallback , Meal meal);
    void onAddingFavToFB(FullDetailedNetworkCallback fullDetailedNetworkCallback , Meal meal);
    void onRemoveFavFromFB(FavCallBack favCallBack , Meal meal);

}
