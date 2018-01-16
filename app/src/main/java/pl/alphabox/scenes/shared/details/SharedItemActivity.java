package pl.alphabox.scenes.shared.details;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import pl.alphabox.R;
import pl.alphabox.utils.BaseToolbarActivity;

public class SharedItemActivity extends BaseToolbarActivity<ISharedItemPresenter>
        implements ISharedItemView {

    @Override
    protected int provideLayout() {
        return R.layout.activity_shared_item;
    }

    @Override
    protected ISharedItemPresenter providePresenter() {
        return new SharedItemPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
