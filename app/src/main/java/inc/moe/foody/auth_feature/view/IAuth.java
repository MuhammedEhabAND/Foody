package inc.moe.foody.auth_feature.view;

public interface IAuth {
    void onLoginSuccess();
    void onLoginValidationFailure(String errorMessage, boolean isEmail);
    void onLoginFailure(String errorMessage);

    void onSignUpSuccess();
    void onSignUpFailure(String errorMessage);
    void onSignUpValidationFailure(String errorMessage , boolean isEmail);

    void onLoginWithGoogleSuccess();
    void onLoginWithGoogleFailure(String errorMessage);

}
