package pl.alphabox.Scenes.Login;

/**
 * Created by ogiba on 04.07.2017.
 */

public interface ILoginView {
    void onLoginFailed(String resInfo);
    void onLoginFailed(int resInfo);
    void onChangeMode(boolean isInRegisterMode);
    void onPasswordNotMatch();
}
