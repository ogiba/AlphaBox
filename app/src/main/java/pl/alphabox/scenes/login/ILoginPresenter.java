package pl.alphabox.scenes.login;

/**
 * Created by ogiba on 04.07.2017.
 */

public interface ILoginPresenter {
    void prepareLoginSystem();

    void releaseLoginSystem();

    void loginUser(String username, String pw);

    void changeMode();

    void registerNewUser(String username, String pw, String repeatedPw);
}
