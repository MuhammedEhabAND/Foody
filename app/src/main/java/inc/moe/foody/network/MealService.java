package inc.moe.foody.network;

import inc.moe.foody.model.ListOfCategories;
import inc.moe.foody.model.ListOfMeals;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface MealService {
    @GET("categories.php")
    Call<ListOfCategories> getCategories();

    @GET("random.php")
    Call<ListOfMeals> getRandomMeal();

    @GET("filter.php?c=")
    Call<ListOfMeals> getMealByCategorySearch(@Query("c") String categoryName);


}
