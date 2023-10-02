package inc.moe.foody.search_feature.presenter;

import android.util.Log;

import java.util.List;

import inc.moe.foody.model.IRepo;
import inc.moe.foody.model.Meal;
import inc.moe.foody.network.SearchNetworkCallback;
import inc.moe.foody.search_feature.view.ISearch;

public class SearchPresenter implements ISearchPresenter , SearchNetworkCallback  {

    ISearch iSearch;
    private IRepo iRepo;

    public SearchPresenter(ISearch iSearch, IRepo iRepo) {
        this.iSearch = iSearch;
        this.iRepo = iRepo;
    }

    @Override
    public void getMealsByCategoryOf(String categoryName) {
        Log.i("TAG", "getMealsByCategoryOf: search preseneter");
        iRepo.makeNetworkCallForSearchByCategoryName(this , categoryName);

    }

    @Override
    public void onSearchByCategoryNameFromHomeSuccess(List<Meal> meals) {
        Log.i("TAG", "onSearchByCategoryNameFromHomeSuccess: ");
        iSearch.searchByCategoryGotFromHome(meals);

    }

    @Override
    public void onSearchByCategoryNameFromHomeFailure(String errorMessage) {
        Log.i("TAG", "onSearchByCategoryNameFromHomeFailure: ");
    }
}
