package inc.moe.foody.model;

import java.util.ArrayList;

public class Meal {
    private String strMeal ;
    private String strCategory ;
    private String strArea;
    private String strInstructions;
    private String strMealThumb;
    private String strYoutube;
    private ArrayList<String> strIngredient;
    private ArrayList<String> strMeasure;
    private String strSource;

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public String getStrYoutube() {
        return strYoutube;
    }

    public void setStrYoutube(String strYoutube) {
        this.strYoutube = strYoutube;
    }

    public ArrayList<String> getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(ArrayList<String> strIngredient) {
        this.strIngredient = strIngredient;
    }

    public ArrayList<String> getStrMeasure() {
        return strMeasure;
    }

    public void setStrMeasure(ArrayList<String> strMeasure) {
        this.strMeasure = strMeasure;
    }

    public String getStrSource() {
        return strSource;
    }

    public void setStrSource(String strSource) {
        this.strSource = strSource;
    }

    public Meal() {
    }

    public Meal(String strMeal, String strCategory, String strArea, String strInstructions, String strMealThumb, String strYoutube, ArrayList<String> strIngredient, ArrayList<String> strMeasure, String strSource) {
        this.strMeal = strMeal;
        this.strCategory = strCategory;
        this.strArea = strArea;
        this.strInstructions = strInstructions;
        this.strMealThumb = strMealThumb;
        this.strYoutube = strYoutube;
        this.strIngredient = strIngredient;
        this.strMeasure = strMeasure;
        this.strSource = strSource;
    }
}
