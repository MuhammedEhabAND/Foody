package inc.moe.foody.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import inc.moe.foody.model.Meal;
import inc.moe.foody.model.PlannedMeal;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealDao {
    @Query("SELECT * FROM favourite_meal")
    Flowable<List<Meal>> getAllFavMeals();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMealToFav(Meal meal );
    @Delete
    void deleteMealFromFav(Meal meal);
    @Query("SELECT * FROM planned_meal")
    LiveData<List<PlannedMeal>> getAllPlannedMeal();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPlannedMeal(PlannedMeal plannedMeal );
    @Delete
    void deleteMealFromFav(PlannedMeal plannedMeal);


}
