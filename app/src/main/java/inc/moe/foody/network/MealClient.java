package inc.moe.foody.network;

import android.util.Log;

import inc.moe.foody.model.ListOfCategories;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealClient implements RemoteSource  {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    public static final String TAG = "meals client";
    private static MealClient instance = null;

    private MealClient() {
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AllCategoriesService productsService = retrofit.create(AllCategoriesService.class);
        Call<ListOfCategories> call = productsService.getCategories();
        call.enqueue(new Callback<ListOfCategories>() {
            @Override
            public void onResponse(Call<ListOfCategories> call, Response<ListOfCategories> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onSuccess: " + response.raw() + "meals: " + response.body().getCategories());
                    myNetworkCallBack.onSuccess(response.body().getCategories());
                }
            }
                @Override
                public void onFailure (Call < ListOfCategories > call, Throwable t){
                    Log.i(TAG, "onFailure: " + t.getMessage());
                    myNetworkCallBack.onFailed(t.getMessage());

                }


        });
    }
}
