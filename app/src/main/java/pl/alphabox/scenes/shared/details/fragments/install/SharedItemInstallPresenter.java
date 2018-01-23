package pl.alphabox.scenes.shared.details.fragments.install;

/**
 * Created by robertogiba on 23.01.2018.
 */

public class SharedItemInstallPresenter implements ISharedItemInstallPresenter {

    private ISharedItemInstallView installView;

    public SharedItemInstallPresenter(ISharedItemInstallView installView) {
        this.installView = installView;
    }
}
