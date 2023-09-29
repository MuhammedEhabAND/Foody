package inc.moe.foody.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import inc.moe.foody.model.Meal;
@Dao
public interface MealDao {
    @Query("SELECT * FROM favourite_meal")
    LiveData<List<Meal>> getAllFavMeals();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMealToFav(Meal meal);
    @Delete
    void deleteMealFromFav(Meal meal);

}
