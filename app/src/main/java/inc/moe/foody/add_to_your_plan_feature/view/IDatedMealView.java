package inc.moe.foody.add_to_your_plan_feature.view;

import inc.moe.foody.model.Meal;

public interface IDatedMealView {
    void onFullDetailedMealFetch(Meal meal);
    void onFullDetailedMealFailed(String errorMessage);
    void onSuccessAddPlannedMeal(String addedMessage);

}
