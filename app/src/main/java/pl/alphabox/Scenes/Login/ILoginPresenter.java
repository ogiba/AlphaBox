package pl.alphabox.Scenes.Login;

/**
 * Created by ogiba on 04.07.2017.
 */

public interface ILoginPresenter {
    void loginUser(String username, String pw);
    void prepareLoginSystem();
    void releaseLoginSystem();
}
