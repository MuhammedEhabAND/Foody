package inc.moe.foody.favourite_feature.view;

import java.util.List;

import inc.moe.foody.model.Meal;

public interface IFav {
    void onGettingFavDataFromDBSuccess(List<Meal> mealList);
    void onGettingFavDataFromDBFailure(String errorMessage);

    void onRemoveFromCloud(String message);
}
