package inc.moe.foody.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import inc.moe.foody.model.ListOfCategories;
import inc.moe.foody.model.ListOfIngredients;
import inc.moe.foody.model.ListOfMeals;

import inc.moe.foody.model.Meal;
import inc.moe.foody.utils.MealToMapConverter;
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

    @Override
    public void makeNetworkCallForCategories(SearchNetworkCallback searchNetworkCallback) {
        Call<ListOfCategories> call = mealService.getCategories();
        call.enqueue(new Callback<ListOfCategories>() {
            @Override
            public void onResponse(Call<ListOfCategories> call, Response<ListOfCategories> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onSuccess: " + response.raw() + "meals: " + response.body().getCategories());
                    searchNetworkCallback.onSuccessCategories(response.body().getCategories());
                }
            }

            @Override
            public void onFailure(Call<ListOfCategories> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                searchNetworkCallback.onFailureCategories(t.getMessage());

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
    public void makeNetworkCallForSearchByCountryName(SearchNetworkCallback searchNetworkCallback, String countryName) {
        Call<ListOfMeals> call = mealService.getMealsByCountry(countryName);
        call.enqueue(new Callback<ListOfMeals>() {
            @Override
            public void onResponse(Call<ListOfMeals> call, Response<ListOfMeals> response) {
                if(response.isSuccessful()){
                    searchNetworkCallback.onSearchByCountryNameFromHomeSuccess(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<ListOfMeals> call, Throwable t) {
                searchNetworkCallback.onSearchByCountryNameFromHomeFailure(t.getMessage());
            }
        });
    }

    @Override
    public void makeNetworkCallForSearchByIngredientName(SearchNetworkCallback searchNetworkCallback, String ingredientName) {
        Call<ListOfMeals> call = mealService.getMealsByIngredient(ingredientName);
        call.enqueue(new Callback<ListOfMeals>() {
            @Override
            public void onResponse(Call<ListOfMeals> call, Response<ListOfMeals> response) {
                if(response.isSuccessful()){
                    searchNetworkCallback.onSearchByIngredientNameSuccess(response.body().getMeals());

                }
            }

            @Override
            public void onFailure(Call<ListOfMeals> call, Throwable t) {
                searchNetworkCallback.onSearchByIngredientNameFailure(t.getMessage());
            }
        });
    }

    @Override
    public void makeNetworkCallForGetFullDetailedMeal(FullDetailedNetworkCallback fullDetailedNetworkCallback, String idMeal) {
        Call<ListOfMeals> call = mealService.getFullDetailedMeal(idMeal);
        call.enqueue(new Callback<ListOfMeals>() {
            @Override
            public void onResponse(Call<ListOfMeals> call, Response<ListOfMeals> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "onResponse: success");
                    fullDetailedNetworkCallback.onSuccessFullDetailedMeal(response.body().getMeals().get(0));
                }
            }

            @Override
            public void onFailure(Call<ListOfMeals> call, Throwable t) {
                    fullDetailedNetworkCallback.onFailedFullDetialedMeal(t.getMessage());
            }
        });
    }

    @Override
    public void makeNetworkCallForSearchByFirstLetter(HomeNetworkCallback homeNetworkCallback, String letter) {
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
    public void makeNetworkCallForSearchByFirstLetter(SearchNetworkCallback searchNetworkCallback, String letter) {
        Call<ListOfMeals> call = mealService.getAllMealsByLetter(letter);
        call.enqueue(new Callback<ListOfMeals>() {
            @Override
            public void onResponse(Call<ListOfMeals> call, Response<ListOfMeals> response) {
                if(response.isSuccessful()){
                    searchNetworkCallback.onSearchByLetterForMealsSuccess(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<ListOfMeals> call, Throwable t) {
                searchNetworkCallback.onSearchByLetterForMealsFailure(t.getMessage());
            }
        });
    }

    @Override
    public void onGettingFavFromFB(FavCallBack favCallBack) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String userID = currentUser.getUid();
            CollectionReference collectionReference = db.collection("My Favourite Meals "+ userID);
            Query query = collectionReference.whereEqualTo("userID" , userID);

            ArrayList<Meal> mealArrayList =new ArrayList<>();
            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot querySnapshot) {
                    Log.i("TAG", "onSuccess: retrieving data");
                    List<DocumentSnapshot> documents = querySnapshot.getDocuments();

                    for (DocumentSnapshot documentSnapshot : documents) {

                        Meal meal = MealToMapConverter.convertToMeal(documentSnapshot);
                        mealArrayList.add(meal);
                        Log.i("TAG", "onSuccess: " + meal.getStrMeal());
                    }
                    favCallBack.onSuccessGetFavFb(mealArrayList);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Handle the error.
                    favCallBack.onFailureGetFavFB(e.getMessage());
                }
            });

    }

    @Override
    public void onAddingFavToFB(HomeNetworkCallback homeNetworkCallback, Meal meal) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = currentUser.getUid();

        Map<String , Object> myMealMapped  = MealToMapConverter.convertToMap(meal ,userID);
        db.collection("My Favourite Meals "+ userID).document(meal.getStrMeal()).set(myMealMapped).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i("TAG", "Firebase Store onSuccess: ");
                homeNetworkCallback.onSuccessAddFavFb(meal.getStrMeal() + " saved in cloud");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("TAG", "Firebase Store onFailure: ");

                homeNetworkCallback.onFailureAddFavFB(meal.getStrMeal() + " didn't save in cloud");
            }
        });

    }

    @Override
    public void onAddingFavToFB(FullDetailedNetworkCallback fullDetailedNetworkCallback, Meal meal) {
        if(FirebaseAuth.getInstance().getCurrentUser()==null) {

            fullDetailedNetworkCallback.onFailureAddFavFB("You Have to Login First!");
        }else{

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String userID = currentUser.getUid();

            Map<String, Object> myMealMapped = MealToMapConverter.convertToMap(meal, userID);
            db.collection("My Favourite Meals " + userID).document(meal.getStrMeal()).set(myMealMapped).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.i("TAG", "Firebase Store onSuccess: ");
                    fullDetailedNetworkCallback.onSuccessAddFavFb(meal.getStrMeal() + " saved in cloud");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("TAG", "Firebase Store onFailure: ");

                    fullDetailedNetworkCallback.onFailureAddFavFB(meal.getStrMeal() + " didn't save in cloud");
                }
            });

        }

    }

    @Override
    public void onRemoveFavFromFB(FavCallBack favCallBack, Meal meal) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = currentUser.getUid();

        Map<String , Object> myMealMapped  = MealToMapConverter.convertToMap(meal ,userID);
        db.collection("My Favourite Meals "+ userID).document(meal.getStrMeal()).delete();
        favCallBack.onRemoveMealFBSuccess(meal.getStrMeal() + "Deleted From Cloud");

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

    @Override
    public void makeNetworkCallForAllCountries(SearchNetworkCallback searchNetworkCallback) {
        Call<ListOfMeals> call = mealService.getListOfCountries();
        call.enqueue(new Callback<ListOfMeals>() {
            @Override
            public void onResponse(Call<ListOfMeals> call, Response<ListOfMeals> response) {
                if(response.isSuccessful() && response.body() !=null){
                    Log.i(TAG, "onResponse countries: " + response.body().getMeals());
                    searchNetworkCallback.onSuccessAllCountries(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<ListOfMeals> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
                searchNetworkCallback.onFailureAllCountries(t.getMessage());
            }
        });
    }

    @Override
    public void makeNetworkCallForAllIngredients(SearchNetworkCallback searchNetworkCallback) {
        Call<ListOfIngredients> call = mealService.getListOfIngredients();
        call.enqueue(new Callback<ListOfIngredients>() {
            @Override
            public void onResponse(Call<ListOfIngredients> call, Response<ListOfIngredients> response) {
                if(response.isSuccessful() && response.body() !=null){
                    Log.i(TAG, "onResponse ingredients: " + response.body());
                    searchNetworkCallback.onGettingAllIngredientsSuccess(response.body().getIngredients());
                }
            }

            @Override
            public void onFailure(Call<ListOfIngredients> call, Throwable t) {
                Log.i(TAG, "onFailure: ingredients "+t.getMessage());
                searchNetworkCallback.onGettingAllIngredientsFailure(t.getMessage());
            }
        });
    }
}