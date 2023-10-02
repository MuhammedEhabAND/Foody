package inc.moe.foody.network;

public interface RemoteSource {
    void makeNetworkCallForCategories(HomeNetworkCallback homeNetworkCallback);
    void makeMultipleRandomMealRequests(int numberOfRequests, HomeNetworkCallback homeNetworkCallback);
    void makeNetworkCallForSearchByCategoryName(SearchNetworkCallback searchNetworkCallback, String categoryName);
    void makeNetworkCallForGetFullDetailedMeal(FullDetailedNetworkCallback fullDetailedNetworkCallback , String idMeal);
    void makeNetworkCallForAllCountries(HomeNetworkCallback homeNetworkCallback);

    void makeNetworkCallForAllMeals(HomeNetworkCallback homeNetworkCallback, String letter);

}
