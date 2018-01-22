package pl.alphabox.scenes.shared.details;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import pl.alphabox.R;
import pl.alphabox.models.UserFile;
import pl.alphabox.scenes.shared.details.fragments.download.SharedItemDownloadFragment;
import pl.alphabox.utils.BaseToolbarActivity;

public class SharedItemDetailsActivity extends BaseToolbarActivity<ISharedItemDetailsPresenter>
        implements ISharedItemDetailsView {

    @BindView(R.id.tv_app_name)
    protected TextView appNameView;

    @BindView(R.id.tv_app_size)
    protected TextView appSizeView;

    @BindView(R.id.tv_user_name)
    protected TextView userNameView;

    @BindView(R.id.tv_share_time)
    protected TextView shareTimeView;

//    @BindView(R.id.btn_download_file)
//    protected View downloadFileButton;

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

        parseExtras(getIntent().getExtras());

        presenter.restoreSavedInstance(savedInstanceState);

//        setupFragment();
    }

    @Override
    protected void setupToolbar() throws Exception {
        super.setupToolbar();

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("File details");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(false);
        }
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
    protected void onSaveInstanceState(Bundle outState) {
        presenter.saveInstance(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    private void parseExtras(Bundle extras) {
        if (extras == null) {
            return;
        }

        final UserFile resolvedData = extras.getParcelable(Builder.EXTRA_USER_FILE);

        presenter.transferData(resolvedData);
    }

    private void setupFragment() {
        if (getFragmentManager().findFragmentByTag("TEST") != null) {
            return;
        }

        SharedItemDownloadFragment downloadFragment = new SharedItemDownloadFragment.Builder()
                .build();
        downloadFragment.replace(getFragmentManager(), R.id.download_fragment_container);
    }

    @Override
    public void onUserFileResolved(UserFile resolvedUserFile) {
        if (getFragmentManager().findFragmentByTag("TEST") != null) {
            return;
        }

        SharedItemDownloadFragment downloadFragment = new SharedItemDownloadFragment.Builder()
                .setUserFile(resolvedUserFile)
                .build();
        downloadFragment.replace(getFragmentManager(), R.id.download_fragment_container);
    }

    @Override
    public void onDataResoled(String appName, String appSize, String sharedByUser, String sharingTime) {
        appNameView.setText(appName);
        appSizeView.setText(appSize);
        userNameView.setText(sharedByUser);
        shareTimeView.setText(sharingTime);
    }

    @Override
    public void onFileDownloaded(String filePath) {
        showToast(String.format("Saved file at: %s", filePath));
    }

    @Override
    public void onDownloadFailed(String message) {
        showToast(String.format("Downloading failed cause: %s", message));
    }

    @Override
    public void onDownloadProgress(int progress) {

    }

//    @OnClick(R.id.btn_download_file)
//    protected void downloadFileAction() {
//        presenter.downloadButtonClicked();
//    }

    public static class Builder {
        static final String EXTRA_USER_FILE = "userFileExtra";

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
