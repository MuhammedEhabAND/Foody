package inc.moe.foody.home_feature.presenter;

import inc.moe.foody.model.ListOfCategories;
import inc.moe.foody.model.ListOfMeals;
import inc.moe.foody.model.Meal;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface IHomePresenter {

    void getAllCategories();
    void getRandomMeal();
    Completable addRandomMealToFav(Meal meal);
    void getAllMeals();
    void getAllCountries();


}
