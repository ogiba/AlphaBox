package pl.alphabox.scenes.shared;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import pl.alphabox.R;
import pl.alphabox.scenes.shared.adapter.SharedItemAdapter;
import pl.alphabox.utils.BaseActivity;
import pl.alphabox.utils.BaseToolbarActivity;

public class SharedItemsActivity extends BaseToolbarActivity<ISharedItemsPresenter>
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
    protected void setupToolbar() throws Exception {
        super.setupToolbar();

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(false);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupShareItemsAdapter();

        presenter.loadSharedItems();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupShareItemsAdapter() {
        this.sharedItemsListView.setAdapter(new SharedItemAdapter());
    }

    @Override
    public void onItemsLoad(ArrayList<Object> items) {
        Adapter adapter = sharedItemsListView.getAdapter();

        if (adapter != null && adapter instanceof SharedItemAdapter) {
            ((SharedItemAdapter) adapter).setItems(items);
        }
    }
}
