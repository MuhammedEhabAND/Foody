package inc.moe.foody.utils;

import com.google.firebase.firestore.DocumentSnapshot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import inc.moe.foody.model.Meal;

public class MealToMapConverter {
        public static Map<String, Object> convertToMap(Object obj , String userID) {
            Map<String, Object> map = new HashMap<>();
            Field[] fields = obj.getClass().getDeclaredFields();

            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    Object value = field.get(obj);
                    map.put(fieldName, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
            map.put("userID" ,userID);

            return map;
        }
    public static Meal convertToMeal(DocumentSnapshot documentSnapshot) {
        // Document doesn't exist
        if (documentSnapshot.exists()) {
            // Create a new Meal object
            Meal meal = new Meal();

            // Get all declared fields in the Meal class
            Field[] fields = meal.getClass().getDeclaredFields();

            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    String fieldName = field.getName();

                    // Handle special case for the 'idMeal' field since it's marked as @NonNull
                    if ("idMeal".equals(fieldName)) {
                        field.set(meal, documentSnapshot.getString("idMeal"));
                    } else {
                        field.set(meal, documentSnapshot.getString(fieldName));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            // Extract individual ingredients and measures
            for (int i = 1; i <= 20; i++) {
                try {
                    Field ingredientField = meal.getClass().getDeclaredField("strIngredient" + i);
                    Field measureField = meal.getClass().getDeclaredField("strMeasure" + i);
                    ingredientField.setAccessible(true);
                    measureField.setAccessible(true);

                    String ingredient = documentSnapshot.getString("strIngredient" + i);
                    String measure = documentSnapshot.getString("strMeasure" + i);

                    // Set the ingredient and measure fields
                    ingredientField.set(meal, ingredient);
                    measureField.set(meal, measure);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return meal;
        } else return null;
    }
}
