package inc.moe.foody.network;

import inc.moe.foody.model.ListOfCategories;
import inc.moe.foody.model.ListOfMeals;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MealService {
    @GET("categories.php")
    Call<ListOfCategories> getCategories();

    @GET("random.php")
    Call<ListOfMeals> getRandomMeal();

}
