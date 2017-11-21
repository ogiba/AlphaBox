package pl.alphabox.Scenes.Share;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import pl.alphabox.Models.AppModel;
import pl.alphabox.Models.UserModel;
import pl.alphabox.R;
import pl.alphabox.Utils.BaseToolbarActivity;

public class ShareActivity extends BaseToolbarActivity implements IShareView {
    @BindView(R.id.iv_app_icon)
    protected ImageView appIconView;
    @BindView(R.id.tv_app_name)
    protected TextView appNameTextView;
    @BindView(R.id.tv_app_size)
    protected TextView appSizeTextView;

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
    public void onLoadData(ArrayList<UserModel> items) {
        adapter.setItems(items);
    }

    private void setupAdapter() {
        this.adapter = new ShareUsersAdapter(this);
    }
}
