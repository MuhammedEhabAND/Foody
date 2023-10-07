package inc.moe.foody.network;

import java.util.List;

import inc.moe.foody.model.Meal;

public interface FavCallBack {
    void onSuccessGetFavFb(List<Meal> meals);
    void onFailureGetFavFB(String errorMessage);
    void onRemoveMealFBSuccess(String message);

}
