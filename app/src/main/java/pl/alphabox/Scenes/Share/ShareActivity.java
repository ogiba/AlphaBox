package pl.alphabox.Scenes.Share;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();

        presenter.transferExtras(extras);
    }

    @Override
    protected int provideLayout() {
        return R.layout.activity_share;
    }

    @Override
    protected void setupPresenter() {
        this.presenter = new SharePresenter(this);
    }
}
