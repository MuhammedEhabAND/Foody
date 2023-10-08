package inc.moe.foody.network;

public interface DatedMealNetworkCallback {
    void onSuccessAddPlanFb(String addedMessage);
    void onFailureAddPlanFB(String errorMessage);

    void onSuccessRemovePlanFb(String message);
}
