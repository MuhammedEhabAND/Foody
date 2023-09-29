package inc.moe.foody.model;

import inc.moe.foody.network.MyNetworkCallBack;

public interface IRepo {

    void makeNetworkCallForCategories(MyNetworkCallBack myNetworkCallBack);
    void makeNetworkCallForRandomMeal(MyNetworkCallBack myNetworkCallBack);

}
