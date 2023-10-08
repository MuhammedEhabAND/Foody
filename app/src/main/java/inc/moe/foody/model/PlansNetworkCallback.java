package inc.moe.foody.model;

import java.util.List;

public interface PlansNetworkCallback {
    void onGettingPlansSuccess(List<PlannedMeal> plannedMealList);
    void onGettingPlansFailure(String errorMessage);
}
