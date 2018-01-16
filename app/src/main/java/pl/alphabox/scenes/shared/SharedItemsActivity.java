package pl.alphabox.scenes.shared;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.OnItemClick;
import pl.alphabox.R;
import pl.alphabox.models.UserFile;
import pl.alphabox.scenes.shared.adapter.SharedItemAdapter;
import pl.alphabox.scenes.shared.details.SharedItemDetailsActivity;
import pl.alphabox.utils.BaseToolbarActivity;

public class SharedItemsActivity extends BaseToolbarActivity<ISharedItemsPresenter>
        implements ISharedItemsView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tv_empty_info)
    protected View emptyInfo;

    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;

    @BindView(R.id.lv_shared_items)
    protected ListView sharedItemsListView;

    @BindView(R.id.swipe_refresh_layout)
    protected SwipeRefreshLayout swipeRefreshLayout;

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
        setupSwipeRefreshLayout();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    private void setupShareItemsAdapter() {
        this.sharedItemsListView.setAdapter(new SharedItemAdapter());
    }

    private void setupSwipeRefreshLayout() {
        this.swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        Adapter adapter = sharedItemsListView.getAdapter();

        if (adapter != null && adapter instanceof SharedItemAdapter) {
            ((SharedItemAdapter) adapter).clearList();
            presenter.loadSharedItems();
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onResolvedItem(UserFile item) {
        if (emptyInfo.getVisibility() == View.VISIBLE) {
            emptyInfo.setVisibility(View.GONE);
        }

        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }

        Adapter adapter = sharedItemsListView.getAdapter();

        if (adapter != null && adapter instanceof SharedItemAdapter) {
            ((SharedItemAdapter) adapter).addItem(item);
        }
    }

    @Override
    public void onChildrenNotFound() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @OnItemClick(R.id.lv_shared_items)
    protected void fileItemAction(int position) {
        showToast(String.format("Clicked file at: %s", position));
        presenter.sharedItemClicked(position);
    }

    @Override
    public void onFileSelected(UserFile userFile) {
        final Intent intent = new SharedItemDetailsActivity.Builder()
                .setUserFile(userFile)
                .build(this);
        startActivity(intent);
    }
}
