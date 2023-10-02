package inc.moe.foody.full_details_feature.presenter;

import inc.moe.foody.full_details_feature.view.IDetailedMeal;
import inc.moe.foody.home_feature.view.IHome;
import inc.moe.foody.model.IRepo;
import inc.moe.foody.model.Meal;
import inc.moe.foody.network.FullDetailedNetworkCallback;

public class DetailedMealPresenter implements IDetailedMealPresenter , FullDetailedNetworkCallback {
    IDetailedMeal iDetailedMeal;
    private IRepo iRepo;

    public DetailedMealPresenter(IDetailedMeal iDetailedMeal, IRepo iRepo) {
        this.iDetailedMeal = iDetailedMeal;
        this.iRepo = iRepo;
    }


    @Override
    public void getFullDetailedMeal(String idMeal) {
        iRepo.makeNetworkCallForGetFullDetailedMeal(this , idMeal);
    }

    @Override
    public void onSuccessFullDetailedMeal(Meal meal) {
        iDetailedMeal.onFullDetailedMealFetch(meal);
    }

    @Override
    public void onFailedFullDetialedMeal(String errorMessage) {
        iDetailedMeal.onFullDetailedMealFailed(errorMessage);
    }
}
