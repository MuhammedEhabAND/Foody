package inc.moe.foody.utils;

import java.util.List;

import inc.moe.foody.model.Category;
import inc.moe.foody.model.Meal;

public class Cache {
    public static Cache instance = null ;
    List<Meal> randomMeals ;
    List<Meal> meals ;
    List<Category> categories ;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Meal> getRandomMeals() {
        return randomMeals;
    }

    public void setRandomMeals(List<Meal> randomMeals) {
        this.randomMeals = randomMeals;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    private Cache () {
    }
    public static Cache getInstance(){
        if(instance == null ){
            instance = new Cache();
        }
        return instance;
    }
}
