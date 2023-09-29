package inc.moe.foody.network;

import android.util.Log;

import inc.moe.foody.model.ListOfCategories;
import inc.moe.foody.model.ListOfMeals;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealClient implements RemoteSource  {
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
            return new MealClient();
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
                public void onFailure (Call < ListOfCategories > call, Throwable t){
                    Log.i(TAG, "onFailure: " + t.getMessage());
                    myNetworkCallBack.onFailedCategories(t.getMessage());

                }


        });


    }

    @Override
    public void makeNetworkCallForRandomMeal(MyNetworkCallBack myNetworkCallBack) {
        Call<ListOfMeals> call = mealService.getRandomMeal();
        call.enqueue(new Callback<ListOfMeals>() {
            @Override
            public void onResponse(Call<ListOfMeals> call, Response<ListOfMeals> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onSuccess: " + response.raw() + "meals: " + response.body().getMeals());
                    myNetworkCallBack.onSuccessRandomMeal(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<ListOfMeals> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                myNetworkCallBack.onFailedRandomMeal(t.getMessage());

            }
        });
    }
}
