package pl.alphabox.scenes.share;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import pl.alphabox.models.AppModel;
import pl.alphabox.models.User;
import pl.alphabox.R;
import pl.alphabox.scenes.share.fragments.ShareUserFragment;
import pl.alphabox.scenes.share.fragments.upload.ShareUploadingFragment;
import pl.alphabox.scenes.share.fragments.users.IShareUserList;
import pl.alphabox.scenes.share.fragments.users.ShareUserListFragment;
import pl.alphabox.utils.BaseToolbarActivity;

public class ShareActivity extends BaseToolbarActivity<ISharePresenter>
        implements IShareView {
    private static final String FRAGMENT_USERS_LIST = "UsersListFragment";
    private static final String STATE_DONE_BUTTON = "DoneButtonState";

    @BindView(R.id.iv_app_icon)
    protected ImageView appIconView;
    @BindView(R.id.tv_app_name)
    protected TextView appNameTextView;
    @BindView(R.id.tv_app_size)
    protected TextView appSizeTextView;

    private boolean shouldShowDoneBtn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupFragment();

        if (presenter != null) {
            presenter.restoreSavedInstance(savedInstanceState);
            presenter.transferExtras(getIntent().getExtras());
        }

        restoreInstance(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(STATE_DONE_BUTTON, shouldShowDoneBtn);

        presenter.saveInstanceState(outState);
    }

    private void restoreInstance(Bundle savedInstance) {
        if (savedInstance == null)
            return;

        shouldShowDoneBtn = savedInstance.getBoolean(STATE_DONE_BUTTON);
    }

    @Override
    protected int provideLayout() {
        return R.layout.activity_share;
    }

    @Override
    protected ISharePresenter providePresenter() {
        return new SharePresenter(this, getPackageManager());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share_activity, menu);

        final MenuItem item = menu.findItem(R.id.menu_done);

        if (item != null) {
            item.setVisible(shouldShowDoneBtn);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_done:
                showToast("Done btn pressed");
                presenter.doneButtonClicked();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupFragment() {
        final FragmentManager fragmentManager = getFragmentManager();

        if (fragmentManager.findFragmentByTag(FRAGMENT_USERS_LIST) == null) {
            final Fragment fragment = new ShareUserListFragment.Builder().build();

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment, FRAGMENT_USERS_LIST);
            transaction.commit();
        }
    }

    @Override
    public void onExtrasTransferred(AppModel appModel) {
        appNameTextView.setText(appModel.getName());
        appSizeTextView.setText(String.format(getResources().getString(R.string.activity_main_apk_size),
                "" + appModel.getSize()));

        if (appModel.getIcon() != null)
            appIconView.setImageDrawable(appModel.getIcon());
    }

    @Override
    public void navigateToShareToSelectedUser(User selectedUser) {
        final ShareUserFragment.Builder builder = new ShareUserFragment.Builder();
        builder.setUser(selectedUser);
        final Fragment fragment = builder.build();

        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void changeDoneButtonVisibility(boolean shouldShow) {
        if (shouldShow && shouldShowDoneBtn)
            return;

        this.shouldShowDoneBtn = shouldShow;
        this.invalidateOptionsMenu();
    }

    @Override
    public void onDoneButtonClicked() {
        final FragmentManager fragmentManager = getFragmentManager();

        if (fragmentManager.findFragmentByTag(FRAGMENT_USERS_LIST) instanceof IShareUserList) {
            final User selectedUser = ((IShareUserList) fragmentManager.
                    findFragmentByTag(FRAGMENT_USERS_LIST)).retrievePresenter().getSelectedUser();

            presenter.transferDataToUpload(selectedUser);
        }
    }

    @Override
    public void onTransferData(Bundle args) {
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();

        final ShareUploadingFragment.Builder builder = new ShareUploadingFragment.Builder();
        builder.setArgs(args);
        final Fragment fragment = builder.build();

        transaction.replace(R.id.fragment_container, fragment, "TEST");
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
