package inc.moe.foody.favourite_feature.presenter;

import java.util.List;

import inc.moe.foody.model.Meal;
import io.reactivex.rxjava3.core.Flowable;

public interface IFavouritePresenter {
    Flowable<List<Meal>> getFavMeals();

    void removeFromDataBase(Meal meal);

    void getMealsFromFirebase();

}
