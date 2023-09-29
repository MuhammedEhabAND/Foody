package inc.moe.foody.home_feature.presenter;

import java.util.List;

import inc.moe.foody.home_feature.view.IView;
import inc.moe.foody.model.Category;
import inc.moe.foody.model.IRepo;
import inc.moe.foody.network.MyNetworkCallBack;

public class HomePresenter implements IHomePresenter, MyNetworkCallBack {

    IView iView ;
    private IRepo iRepo;

    public HomePresenter(IView iView, IRepo iRepo) {
        this.iView = iView;
        this.iRepo = iRepo;
    }

    @Override
    public void getAllCategories() {
        iRepo.makeNetworkCall(this);
    }

    @Override
    public void getRandomMeal() {

    }

    @Override
    public void onSuccess(List<Category> categories) {
        iView.onDataFetch(categories);
    }

    @Override
    public void onFailed(String errorMessage) {
        iView.onDataFetchFailed(errorMessage);
    }
}
