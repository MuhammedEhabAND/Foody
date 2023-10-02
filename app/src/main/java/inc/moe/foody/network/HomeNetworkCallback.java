package inc.moe.foody.network;

import java.util.List;

import inc.moe.foody.model.Category;
import inc.moe.foody.model.Country;
import inc.moe.foody.model.Meal;

public interface HomeNetworkCallback {

    void onSuccessCategories(List<Category> categories);
    void onFailedCategories(String errorMessage);

    void onSuccessRandomMeal(List<Meal> meals);
    void onFailedRandomMeal(String errorMessage);

    void onSuccessAllMealsWithBLetter(List<Meal> meals);
    void onFailedAllMealsWithBLetter(String errorMessage);

    void onSuccessAllCountries(List<Meal> countries);
    void onFailedAllCountries(String errorMessage);





}
