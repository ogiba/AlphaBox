package pl.alphabox.scenes.shared.details;

/**
 * Created by robertogiba on 16.01.2018.
 */

public class SharedItemPresenter implements ISharedItemPresenter {
    private final ISharedItemView itemView;

    public SharedItemPresenter(ISharedItemView itemView) {
        this.itemView = itemView;
    }
}
