package inc.moe.foody.home_feature.presenter;

import java.util.List;

import inc.moe.foody.home_feature.view.IView;
import inc.moe.foody.model.IRepo;
import inc.moe.foody.model.Meal;
import inc.moe.foody.network.MyNetworkCallBack;
import inc.moe.foody.utils.Cache;

public class HomePresenter implements IHomePresenter, MyNetworkCallBack {

    IView iView ;
    private IRepo iRepo;

    public HomePresenter(IView iView, IRepo iRepo) {
        this.iView = iView;
        this.iRepo = iRepo;
    }

    @Override
    public void getAllCategories() {
        if(Cache.getInstance().getCategories() == null )
            iRepo.makeNetworkCallForCategories(this);
        else
            iView.onCategoryFetch(Cache.getInstance().getCategories());
    }

    @Override
    public void getRandomMeal() {
        if(Cache.getInstance().getRandomMeals() == null )
            iRepo.makeNetworkCallForRandomMeal(this);
        else
            iView.onRandomMealFetch(Cache.getInstance().getRandomMeals());
    }

    @Override
    public void addRandomMealToFav(Meal meal) {
        iRepo.insertMealToFav(meal);
    }

    @Override
    public void searchByCategoryName(String categoryName) {
        iRepo.makeNetworkCallForSearchByCategoryName(this , categoryName);

    }

    @Override
    public void onSuccessCategories(List categories) {
        iView.onCategoryFetch(categories);
        Cache.getInstance().setCategories(categories);


    }

    @Override
    public void onFailedCategories(String errorMessage) {
        iView.onCategoryFailed(errorMessage);
    }

    @Override
    public void onSuccessRandomMeal(List<Meal> meals) {
        iView.onRandomMealFetch(meals);
        Cache.getInstance().setRandomMeals(meals);

    }

    @Override
    public void onFailedRandomMeal(String errorMessage) {
        iView.onRandomMealFailed(errorMessage);
    }
}
