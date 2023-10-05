package inc.moe.foody.auth_feature.presenter;

public interface IAuthPresenter {
    void LoginWithEmailAndPassword(String email ,String password);
    void SignUpWithEmailAndPassword(String email ,String password ,String confirmPassword);
    void LoginWithGoogle(String idToken);
}
