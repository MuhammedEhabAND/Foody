package inc.moe.foody.full_details_feature.view;

import inc.moe.foody.model.Meal;

public interface IDetailedMeal {

    void onFullDetailedMealFetch(Meal meal);
    void onFullDetailedMealFailed(String errorMessage);
}
