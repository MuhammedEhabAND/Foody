package inc.moe.foody.network;

import inc.moe.foody.model.ListOfCategories;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AllCategoriesService {
    @GET("categories.php")
    Call<ListOfCategories> getCategories();

}
