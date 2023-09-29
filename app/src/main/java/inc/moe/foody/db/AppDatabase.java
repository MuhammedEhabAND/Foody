package inc.moe.foody.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import inc.moe.foody.model.Meal;


@Database(entities = {Meal.class} , version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance = null;
    public abstract MealDao getMealDao();
    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null ){
            instance = Room.databaseBuilder(context.getApplicationContext() ,AppDatabase.class , "favourite_meal").build();

        }
        return instance;
    }
}
