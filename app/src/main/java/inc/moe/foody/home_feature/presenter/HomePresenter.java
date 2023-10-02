package inc.moe.foody.home_feature.presenter;

import java.util.List;

import inc.moe.foody.home_feature.view.IHome;
import inc.moe.foody.model.IRepo;
import inc.moe.foody.model.Meal;
import inc.moe.foody.network.HomeNetworkCallback;
import inc.moe.foody.utils.Cache;

public class HomePresenter implements IHomePresenter, HomeNetworkCallback {

    IHome iHome;
    private IRepo iRepo;

    public HomePresenter(IHome iHome, IRepo iRepo) {
        this.iHome = iHome;
        this.iRepo = iRepo;
    }

    @Override
    public void getAllCategories() {
        if(Cache.getInstance().getCategories() == null )
            iRepo.makeNetworkCallForCategories(this);
        else
            iHome.onCategoryFetch(Cache.getInstance().getCategories());
    }

    @Override
    public void getRandomMeal() {
        if(Cache.getInstance().getRandomMeals() == null )
            iRepo.makeNetworkCallForRandomMeal(this);
        else
            iHome.onRandomMealFetch(Cache.getInstance().getRandomMeals());
    }

    @Override
    public void addRandomMealToFav(Meal meal) {
        iRepo.insertMealToFav(meal);
    }


    @Override
    public void onSuccessCategories(List categories) {
        iHome.onCategoryFetch(categories);
        Cache.getInstance().setCategories(categories);


    }

    @Override
    public void onFailedCategories(String errorMessage) {
        iHome.onCategoryFailed(errorMessage);
    }

    @Override
    public void onSuccessRandomMeal(List<Meal> meals) {
        iHome.onRandomMealFetch(meals);
        Cache.getInstance().setRandomMeals(meals);

    }

    @Override
    public void onFailedRandomMeal(String errorMessage) {
        iHome.onRandomMealFailed(errorMessage);
    }
}
