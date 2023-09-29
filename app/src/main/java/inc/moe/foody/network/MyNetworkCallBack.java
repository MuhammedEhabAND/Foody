package inc.moe.foody.network;

import java.util.List;

import inc.moe.foody.model.Category;

public interface MyNetworkCallBack {

    void onSuccess(List<Category> categories);
    void onFailed(String errorMessage);
}
