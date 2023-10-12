package inc.moe.foody.full_details_feature.presenter;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import inc.moe.foody.full_details_feature.view.IDetailedMeal;
import inc.moe.foody.model.IRepo;
import inc.moe.foody.model.Meal;
import inc.moe.foody.network.FullDetailedNetworkCallback;
import inc.moe.foody.utils.Cache;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailedMealPresenter implements IDetailedMealPresenter , FullDetailedNetworkCallback {
    IDetailedMeal iDetailedMeal;
    private IRepo iRepo;

    public DetailedMealPresenter(IDetailedMeal iDetailedMeal, IRepo iRepo) {
        this.iDetailedMeal = iDetailedMeal;
        this.iRepo = iRepo;
    }


    @Override
    public void getFullDetailedMeal(String idMeal) {
        if(Cache.getInstance().getCurrentMeal()==null){
        iRepo.makeNetworkCallForGetFullDetailedMeal( idMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item->{
                            iDetailedMeal.onFullDetailedMealFetch(item.getMeals().get(0));
                            Cache.getInstance().setCurrentMeal(item.getMeals().get(0));
                            },
                        onError->{iDetailedMeal.onFullDetailedMealFailed(onError.getMessage());}
                );
        }else {
            iDetailedMeal.onFullDetailedMealFetch(Cache.instance.getCurrentMeal());
        }
    }

    @Override
    public void insertMealToFav() {

        Meal meal = Cache.getInstance().getCurrentMeal();
        meal.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        iRepo.insertMealToFav(meal).subscribe();
        iRepo.addFavMealToFB(this ,Cache.getInstance().getCurrentMeal());
    }

    @Override
    public void addToPlans() {
        if(Cache.getInstance().getCurrentMeal()!=null){
            Log.i("TAG", "addToPlans: "+Cache.getInstance().getCurrentMeal().getIdMeal());
            iDetailedMeal.navigateToCalendarSuccess(Cache.getInstance().getCurrentMeal().getIdMeal());
        }else{
            iDetailedMeal.navigateToCalendarFailure("Meal not Found");
        }
    }

    @Override
    public void onSuccessAddFavFb(String addedMessage) {
        iDetailedMeal.onAddedToFavFBSuccess(addedMessage);
    }

    @Override
    public void onFailureAddFavFB(String errorMessage) {
        iDetailedMeal.onAddedToFavFBFailure(errorMessage);
    }
}
