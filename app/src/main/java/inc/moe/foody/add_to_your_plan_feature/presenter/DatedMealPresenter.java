package inc.moe.foody.add_to_your_plan_feature.presenter;

import com.google.firebase.auth.FirebaseAuth;

import inc.moe.foody.add_to_your_plan_feature.view.IDatedMealView;
import inc.moe.foody.model.IRepo;
import inc.moe.foody.model.PlannedMeal;
import inc.moe.foody.network.DatedMealNetworkCallback;
import inc.moe.foody.network.FullDetailedNetworkCallback;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DatedMealPresenter implements IDatedMealPresenter , FullDetailedNetworkCallback , DatedMealNetworkCallback {
    private IRepo iRepo ;
    IDatedMealView iDatedMealView;
    public DatedMealPresenter(IRepo iRepo , IDatedMealView iDatedMealView){
        this.iRepo =iRepo ;
        this.iDatedMealView =iDatedMealView;
    }

    @Override
    public void onSuccessAddFavFb(String addedMessage) {

    }

    @Override
    public void onFailureAddFavFB(String errorMessage) {

    }

    @Override
    public void getMealById(String idMeal) {
         iRepo.makeNetworkCallForGetFullDetailedMeal(idMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item->{
                            iDatedMealView.onFullDetailedMealFetch(item.getMeals().get(0));},
                        onError->{iDatedMealView.onFullDetailedMealFailed(onError.getMessage());}

                );
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
