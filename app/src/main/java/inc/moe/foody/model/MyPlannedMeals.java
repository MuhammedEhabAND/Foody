package inc.moe.foody.model;

import java.util.ArrayList;

public class MyPlannedMeals {

    private ArrayList<PlannedMeal> plannedMeals = new ArrayList<>() ;


    public ArrayList<PlannedMeal> getPlannedMeals() {
        return plannedMeals;
    }

    public void setPlannedMeals(ArrayList<PlannedMeal> plannedMeals) {
        this.plannedMeals = plannedMeals;
    }

    public MyPlannedMeals( ArrayList<PlannedMeal> plannedMeals) {
        this.plannedMeals = plannedMeals;
    }
    public void addMeal(PlannedMeal plannedMeal){
        if(!plannedMeals.contains(plannedMeal)){
            plannedMeals.add(plannedMeal);
        }else{

        }
    }

    public MyPlannedMeals() {
    }
}
