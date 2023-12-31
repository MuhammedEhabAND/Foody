package inc.moe.foody.plan_feature.view;

import java.util.List;

import inc.moe.foody.model.PlannedMeal;

public interface IPlansView {

    void onGettingPlansSuccess(List<PlannedMeal> plannedMealList);
    void onGettingPlansFailure(String errorMessage);
}
