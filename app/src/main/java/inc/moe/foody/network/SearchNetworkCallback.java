package inc.moe.foody.network;

import java.util.List;

import inc.moe.foody.model.Meal;

public interface SearchNetworkCallback {
    void onSearchByCategoryNameFromHomeSuccess(List<Meal> meals );
    void onSearchByCategoryNameFromHomeFailure(String errorMessage);

}
