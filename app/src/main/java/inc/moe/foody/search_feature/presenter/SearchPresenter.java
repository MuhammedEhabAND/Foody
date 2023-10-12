package inc.moe.foody.search_feature.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import inc.moe.foody.model.Category;
import inc.moe.foody.model.IRepo;
import inc.moe.foody.model.Ingredient;
import inc.moe.foody.model.ListOfCategories;
import inc.moe.foody.model.ListOfIngredients;
import inc.moe.foody.model.ListOfMeals;
import inc.moe.foody.model.Meal;
import inc.moe.foody.network.SearchNetworkCallback;
import inc.moe.foody.search_feature.view.ISearch;
import inc.moe.foody.utils.Cache;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenter implements ISearchPresenter  {

    ISearch iSearch;
    private IRepo iRepo;
    char currentChar =' ';
    public SearchPresenter(ISearch iSearch, IRepo iRepo) {
        this.iSearch = iSearch;
        this.iRepo = iRepo;
    }

    @Override
    public void getMealsByCategoryOf(String categoryName) {
        Log.i("TAG", "getMealsByCategoryOf: search presenter");
        iRepo.makeNetworkCallForSearchByCategoryName( categoryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item->{iSearch.searchByCategoryGotFromHome(item.getMeals());},
                        onError->{iSearch.searchByCategoryGotFromHomeFailed(onError.getMessage());}
                );

    }

    @Override
    public void getMealsByCountryOf(String query) {
         iRepo.makeNetworkCallForSearchByCountryName(query)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(
                         item->{iSearch.searchByCountryGotFromHome(item.getMeals());},
                         onError->{iSearch.searchByCategoryGotFromHomeFailed(onError.getMessage());}
                 );
    }

    @Override
    public void getMealsByIngredientOf(String ingredientName) {
         iRepo.makeNetworkCallForSearchByIngredientName( ingredientName)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(
                         item->{iSearch.searchByIngredientSuccess(item.getMeals());},
                         onError->{iSearch.searchByIngredientFailure(onError.getMessage());}
                 );
    }

    @Override
    public void getAllCategories() {
        if(Cache.getInstance().getCategories() != null){
            iSearch.allCategoriesFetched(Cache.instance.getCategories());
        }else{
        iRepo.makeNetworkCallForCategories()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            item->{
                                iSearch.allCategoriesFetched(item.getCategories());
                                Cache.getInstance().setCategories(item.getCategories());},
                            onError->{iSearch.allCategoriesFailed(onError.getMessage());}
                    );
        }
    }

    @Override
    public void getAllMeals() {
        if(Cache.getInstance().getAllMeals() != null) {
            iSearch.allMealsFetched(Cache.instance.getAllMeals());
        }else{
        iRepo.makeNetworkCallForGettingAllMealsWithLetter( "b")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            item->{
                                iSearch.allMealsFetched(item.getMeals());
                                Cache.getInstance().setAllMeals(item.getMeals());
                            },
                            onError->{iSearch.allMealsFailed(onError.getMessage());}
                    );

        }
    }

    @Override
    public void getAllIngredients() {
      if(Cache.getInstance().getAllIngredients() == null){
        iRepo.makeNetworkCallForGetAllIngredients()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            item->{
                                iSearch.allIngredientsFetched(item.getIngredients());
                                Cache.getInstance().setAllIngredients(item.getIngredients());
                            },
                            onError->{
                                iSearch.allIngredientsFailed(onError.getMessage());
                            }
                    );
      }else{
            iSearch.allIngredientsFetched(Cache.getInstance().getAllIngredients());
        }
    }

    @Override
    public void getAllCountries() {
        if(Cache.getInstance().getCountries() != null) {
            iSearch.allCountriesFetched(Cache.instance.getCountries());
        }else {
            iRepo.makeNetworkCallForGettingAllCountries()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            item -> {
                                iSearch.allCountriesFetched(item.getMeals());
                                Cache.getInstance().setCountries(item.getMeals());
                            },
                            onError -> {
                                iSearch.allCountriesFailed(onError.getMessage());
                            }
                    );

        }
    }

    @Override
    public void getFilteredCategories(String searchCategories) {
        List<Category> categories = Cache.getInstance().getCategories().stream().filter(category ->
                    category.getStrCategory().toLowerCase().contains(searchCategories.toLowerCase())).collect(Collectors.toCollection(ArrayList::new));
        iSearch.showFilteredCategories(categories);

    }

    @Override
    public void getFilteredCountries(String searchCountries) {
        List<Meal> countries = Cache.getInstance().getCountries().stream().filter(country ->
                country.getStrArea().toLowerCase().contains(searchCountries.toLowerCase())).collect(Collectors.toCollection(ArrayList::new));
        iSearch.showFilteredCountries(countries);


    }

    @Override
    public void getFilteredIngredients(String searchIngredients) {
        List<Ingredient> ingredients = Cache.getInstance().getAllIngredients().stream().filter(ingredient ->
                ingredient.getStrIngredient().toLowerCase().contains(searchIngredients.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new ));
        iSearch.showFilteredIngredients(ingredients);

    }

    @Override
    public void getFilteredMeals(String searchMeal) {
//        if (currentChar != ' ' && searchMeal.charAt(0) == currentChar) {
//            ArrayList<Meal> filteredMeals = Cache.getInstance().getSearchedMeals().stream()
//                    .filter(meal -> meal.getStrMeal().toLowerCase().contains(searchMeal.toLowerCase()))
//                    .collect(Collectors.toCollection(ArrayList::new));
//
//            iSearch.showFilteredMeals(filteredMeals);
//        } else {
            currentChar = searchMeal.charAt(0);
            iRepo.makeNetworkCallForGettingAllMealsWithLetter( String.valueOf(currentChar))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            item->{
                                if(!String.valueOf(currentChar).isEmpty())
                                iSearch.showFilteredMeals(item.getMeals()
                                    .stream().filter(meal->
                                            meal.getStrMeal()
                                                    .toLowerCase()
                                                    .contains(searchMeal.toLowerCase()))
                                    .collect(Collectors.toCollection(ArrayList::new)));
                                else
                                 iSearch.showFilteredMeals(item.getMeals());
                            },
                            onError->{iSearch.showFilteredMealsFailure(onError.getMessage());}
                    );
//        }
    }

}
