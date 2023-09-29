package inc.moe.foody.home_feature.view;

import java.util.List;

import inc.moe.foody.model.Category;
import inc.moe.foody.model.Meal;

public interface IView {

    void onCategoryFetch(List<Category> categories);
    void onRandomMealFetch(List<Meal> meals);
    void onDataFetchFailed(String errorMessage);
}
