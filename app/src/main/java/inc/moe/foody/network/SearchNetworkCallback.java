package inc.moe.foody.network;

import java.util.List;

import inc.moe.foody.model.Category;
import inc.moe.foody.model.Ingredient;
import inc.moe.foody.model.Meal;

public interface SearchNetworkCallback {
    void onSearchByCategoryNameFromHomeSuccess(List<Meal> meals );
    void onSearchByCategoryNameFromHomeFailure(String errorMessage);

    void onGettingAllIngredientsSuccess(List<Ingredient> ingredients);
    void onGettingAllIngredientsFailure(String errorMessage);

    void onSearchByCountryNameFromHomeSuccess(List<Meal> meals );
    void onSearchByCountryNameFromHomeFailure(String errorMessage);

    void onSearchByIngredientNameSuccess(List<Meal> meals );
    void onSearchByIngredientNameFailure(String errorMessage);

    void onSearchByLetterForMealsSuccess(List<Meal> meals);
    void onSearchByLetterForMealsFailure(String errorMessage);

    void onSuccessCategories(List<Category> categoryList);
    void onFailureCategories(String errorMessage);

    void onSuccessAllCountries(List<Meal> countries);
    void onFailureAllCountries(String errorMessage);

}
