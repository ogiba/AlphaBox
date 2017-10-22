package pl.alphabox.Scenes.Share;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import pl.alphabox.Models.AppModel;
import pl.alphabox.Models.User;
import pl.alphabox.R;
import pl.alphabox.Scenes.Share.Fragments.ShareUserFragment;
import pl.alphabox.Scenes.Share.Fragments.ShareUserListFragment;
import pl.alphabox.Utils.BaseToolbarActivity;

public class ShareActivity extends BaseToolbarActivity
        implements IShareView {
    private static final String FRAGMENT_USERS_LIST = "UsersListFragment";

    @BindView(R.id.iv_app_icon)
    protected ImageView appIconView;
    @BindView(R.id.tv_app_name)
    protected TextView appNameTextView;
    @BindView(R.id.tv_app_size)
    protected TextView appSizeTextView;

    private ISharePresenter presenter;
    private boolean shouldShowDoneBtn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupPresenter();
        setupFragment();

        if (presenter != null) {
            presenter.transferExtras(getIntent().getExtras());
        }
    }

    @Override
    protected int provideLayout() {
        return R.layout.activity_share;
    }

    @Override
    protected void setupPresenter() {
        this.presenter = new SharePresenter(this, getPackageManager());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share_activity, menu);

        MenuItem item = menu.findItem(R.id.menu_done);

        if (item != null) {
            item.setVisible(shouldShowDoneBtn);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_done:
                Toast.makeText(this, "Done btn pressed", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentByTag(FRAGMENT_USERS_LIST) == null) {
            Fragment fragment = new ShareUserListFragment.Builder().build();

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
        ShareUserFragment.Builder builder = new ShareUserFragment.Builder();
        builder.setUser(selectedUser);
        Fragment fragment = builder.build();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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
}
