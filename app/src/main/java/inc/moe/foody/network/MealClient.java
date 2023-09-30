package inc.moe.foody.network;

import android.util.Log;
import android.util.LruCache;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import inc.moe.foody.model.Category;
import inc.moe.foody.model.ListOfCategories;
import inc.moe.foody.model.ListOfMeals;

import inc.moe.foody.model.Meal;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
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
    public void makeNetworkCallForCategories(MyNetworkCallBack myNetworkCallBack) {

        Call<ListOfCategories> call = mealService.getCategories();
        call.enqueue(new Callback<ListOfCategories>() {
            @Override
            public void onResponse(Call<ListOfCategories> call, Response<ListOfCategories> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onSuccess: " + response.raw() + "meals: " + response.body().getCategories());
                    myNetworkCallBack.onSuccessCategories(response.body().getCategories());
                }
            }

            @Override
            public void onFailure(Call<ListOfCategories> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                myNetworkCallBack.onFailedCategories(t.getMessage());

            }


        });


    }

    public void makeMultipleRandomMealRequests(int numberOfRequests, MyNetworkCallBack myNetworkCallBack) {
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
                            myNetworkCallBack.onSuccessRandomMeal(allMeals);
                        },
                        throwable -> {
                            // Handle the error
                            myNetworkCallBack.onFailedRandomMeal(throwable.getMessage());
                        }
                );
    }
}