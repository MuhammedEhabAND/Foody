package inc.moe.foody.db;

import androidx.lifecycle.LiveData;

import java.util.List;

import inc.moe.foody.model.Meal;
import inc.moe.foody.model.PlannedMeal;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface LocalSource {

        Completable addMeal(Meal meal );

        void removeMeal(Meal meal);

        Flowable<List<Meal>> getFavMealsLiveData();

        void addPlannedMeal(PlannedMeal plannedMeal);

        void removePlannedMeal(PlannedMeal plannedMeal);

        LiveData<List<PlannedMeal>> getAllPlannedMeal();


}
