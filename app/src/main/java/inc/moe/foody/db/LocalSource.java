package inc.moe.foody.db;

import androidx.lifecycle.LiveData;

import java.util.List;

import inc.moe.foody.model.Meal;

public interface LocalSource {

        void addMeal(Meal meal);

        void removeMeal(Meal meal);

        LiveData<List<Meal>> getFavMealsLiveData();


}
