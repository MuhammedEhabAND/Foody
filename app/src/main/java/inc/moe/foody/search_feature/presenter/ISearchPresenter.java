package inc.moe.foody.search_feature.presenter;

public interface ISearchPresenter {

    void getMealsByCategoryOf( String categoryName);
    void getMealsByCountryOf( String countryName);

    void getAllCategories();
    void getAllMeals();
    void getAllIngredients();
    void getAllCountries();


}
