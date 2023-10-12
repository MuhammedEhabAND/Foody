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

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import inc.moe.foody.model.ListOfCategories;
import inc.moe.foody.model.ListOfIngredients;
import inc.moe.foody.model.ListOfMeals;

import inc.moe.foody.model.Meal;
import inc.moe.foody.model.PlannedMeal;
import inc.moe.foody.model.PlansNetworkCallback;
import inc.moe.foody.utils.MealToMapConverter;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
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
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
    public Single<ListOfCategories> makeNetworkCallForCategories() {
        return mealService.getCategories();

    }

    @Override
    public Single<ListOfMeals> getRandomMeal() {
        return mealService.getRandomMeal();
    }

    @Override
    public Single<ListOfMeals> makeNetworkCallForSearchByCategoryName(String query) {
        return mealService.getMealByCategorySearch(query);
    }


//
//    public void makeMultipleRandomMealRequests(int numberOfRequests, HomeNetworkCallback homeNetworkCallback) {
//        List<Observable<ListOfMeals>> observables = new ArrayList<>();
//
//        for (int i = 0; i < numberOfRequests; i++) {
//            Observable<ListOfMeals> observable = Observable.fromCallable(() -> {
//                Response<ListOfMeals> response = mealService.getRandomMeal().execute();
//                if (response.isSuccessful()) {
//                    return response.body();
//                } else {
//                    throw new IOException("Request failed: " + response.message());
//                }
//            }).subscribeOn(Schedulers.io());
//
//            observables.add(observable);
//        }
//
//        Observable.merge(observables)
//                .observeOn(AndroidSchedulers.mainThread())
//                .toList()
//                .subscribe(
//                        listOfListOfMeals -> {
//                            List<Meal> allMeals = new ArrayList<>();
//                            for (ListOfMeals listOfMeals : listOfListOfMeals) {
//                                allMeals.addAll(listOfMeals.getMeals());
//                            }
//
//                            homeNetworkCallback.onSuccessRandomMeal(allMeals);
//                        },
//                        throwable -> {
//                            homeNetworkCallback.onFailedRandomMeal(throwable.getMessage());
//                        }
//                );
//    }


    @Override
    public Single<ListOfMeals> makeNetworkCallForSearchByCountryName(String query) {
        return mealService.getMealsByCountry(query);
    }

    @Override
    public Single<ListOfMeals> makeNetworkCallForSearchByIngredientName(String ingredientName) {
        return mealService.getMealsByIngredient(ingredientName);
    }

    @Override
    public Single<ListOfMeals> makeNetworkCallForGetFullDetailedMeal(String idMeal) {
        return mealService.getFullDetailedMeal(idMeal);
    }

    @Override
    public Single<ListOfMeals> makeNetworkCallForAllCountries() {
        return mealService.getListOfCountries();
    }

    @Override
    public Single<ListOfIngredients> makeNetworkCallForAllIngredients() {
        return mealService.getListOfIngredients();
    }

    @Override
    public Single<ListOfMeals> makeNetworkCallForSearchByFirstLetter(String letter) {
        return mealService.getAllMealsByLetter(letter);
    }

    @Override
    public void onGettingFavFromFB(FavCallBack favCallBack) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = currentUser.getUid();
        CollectionReference collectionReference = db.collection("My Favourite Meals " + userID);
        Query query = collectionReference.whereEqualTo("userID", userID);

        ArrayList<Meal> mealArrayList = new ArrayList<>();
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
    public void onAddingFavToFB(FullDetailedNetworkCallback fullDetailedNetworkCallback , Meal meal) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = currentUser.getUid();

        Map<String, Object> myMealMapped = MealToMapConverter.convertToMap(meal, userID);
        db.collection("My Favourite Meals " + userID).document(meal.getStrMeal()).set(myMealMapped).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i("TAG", "Firebase Store onSuccess: ");
                fullDetailedNetworkCallback.onSuccessAddFavFb("Saved in Cloud");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("TAG", "Firebase Store onFailure: ");
                fullDetailedNetworkCallback.onFailureAddFavFB(e.getMessage());
            }
        });

    }



    @Override
    public void onRemoveFavFromFB(FavCallBack favCallBack, Meal meal) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = currentUser.getUid();

        Map<String, Object> myMealMapped = MealToMapConverter.convertToMap(meal, userID);
        db.collection("My Favourite Meals " + userID).document(meal.getStrMeal()).delete();
        favCallBack.onRemoveMealFBSuccess(meal.getStrMeal() + "Deleted From Cloud");

    }

    @Override
    public void onAddingPlansToFB(DatedMealNetworkCallback datedMealNetworkCallback, PlannedMeal meal) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = currentUser.getUid();

        Map<String, Object> myMealMapped = meal.toMap();
        db.collection("My Planned Meals " + userID).document(meal.getIdMeal()).set(myMealMapped).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i("TAG", "Firebase Store onSuccess: ");
                datedMealNetworkCallback.onSuccessAddPlanFb(" saved in cloud");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("TAG", "Firebase Store onFailure: ");

                datedMealNetworkCallback.onFailureAddPlanFB(" didn't save in cloud");
            }
        });

    }

    @Override
    public void onRemovePlansToFB(DatedMealNetworkCallback datedMealNetworkCallback, PlannedMeal meal) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = currentUser.getUid();

        Map<String, Object> myMealMapped = meal.toMap();
        db.collection("My Planned Meals " + userID).document(meal.getIdMeal()).delete();
        datedMealNetworkCallback.onSuccessRemovePlanFb("Deleted");
    }

    @Override
    public void onGettingPlansFromFB(PlansNetworkCallback plansNetworkCallback) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = currentUser.getUid();
        CollectionReference collectionReference = db.collection("My Planned Meals " + userID);
        Query query = collectionReference.whereEqualTo("userID", userID);
        ArrayList<PlannedMeal> plannedMeals = new ArrayList<>();
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                Log.i("TAG", "onSuccess: retrieving data");
                List<DocumentSnapshot> documents = querySnapshot.getDocuments();

                for (DocumentSnapshot documentSnapshot : documents) {
                    String idMeal = documentSnapshot.getString("idMeal");
                    String dayOfMonth = documentSnapshot.getString("dayOfMonth");
                    String month = documentSnapshot.getString("month");
                    String year = documentSnapshot.getString("year");
                    String type = documentSnapshot.getString("type");
                    String userId = documentSnapshot.getString("userId");
                    PlannedMeal plannedMeal = new PlannedMeal(idMeal, dayOfMonth, month, year);
                    plannedMeal.setType(type);
                    plannedMeal.setUserId(userId);
                    plannedMeals.add(plannedMeal);

                }

                plansNetworkCallback.onGettingPlansSuccess(plannedMeals);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle the error.
                plansNetworkCallback.onGettingPlansFailure(e.getMessage());
            }
        });

    }
}
