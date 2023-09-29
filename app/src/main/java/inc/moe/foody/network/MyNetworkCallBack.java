package inc.moe.foody.network;

import java.util.List;

public interface MyNetworkCallBack<T> {

    void onSuccess(List<T> categories);
    void onFailed(String errorMessage);
}
