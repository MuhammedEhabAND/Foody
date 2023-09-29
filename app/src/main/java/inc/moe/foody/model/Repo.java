package inc.moe.foody.model;

import inc.moe.foody.network.MyNetworkCallBack;
import inc.moe.foody.network.RemoteSource;

public class Repo implements IRepo{
    RemoteSource remoteSource;
    private static Repo instance = null;



    private Repo(RemoteSource remoteSource) {
        this.remoteSource = remoteSource;

    }

    public static Repo getInstance(RemoteSource remoteSource) {
        if (instance == null) return new Repo(remoteSource);
        return instance;
    }


    @Override
    public void makeNetworkCallForCategories(MyNetworkCallBack myNetworkCallBack) {
        remoteSource.makeNetworkCallForCategories(myNetworkCallBack);
    }

    @Override
    public void makeNetworkCallForRandomMeal(MyNetworkCallBack myNetworkCallBack) {
        remoteSource.makeNetworkCallForRandomMeal(myNetworkCallBack);
    }
}
