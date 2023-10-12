package inc.moe.foody.add_to_your_plan_feature.presenter;

import inc.moe.foody.model.PlannedMeal;

public interface IDatedMealPresenter {
    void getMealById(String idMeal);
    void addPlannedMeal(PlannedMeal plannedMeal );
    void removePlannedMeal(PlannedMeal plannedMeal );
}
