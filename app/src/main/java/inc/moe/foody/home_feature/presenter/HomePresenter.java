package inc.moe.foody.home_feature.presenter;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

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

        iRepo.addFavMealToFB(this , meal);
        Cache.getInstance().setFavMeals(null);
        meal.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        iRepo.insertMealToFav(meal);
    }

    @Override
    public void getAllMeals() {
        if(Cache.getInstance().getAllMeals() == null)
            iRepo.makeNetworkCallForGettingAllMealsWithLetter(this ,"b");
        else
            iHome.onAllMealsFetch(Cache.getInstance().getAllMeals());

    }

    @Override
    public void getAllCountries() {
        if(Cache.getInstance().getCountries() == null)
            iRepo.makeNetworkCallForGettingAllCountries(this);
        else
            iHome.onAllCountriesFetch(Cache.getInstance().getCountries());

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
        Cache.
                getInstance().setRandomMeals(meals);

    }

    @Override
    public void onFailedRandomMeal(String errorMessage) {
        iHome.onRandomMealFailed(errorMessage);
    }

    @Override
    public void onSuccessAllMealsWithBLetter(List<Meal> meals) {
        iHome.onAllMealsFetch(meals);
        Cache.getInstance().setAllMeals(meals);
    }

    @Override
    public void onFailedAllMealsWithBLetter(String errorMessage) {
        iHome.onAllMealsFailed(errorMessage);
    }

    @Override
    public void onSuccessAllCountries(List<Meal> countries) {
        Log.i("TAG", "onSuccessAllCountries: " + countries.get(0).getStrArea());
        iHome.onAllCountriesFetch(countries);
        Cache.getInstance().setCountries(countries);
        Log.i("TAG", "onSuccessAllCountries: " + Cache.getInstance().getCountries().get(0).getStrArea());
    }

    @Override
    public void onFailedAllCountries(String errorMessage) {
        iHome.onAllCountriesFailed(errorMessage);
    }

    @Override
    public void onSuccessAddFavFb(String addedMessage) {
     iHome.onAddedToFavFBSuccess(addedMessage);
    }

    @Override
    public void onFailureAddFavFB(String errorMessage) {
        iHome.onAddedToFavFBFailure(errorMessage);

    }

}
