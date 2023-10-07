package inc.moe.foody.home_feature.view;

import java.util.List;

import inc.moe.foody.model.Category;
import inc.moe.foody.model.Country;
import inc.moe.foody.model.Meal;

public interface IHome {

    void onCategoryFetch(List<Category> categories);
    void onRandomMealFetch(List<Meal> meals);
    void onCategoryFailed(String errorMessage);
    void onRandomMealFailed(String errorMessage);
    void onAllMealsFetch(List<Meal>meals);
    void onAllMealsFailed(String errorMessage);
    void onAllCountriesFetch(List<Meal> countries);
    void onAllCountriesFailed(String errorMessage);
    void onAddedToFavFBSuccess(String addedMessage);
    void onAddedToFavFBFailure(String errorMessage);
}
