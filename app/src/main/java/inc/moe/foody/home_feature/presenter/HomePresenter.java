package inc.moe.foody.home_feature.presenter;

import com.google.firebase.auth.FirebaseAuth;

import inc.moe.foody.home_feature.view.IHome;
import inc.moe.foody.model.IRepo;
import inc.moe.foody.model.Meal;
import inc.moe.foody.utils.Cache;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter implements IHomePresenter {

    IHome iHome;
    private IRepo iRepo;

    public HomePresenter(IHome iHome, IRepo iRepo) {
        this.iHome = iHome;
        this.iRepo = iRepo;
    }

    @Override
    public void getAllCategories() {
        if(Cache.getInstance().getCategories() == null )
         iRepo.makeNetworkCallForCategories()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            item ->{
                                iHome.onCategoryFetch(item.getCategories());
                                Cache.getInstance().setCategories(item.getCategories());
                                },
                            onError ->{iHome.onCategoryFailed(onError.getMessage());});
        else
            iHome.onCategoryFetch(Cache.getInstance().getCategories());
    }

    @Override
    public void getRandomMeal() {
        if(Cache.getInstance().getRandomMeals() == null )
           iRepo.makeNetworkCallForRandomMeal()
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(
                           item->{
                               iHome.onRandomMealFetch(item.getMeals());
                               Cache.getInstance().setRandomMeals(item.getMeals());},
                           onError ->{iHome.onRandomMealFailed(onError.getMessage());}
                   );
        else
            iHome.onRandomMealFetch(Cache.getInstance().getRandomMeals());
    }

    @Override
    public Completable addRandomMealToFav(Meal meal) {

//        iRepo.addFavMealToFB(this , meal);
//        Cache.getInstance().setFavMeals(null);
         meal.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        return iRepo.insertMealToFav(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void getAllMeals() {
        if(Cache.getInstance().getAllMeals() == null)
        iRepo.makeNetworkCallForGettingAllMealsWithLetter("b")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item->{
                            iHome.onAllMealsFetch(item.getMeals());
                            Cache.getInstance().setAllMeals(item.getMeals());},
                        onError->{iHome.onAllMealsFailed(onError.getMessage());}
                );
        else
            iHome.onAllMealsFetch(Cache.getInstance().getAllMeals());

    }

    @Override
    public void getAllCountries() {
       if(Cache.getInstance().getCountries() == null)
           iRepo.makeNetworkCallForGettingAllCountries()
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(
                           item->{
                               iHome.onAllCountriesFetch(item.getMeals());
                               Cache.getInstance().setCountries(item.getMeals());},
                           onError->{iHome.onAllCountriesFailed(onError.getMessage());}
                   );
        else
            iHome.onAllCountriesFetch(Cache.getInstance().getCountries());

    }





//    @Override
//    public void onSuccessCategories(List categories) {
//        iHome.onCategoryFetch(categories);
//        Cache.getInstance().setCategories(categories);
//
//
//    }


//    @Override
//    public void onSuccessRandomMeal(List<Meal> meals) {
//        iHome.onRandomMealFetch(meals);
//        Cache.
//                getInstance().setRandomMeals(meals);
//
//    }

//
//    @Override
//    public void onSuccessAllMealsWithBLetter(List<Meal> meals) {
//        iHome.onAllMealsFetch(meals);
//        Cache.getInstance().setAllMeals(meals);
//    }


//    @Override
//    public void onSuccessAllCountries(List<Meal> countries) {
//        Log.i("TAG", "onSuccessAllCountries: " + countries.get(0).getStrArea());
//        iHome.onAllCountriesFetch(countries);
//        Cache.getInstance().setCountries(countries);
//        Log.i("TAG", "onSuccessAllCountries: " + Cache.getInstance().getCountries().get(0).getStrArea());
//    }
//

}
