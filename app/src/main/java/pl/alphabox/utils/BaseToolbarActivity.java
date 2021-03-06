package pl.alphabox.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import pl.alphabox.R;

/**
 * Created by ogiba on 12.07.2017.
 */

public abstract class BaseToolbarActivity<T> extends BaseActivity<T> {
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setupToolbar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setupToolbar() throws Exception {
        if (toolbar != null) {
            this.setSupportActionBar(toolbar);
            if (this.getSupportActionBar() != null) {
                this.getSupportActionBar().setDisplayShowTitleEnabled(true);
            }
        } else {
            throw new Exception("Toolbar is required when extending " + BaseToolbarActivity.class + "class");
        }
    }
}
