package inc.moe.foody.db;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import inc.moe.foody.model.Meal;

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
