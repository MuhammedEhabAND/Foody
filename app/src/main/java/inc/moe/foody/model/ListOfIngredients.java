package inc.moe.foody.model;

import java.util.ArrayList;

public class ListOfIngredients {
    private ArrayList<Ingredient> meals;

    public ArrayList<Ingredient> getIngredients() {
        return meals;
    }

    public void setIngredients(ArrayList<Ingredient> meals) {
        this.meals = meals;
    }

    public ListOfIngredients(ArrayList<Ingredient> meals) {
        this.meals = meals;
    }
}
