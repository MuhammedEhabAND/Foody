package inc.moe.foody.favourite_feature.presenter;

import androidx.lifecycle.LiveData;

import java.util.List;

import inc.moe.foody.model.Meal;

public interface IFavouritePresenter {
    LiveData<List<Meal>> getFavMeals();

    void removeFromDataBase(Meal meal);

    void getMealsFromFirebase();

}
