package inc.moe.foody.network;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import inc.moe.foody.model.ListOfCategories;
import inc.moe.foody.model.ListOfCountries;
import inc.moe.foody.model.ListOfMeals;

import inc.moe.foody.model.Meal;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealClient implements RemoteSource {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    public static final String TAG = "meals client";
    private static MealClient instance = null;
    Retrofit retrofit;
    MealService mealService;
    private MealClient() {


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);
    }

    public static MealClient getInstance() {
        if (instance == null) {
            return instance = new MealClient();
        } else {
            return instance;
        }
    }

    @Override
    public void makeNetworkCallForCategories(HomeNetworkCallback homeNetworkCallback) {

        Call<ListOfCategories> call = mealService.getCategories();
        call.enqueue(new Callback<ListOfCategories>() {
            @Override
            public void onResponse(Call<ListOfCategories> call, Response<ListOfCategories> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onSuccess: " + response.raw() + "meals: " + response.body().getCategories());
                    homeNetworkCallback.onSuccessCategories(response.body().getCategories());
                }
            }

            @Override
            public void onFailure(Call<ListOfCategories> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                homeNetworkCallback.onFailedCategories(t.getMessage());

            }


        });


    }

    public void makeMultipleRandomMealRequests(int numberOfRequests, HomeNetworkCallback homeNetworkCallback) {
        List<Observable<ListOfMeals>> observables = new ArrayList<>();

        for (int i = 0; i < numberOfRequests; i++) {
            Observable<ListOfMeals> observable = Observable.fromCallable(() -> {
                Response<ListOfMeals> response = mealService.getRandomMeal().execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Request failed: " + response.message());
                }
            }).subscribeOn(Schedulers.io());

            observables.add(observable);
        }

        Observable.merge(observables)
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(
                        listOfListOfMeals -> {
                            // Combine all meals from the list of results
                            List<Meal> allMeals = new ArrayList<>();
                            for (ListOfMeals listOfMeals : listOfListOfMeals) {
                                allMeals.addAll(listOfMeals.getMeals());
                            }

                            // Handle the combined list of meals
                            homeNetworkCallback.onSuccessRandomMeal(allMeals);
                        },
                        throwable -> {
                            // Handle the error
                            homeNetworkCallback.onFailedRandomMeal(throwable.getMessage());
                        }
                );
    }

    @Override
    public void makeNetworkCallForSearchByCategoryName(SearchNetworkCallback searchNetworkCallback, String categoryName) {
        Log.i("searchCallback", "makeNetworkCallForSearchByCategoryName: searchWorking");

        Call<ListOfMeals> call = mealService.getMealByCategorySearch(categoryName);
        call.enqueue(new Callback<ListOfMeals>() {
            @Override
            public void onResponse(Call<ListOfMeals> call, Response<ListOfMeals> response) {
                if (response.isSuccessful()) {
                    Log.i("searchCallback", "onSuccess: " + response.raw() + "meals: " + response.body().getMeals());
                    searchNetworkCallback.onSearchByCategoryNameFromHomeSuccess(response.body().getMeals());
                }else{
                    Log.i("searchCallback", "onResponse: "+ "errorr");
                }
            }

            @Override
            public void onFailure(Call<ListOfMeals> call, Throwable t) {
                Log.i("searchCallback", "onFailure: " + t.getMessage());
                searchNetworkCallback.onSearchByCategoryNameFromHomeFailure(t.getMessage());

            }
        });
    }

    @Override
    public void makeNetworkCallForAllMeals(HomeNetworkCallback homeNetworkCallback, String letter) {
        Call<ListOfMeals> call = mealService.getAllMealsByLetter(letter);
        call.enqueue(new Callback<ListOfMeals>() {
            @Override
            public void onResponse(Call<ListOfMeals> call, Response<ListOfMeals> response) {
                if(response.isSuccessful()){
                    homeNetworkCallback.onSuccessAllMealsWithBLetter(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<ListOfMeals> call, Throwable t) {
                Log.i(TAG, "onFailure: " +t.getMessage());
                homeNetworkCallback.onFailedAllMealsWithBLetter(t.getMessage());
            }
        });
    }

    @Override
    public void makeNetworkCallForAllCountries(HomeNetworkCallback homeNetworkCallback) {
        Call<ListOfMeals> call = mealService.getListOfCountries();
        call.enqueue(new Callback<ListOfMeals>() {
            @Override
            public void onResponse(Call<ListOfMeals> call, Response<ListOfMeals> response) {
                if(response.isSuccessful() && response.body() !=null){
                    Log.i(TAG, "onResponse countries: " + response.body().getMeals());
                    homeNetworkCallback.onSuccessAllCountries(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<ListOfMeals> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
                homeNetworkCallback.onFailedAllCountries(t.getMessage());
            }
        });
    }
}