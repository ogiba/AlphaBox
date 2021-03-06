package pl.alphabox.scenes.profile;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import pl.alphabox.R;
import pl.alphabox.scenes.login.LoginActivity;
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

    @Override
    protected void setupToolbar() throws Exception {
        super.setupToolbar();

        ActionBar toolbar = getSupportActionBar();

        if (toolbar == null) {
            return;
        }

        toolbar.setTitle(R.string.activity_profile_scene_title);
        toolbar.setHomeButtonEnabled(true);
        toolbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setDisplayShowHomeEnabled(true);
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
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    @Override
    public void onLogoutApply() {
        runOnUiThread(() -> {
            final Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    @OnClick(R.id.btn_logout)
    protected void logoutAction() {
        showLogoutWarningDialog();
    }

    private void showLogoutWarningDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this, R.style.BlackTextDialog)
                .setTitle(R.string.activity_main_dialog_logout_title)
                .setMessage(R.string.activity_main_dialog_logout_message)
                .setPositiveButton(R.string.dialog_positive_action, (alertDialog, which) -> {
                    alertDialog.dismiss();
                    presenter.logoutAction();
                })
                .setNegativeButton(R.string.dialog_negative_action, (alertDialog, which) -> alertDialog.dismiss())
                .create();
        dialog.show();
    }
}
