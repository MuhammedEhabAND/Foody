package inc.moe.foody.search_feature.presenter;

import java.util.List;

import inc.moe.foody.model.Category;

public interface ISearchPresenter {

    void getMealsByCategoryOf( String categoryName);
    void getMealsByCountryOf( String countryName);
    void getMealsByIngredientOf( String ingredientName);

    void getAllCategories();
    void getAllMeals();
    void getAllIngredients();
    void getAllCountries();

    void getFilteredCategories(String searchCategories);
    void getFilteredCountries(String searchCountries);
    void getFilteredIngredients(String searchIngredients);
    void getFilteredMeals(String searchMeal);


}
