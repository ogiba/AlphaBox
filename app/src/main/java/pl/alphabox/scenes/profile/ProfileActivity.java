package pl.alphabox.scenes.profile;

import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import pl.alphabox.R;
import pl.alphabox.utils.BaseToolbarActivity;

public class ProfileActivity extends BaseToolbarActivity<IProfilePresenter>
        implements IProfileView {

    @BindView(R.id.btn_logout)
    protected View logoutButton;

    @Override
    protected int provideLayout() {
        return R.layout.activity_profile;
    }

    @Override
    protected IProfilePresenter providePresenter() {
        return new ProfilePresenter(this);
    }

    @OnClick(R.id.btn_logout)
    protected void logoutAction() {
        presenter.logoutAction();
    }

    @Override
    public void onLogoutApply() {
        showToast("Login out");
    }
}
