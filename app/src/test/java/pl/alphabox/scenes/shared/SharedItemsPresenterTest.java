package pl.alphabox.scenes.shared;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by robertogiba on 12.01.2018.
 */

public class SharedItemsPresenterTest {

    @Mock
    private ISharedItemsView sharedItemsView;

    @InjectMocks
    private SharedItemsPresenter sharedItemsPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadItems_succeed() {
        sharedItemsPresenter.loadSharedItems();
    }
}
