package inc.moe.foody.model;

import java.util.ArrayList;

public class ListOfMeals {
    private ArrayList<Meal> meals;

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }

    public ListOfMeals() {
    }

    public ListOfMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }
}
