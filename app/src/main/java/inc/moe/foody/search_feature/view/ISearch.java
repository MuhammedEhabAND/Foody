package inc.moe.foody.search_feature.view;

import java.util.List;

import inc.moe.foody.model.Category;
import inc.moe.foody.model.Ingredient;
import inc.moe.foody.model.Meal;

public interface ISearch {
    void searchByCategoryGotFromHome(List<Meal> meals);
    void searchByCountryGotFromHome(List<Meal> meals);
    void searchByCountryFromHomeFailed(String errorMessage);
    void allCategoriesFetched(List<Category> categories);
    void allCategoriesFailed(String errorMessage);
    void allMealsFetched(List<Meal> meals);
    void allMealsFailed(String errorMessage);
    void allCountriesFetched(List<Meal> countries);
    void allCountriesFailed(String errorMessage);
    void allIngredientsFetched(List<Ingredient> ingredients);
    void allIngredientsFailed(String errorMessage);
}
