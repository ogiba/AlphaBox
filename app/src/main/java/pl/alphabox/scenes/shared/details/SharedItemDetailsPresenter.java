package pl.alphabox.scenes.shared.details;

/**
 * Created by robertogiba on 16.01.2018.
 */

public class SharedItemDetailsPresenter implements ISharedItemDetailsPresenter {
    private final ISharedItemDetailsView itemView;

    public SharedItemDetailsPresenter(ISharedItemDetailsView itemView) {
        this.itemView = itemView;
    }
}
