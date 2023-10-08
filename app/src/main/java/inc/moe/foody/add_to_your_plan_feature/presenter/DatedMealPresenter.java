package inc.moe.foody.add_to_your_plan_feature.presenter;

import com.google.firebase.auth.FirebaseAuth;

import inc.moe.foody.add_to_your_plan_feature.view.DatedMealFragment;
import inc.moe.foody.add_to_your_plan_feature.view.IDatedMealView;
import inc.moe.foody.full_details_feature.presenter.IDetailedMealPresenter;
import inc.moe.foody.model.IRepo;
import inc.moe.foody.model.Meal;
import inc.moe.foody.model.PlannedMeal;
import inc.moe.foody.network.DatedMealNetworkCallback;
import inc.moe.foody.network.FullDetailedNetworkCallback;

public class DatedMealPresenter implements IDatedMealPresenter , FullDetailedNetworkCallback , DatedMealNetworkCallback {
    private IRepo iRepo ;
    IDatedMealView iDatedMealView;
    public DatedMealPresenter(IRepo iRepo , IDatedMealView iDatedMealView){
        this.iRepo =iRepo ;
        this.iDatedMealView =iDatedMealView;
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

    @Override
    public void getMealById(String idMeal) {
        iRepo.makeNetworkCallForGetFullDetailedMeal(this , idMeal);
    }

    @Override
    public void addPlannedMeal(PlannedMeal plannedMeal) {
        plannedMeal.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        iRepo.addPlannedMeal(plannedMeal);
        iRepo.onAddingPlansToFB(this ,plannedMeal);
        iDatedMealView.onSuccessAddPlannedMeal("added your meal to "+plannedMeal.getType());

    }

    @Override
    public void removePlannedMeal(PlannedMeal plannedMeal) {
        iRepo.removePlannedMeal(plannedMeal);
        iRepo.onRemovePlansToFB(this , plannedMeal);
        iDatedMealView.onSuccessAddPlannedMeal("deleted your meal");
    }

    @Override
    public void onSuccessAddPlanFb(String addedMessage) {

    }

    @Override
    public void onFailureAddPlanFB(String errorMessage) {

    }

    @Override
    public void onSuccessRemovePlanFb(String message) {

    }
}
