package inc.moe.foody.db;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import inc.moe.foody.model.Meal;
import inc.moe.foody.model.PlannedMeal;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

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
    public Completable addMeal(Meal meal ) {
        return mealDao.insertMealToFav(meal);
    }

    @Override
    public void removeMeal(Meal meal) {
        new Thread(()->mealDao.deleteMealFromFav(meal)).start();

    }

    @Override
    public Flowable<List<Meal>> getFavMealsLiveData() {
        return mealDao.getAllFavMeals();
    }

    @Override
    public void addPlannedMeal(PlannedMeal plannedMeal) {
        new Thread(()-> mealDao.insertPlannedMeal(plannedMeal)).start();
        Log.i("TAG", "insertPlannedMeal: added success ");
    }

    @Override
    public void removePlannedMeal(PlannedMeal plannedMeal) {
       new Thread(()-> mealDao.deleteMealFromFav(plannedMeal)).start();
    }

    @Override
    public LiveData<List<PlannedMeal>> getAllPlannedMeal() {
        return mealDao.getAllPlannedMeal();
    }

}
