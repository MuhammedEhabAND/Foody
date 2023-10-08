package inc.moe.foody.plan_feature.presenter;

import androidx.lifecycle.LiveData;

import java.util.List;

import inc.moe.foody.model.Meal;
import inc.moe.foody.model.PlannedMeal;

public interface IPlansPresenter {
    LiveData<List<PlannedMeal>> getPlannedMeal();

    void getPlannedMealsFromFirebase();

}
