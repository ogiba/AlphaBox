package pl.alphabox;

/**
 * Created by ogiba on 29.06.2017.
 */

public class MainPresenter implements IMainPresenter {
    private IMainView mainView;

    public MainPresenter(IMainView mainView) {
        this.mainView = mainView;
    }
}
