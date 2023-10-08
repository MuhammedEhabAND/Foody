package inc.moe.foody.db;

import androidx.lifecycle.LiveData;

import java.util.List;

import inc.moe.foody.model.Meal;
import inc.moe.foody.model.PlannedMeal;

public interface LocalSource {

        void addMeal(Meal meal );

        void removeMeal(Meal meal);

        LiveData<List<Meal>> getFavMealsLiveData();

        void addPlannedMeal(PlannedMeal plannedMeal);

        void removePlannedMeal(PlannedMeal plannedMeal);

        LiveData<List<PlannedMeal>> getAllPlannedMeal();


}
