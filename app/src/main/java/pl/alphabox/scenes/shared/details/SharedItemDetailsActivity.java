package pl.alphabox.scenes.shared.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import pl.alphabox.R;
import pl.alphabox.models.UserFile;
import pl.alphabox.utils.BaseToolbarActivity;

public class SharedItemDetailsActivity extends BaseToolbarActivity<ISharedItemDetailsPresenter>
        implements ISharedItemDetailsView {

    @Override
    protected int provideLayout() {
        return R.layout.activity_shared_item;
    }

    @Override
    protected ISharedItemDetailsPresenter providePresenter() {
        return new SharedItemDetailsPresenter(this);
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

    public static class Builder {
        private static final String EXTRA_USER_FILE = "userFileExtra";

        private Bundle extras;

        public Builder() {
            this.extras = new Bundle();
        }

        public Builder setUserFile(UserFile userFile) {
            this.extras.putParcelable(EXTRA_USER_FILE, userFile);
            return this;
        }

        public Intent build(Context context) {
            final Intent intent = new Intent(context, SharedItemDetailsActivity.class);
            intent.putExtras(extras);
            return intent;
        }
    }
}
