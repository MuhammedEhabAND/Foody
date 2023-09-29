package inc.moe.foody.network;

public interface RemoteSource {
    void makeNetworkCallForCategories(MyNetworkCallBack myNetworkCallBack);
    void makeNetworkCallForRandomMeal(MyNetworkCallBack myNetworkCallBack);

}
