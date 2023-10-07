package inc.moe.foody.add_to_your_plan_feature.presenter;

import inc.moe.foody.add_to_your_plan_feature.view.DatedMealFragment;
import inc.moe.foody.add_to_your_plan_feature.view.IDatedMealView;
import inc.moe.foody.full_details_feature.presenter.IDetailedMealPresenter;
import inc.moe.foody.model.IRepo;
import inc.moe.foody.model.Meal;
import inc.moe.foody.network.FullDetailedNetworkCallback;

public class DatedMealPresenter implements IDetailedMealPresenter , FullDetailedNetworkCallback {
    private IRepo iRepo ;
    IDatedMealView iDatedMealView;
    public DatedMealPresenter(IRepo iRepo , IDatedMealView iDatedMealView){
        this.iRepo =iRepo ;
        this.iDatedMealView =iDatedMealView;
    }
    @Override
    public void getFullDetailedMeal(String idMeal) {
        iRepo.makeNetworkCallForGetFullDetailedMeal(this , idMeal);
    }

    @Override
    public void insertMealToFav() {

    }

    @Override
    public void addToPlans() {

    }

    @Override
    public void onSuccessFullDetailedMeal(Meal meal) {
        iDatedMealView.onFullDetailedMealFetch(meal);
    }

    @Override
    public void onFailedFullDetialedMeal(String errorMessage) {
        iDatedMealView.onFullDetailedMealFailed(errorMessage);
    }

    @Override
    public void onSuccessAddFavFb(String addedMessage) {

    }

    @Override
    public void onFailureAddFavFB(String errorMessage) {

    }
}
