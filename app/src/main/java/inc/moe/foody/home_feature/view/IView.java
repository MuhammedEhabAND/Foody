package inc.moe.foody.home_feature.view;

import java.util.List;

import inc.moe.foody.model.Category;

public interface IView {

    void onDataFetch(List<Category> categories);
    void onDataFetchFailed(String errorMessage);
}
