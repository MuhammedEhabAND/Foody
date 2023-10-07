package inc.moe.foody.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import inc.moe.foody.model.Meal;
import inc.moe.foody.utils.MealToMapConverter;

public class ConcreteLocalSource implements LocalSource{

    private Context context;
    private static ConcreteLocalSource instance = null;
    private AppDatabase appDatabase;
    private MealDao mealDao;
    private ConcreteLocalSource(Context context) {
        this.context = context;
        Log.i("Database", "ConcreteLocalSource: created ");

        appDatabase = AppDatabase.getInstance(context);
        mealDao = appDatabase.getMealDao();
    }

    public static ConcreteLocalSource getInstance(Context context) {
        if (instance == null) return new ConcreteLocalSource(context);
        else return instance;
    }

    @Override
    public void addMeal(Meal meal) {
        new Thread(()->mealDao.insertMealToFav(meal)).start();
    }

    @Override
    public void removeMeal(Meal meal) {
        new Thread(()->mealDao.deleteMealFromFav(meal)).start();

    }

    @Override
    public LiveData<List<Meal>> getFavMealsLiveData() {

        return mealDao.getAllFavMeals();
    }

}
