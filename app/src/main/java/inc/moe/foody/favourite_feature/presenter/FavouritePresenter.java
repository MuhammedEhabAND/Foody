package inc.moe.foody.favourite_feature.presenter;

import androidx.lifecycle.LiveData;

import java.util.List;

import inc.moe.foody.favourite_feature.view.IFav;
import inc.moe.foody.model.IRepo;
import inc.moe.foody.model.Meal;
import inc.moe.foody.network.FavCallBack;
import inc.moe.foody.utils.Cache;

public class FavouritePresenter implements IFavouritePresenter , FavCallBack {

    private IRepo repo;
    private IFav iFav;
    public FavouritePresenter( IRepo repo , IFav iFav) {

        this.repo = repo;
        this.iFav = iFav;
    }

    @Override
    public LiveData<List<Meal>> getFavMeals() {
        return repo.getFavMeals();
    }

    @Override
    public void removeFromDataBase(Meal meal) {

        repo.removeFavMealFromFB(this , meal);

        repo.removeMealFromFav(meal);

     //   repo.getFavMealsFB(this);
    }

    @Override
    public void getMealsFromFirebase() {

//        if(Cache.getInstance().getFavMeals()!=null){/
//            iFav.onGettingFavDataFromDBSuccess(Cache.getInstance().getFavMeals());
//        }else{
            repo.getFavMealsFB(this);

//        }
    }


    @Override
    public void onSuccessGetFavFb(List<Meal> meals) {
        iFav.onGettingFavDataFromDBSuccess(meals);
        Cache.getInstance().setFavMeals(meals);
    }

    @Override
    public void onFailureGetFavFB(String errorMessage) {
        iFav.onGettingFavDataFromDBFailure(errorMessage);
    }

    @Override
    public void onRemoveMealFBSuccess(String message) {
        iFav.onRemoveFromCloud(message);

    }
}
