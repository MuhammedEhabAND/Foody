package inc.moe.foody.home_feature.presenter;

import inc.moe.foody.model.Meal;

public interface IHomePresenter {
    void getAllCategories();
    void getRandomMeal();
    void addRandomMealToFav(Meal meal);
    void getAllMeals();
    void getAllCountries();


}
