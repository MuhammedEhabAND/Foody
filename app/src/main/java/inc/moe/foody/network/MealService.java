package inc.moe.foody.network;

import inc.moe.foody.model.ListOfCategories;
import inc.moe.foody.model.ListOfIngredients;
import inc.moe.foody.model.ListOfMeals;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("categories.php")
    Single<ListOfCategories> getCategories();

    @GET("random.php")
    Single<ListOfMeals> getRandomMeal();

    @GET("filter.php")
    Single<ListOfMeals> getMealByCategorySearch(@Query("c") String categoryName);

    @GET("filter.php")
    Single<ListOfMeals> getMealsByCountry(@Query("a") String countryName);

    @GET("filter.php")
    Single<ListOfMeals> getMealsByIngredient(@Query("i") String ingredientName);

    @GET("search.php")
    Single<ListOfMeals> getAllMealsByLetter(@Query("f") String letter);

    @GET("list.php?a=list")
    Single<ListOfMeals> getListOfCountries();

    @GET("list.php?i=list")
    Single<ListOfIngredients> getListOfIngredients();

    @GET("lookup.php")
    Single<ListOfMeals> getFullDetailedMeal(@Query("i") String idMeal);

}
