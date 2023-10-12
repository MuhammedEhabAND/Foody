package inc.moe.foody.network;

import inc.moe.foody.model.Meal;

public interface FullDetailedNetworkCallback {

    void onSuccessAddFavFb(String addedMessage);
    void onFailureAddFavFB(String errorMessage);

}
