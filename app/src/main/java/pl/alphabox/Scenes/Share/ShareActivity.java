package pl.alphabox.Scenes.Share;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import pl.alphabox.Models.AppModel;
import pl.alphabox.Models.User;
import pl.alphabox.R;
import pl.alphabox.Utils.BaseToolbarActivity;

public class ShareActivity extends BaseToolbarActivity
        implements IShareView, ListView.OnItemClickListener {
    @BindView(R.id.iv_app_icon)
    protected ImageView appIconView;
    @BindView(R.id.tv_app_name)
    protected TextView appNameTextView;
    @BindView(R.id.tv_app_size)
    protected TextView appSizeTextView;
    @BindView(R.id.lv_users)
    protected ListView usersListView;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;

    private ISharePresenter presenter;
    private ShareUsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupAdapter();
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
        this.presenter.initData();
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
    public void onUserLoaded(User user) {
        this.adapter.addItem(user);

        if (progressBar.getVisibility() == View.VISIBLE)
            this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Snackbar.make(this.getWindow().getDecorView(), "Selected: " + position, Snackbar.LENGTH_SHORT).show();
    }

    private void setupAdapter() {
        this.adapter = new ShareUsersAdapter(this);
        usersListView.setAdapter(adapter);
        usersListView.setOnItemClickListener(this);
    }
}
