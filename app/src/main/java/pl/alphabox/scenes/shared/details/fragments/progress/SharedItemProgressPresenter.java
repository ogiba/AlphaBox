package pl.alphabox.scenes.shared.details.fragments.progress;

/**
 * Created by robertogiba on 22.01.2018.
 */

public class SharedItemProgressPresenter implements ISharedItemProgressPresenter {
    final private ISharedItemProgressView itemProgressView;

    public SharedItemProgressPresenter(ISharedItemProgressView itemProgressView) {
        this.itemProgressView = itemProgressView;
    }
}
