package pl.alphabox.scenes.shared;

import java.util.ArrayList;

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
        sharedItemsView.onItemsLoad(mockItems());
    }

    private ArrayList<Object> mockItems() {
        ArrayList<Object> mockedItems = new ArrayList<>();
        mockedItems.add(new Object());
        mockedItems.add(new Object());
        mockedItems.add(new Object());
        return mockedItems;
    }
}
