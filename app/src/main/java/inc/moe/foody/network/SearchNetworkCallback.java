package inc.moe.foody.network;

import java.util.List;

import inc.moe.foody.model.Ingredient;
import inc.moe.foody.model.Meal;

public interface SearchNetworkCallback {
    void onSearchByCategoryNameFromHomeSuccess(List<Meal> meals );
    void onSearchByCategoryNameFromHomeFailure(String errorMessage);

    void onGettingAllIngredientsSuccess(List<Ingredient> ingredients);
    void onGettingAllIngredientsFailure(String errorMessage);

    void onSearchByCountryNameFromHomeSuccess(List<Meal> meals );
    void onSearchByCountryNameFromHomeFailure(String errorMessage);


}
