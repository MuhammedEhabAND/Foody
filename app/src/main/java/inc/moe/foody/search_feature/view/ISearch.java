package inc.moe.foody.search_feature.view;

import java.util.List;

import inc.moe.foody.model.Meal;

public interface ISearch {
    void searchByCategoryGotFromHome(List<Meal> meals);
}
