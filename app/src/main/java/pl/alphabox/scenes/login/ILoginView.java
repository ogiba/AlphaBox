package pl.alphabox.scenes.login;

/**
 * Created by ogiba on 04.07.2017.
 */

public interface ILoginView {
    void onValidationError(LoginErrorTypes errorType, int errorRes);
    void onLoginFailed(String resInfo);
    void onLoginFailed(int resInfo);
    void onChangeMode(boolean isInRegisterMode);
    String onRegistrationStarted();
    void onPasswordNotMatch();
    void onLoginSuccess();
    void onAuthenticated();
}
