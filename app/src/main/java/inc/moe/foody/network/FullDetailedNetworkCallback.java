package inc.moe.foody.network;

import inc.moe.foody.model.Meal;

public interface FullDetailedNetworkCallback {

    void onSuccessFullDetailedMeal(Meal meal);
    void onFailedFullDetialedMeal(String errorMessage);

    void onSuccessAddFavFb(String addedMessage);
    void onFailureAddFavFB(String errorMessage);

}
