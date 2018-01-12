package pl.alphabox.scenes.shared;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import pl.alphabox.R;
import pl.alphabox.utils.BaseActivity;

public class SharedItemsActivity extends BaseActivity<ISharedItemsPresenter>
        implements ISharedItemsView {

    @BindView(R.id.lv_shared_items)
    protected ListView sharedItemsListView;

    @Override
    protected int provideLayout() {
        return R.layout.activity_shared_items;
    }

    @Override
    protected ISharedItemsPresenter providePresenter() {
        return new SharedItemsPresenter(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.loadSharedItems();
    }

    @Override
    public void onItemsLoad(ArrayList<Object> items) {

    }
}
