package inc.moe.foody.utils;

import java.util.List;

import inc.moe.foody.model.Category;
import inc.moe.foody.model.Country;
import inc.moe.foody.model.Ingredient;
import inc.moe.foody.model.Meal;

public class Cache {
    public static Cache instance = null ;
    List<Meal> randomMeals ;
    List<Meal> allMeals;
    List<Category> categories ;
    List<Meal> countries;
    List<Ingredient> allIngredients;
    List<Meal> searchedMeals;
    List<Meal> favMeals;

    public List<Meal> getFavMeals() {
        return favMeals;
    }

    public void setFavMeals(List<Meal> favMeals) {
        this.favMeals = favMeals;
    }

    public List<Meal> getSearchedMeals() {
        return searchedMeals;
    }

    public void setSearchedMeals(List<Meal> searchedMeals) {
        this.searchedMeals = searchedMeals;
    }

    Meal currentMeal;

    public Meal getCurrentMeal() {
        return currentMeal;
    }

    public void setCurrentMeal(Meal currentMeal) {
        this.currentMeal = currentMeal;
    }

    public List<Ingredient> getAllIngredients() {
        return allIngredients;
    }

    public void setAllIngredients(List<Ingredient> allIngredients) {
        this.allIngredients = allIngredients;
    }

    public List<Meal> getCountries() {
        return countries;
    }

    public void setCountries(List<Meal> countries) {
        this.countries = countries;
    }

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

    public List<Meal> getAllMeals() {
        return allMeals;
    }

    public void setAllMeals(List<Meal> allMeals) {
        this.allMeals = allMeals;
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
