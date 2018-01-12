package pl.alphabox.scenes.shared;

/**
 * Created by robertogiba on 12.01.2018.
 */

public class SharedItemsPresenter implements ISharedItemsPresenter {
    private ISharedItemsView sharedItemsView;

    public SharedItemsPresenter(ISharedItemsView sharedItemsView) {
        this.sharedItemsView = sharedItemsView;
    }

    @Override
    public void loadSharedItems() {

    }
}
