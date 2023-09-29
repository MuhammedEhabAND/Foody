package inc.moe.foody.favourite_feature.presenter;

import androidx.lifecycle.LiveData;

import java.util.List;

import inc.moe.foody.model.IRepo;
import inc.moe.foody.model.Meal;

public class FavouritePresenter implements IFavouritePresenter {

    private IRepo repo;

    public FavouritePresenter( IRepo repo) {
        this.repo = repo;
    }

    @Override
    public LiveData<List<Meal>> getFavMeals() {
        return repo.getFavMeals();
    }

    @Override
    public void removeFromDataBase(Meal meal) {
        repo.removeMealFromFav(meal);
    }

}
