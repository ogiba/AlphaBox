package pl.alphabox.scenes.menu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import pl.alphabox.R;
import pl.alphabox.models.AppModel;
import pl.alphabox.scenes.login.LoginActivity;
import pl.alphabox.scenes.profile.ProfileActivity;
import pl.alphabox.scenes.share.ShareActivity;
import pl.alphabox.scenes.shared.SharedItemsActivity;
import pl.alphabox.utils.BaseToolbarActivity;

public class MainActivity extends BaseToolbarActivity<IMainPresenter> implements IMainView {
    private static final int REQUEST_STORAGE_READ_ACCESS = 0;
    private static final int REQUEST_PICK_APK = 1;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.btn_load_apk)
    protected View pickApkBtn;
    @BindView(R.id.tv_pick_status)
    protected TextView pickMessageTextView;
    @BindView(R.id.tv_apk_name)
    protected TextView apkNameTextView;
    @BindView(R.id.iv_apk_icon)
    protected ImageView apkIconView;
    @BindView(R.id.ll_app_size_container)
    protected View appSizeContainer;
    @BindView(R.id.tv_app_size)
    protected TextView apkSizeTextView;
    @BindView(R.id.btn_send_file)
    protected View sendFileBtn;
    @BindView(R.id.btn_remove_current_file)
    protected View removeCurrentSelectionBtn;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        restoreSavedInstance(savedInstanceState);
    }

    @Override
    protected int provideLayout() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_user_profile:
                navigateToProfilePage();
                break;
            case R.id.menu_apk_list:
                navigateToSharedItems();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_PICK_APK:
                if (resultCode == RESULT_OK) {
                    presenter.provideData(data.getData());
                }
                break;
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_READ_ACCESS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.updatePermission();
                    Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Not granted", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        showToast(R.string.activity_main_exit_on_double_press);

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        presenter.saveInstance(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected IMainPresenter providePresenter() {
        return new MainPresenter(this, getPackageManager());
    }

    private void restoreSavedInstance(Bundle savedInstance) {
        if (savedInstance != null) {
            presenter.restoreInstance(savedInstance);
        }
    }

    @OnClick(R.id.btn_load_apk)
    protected void onLoadButtonAction() {
        presenter.requestData();
    }

    @OnClick(R.id.btn_send_file)
    protected void onSendFileAction() {
        presenter.proceedSharingData();
    }

    @OnClick(R.id.btn_remove_current_file)
    protected void onRemoveSelectionAction() {
        this.presenter.removeSelection();
    }

    @Override
    public void onLogoutUser() {
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPermissionRequired() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_READ_ACCESS);
        } else {
            presenter.updatePermission();
            Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestData() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivityForResult(Intent.createChooser(intent, "Test"), REQUEST_PICK_APK);
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDataProvided(AppModel appModel) {
        if (appModel == null)
            return;

        changeViewState(true);

        this.pickMessageTextView.setText(R.string.activity_main_apk_loaded);

        this.apkNameTextView.setText(appModel.getName());

        this.apkIconView.setImageDrawable(appModel.getIcon());

        this.apkSizeTextView.setText(String.format(getResources().getString(R.string.activity_main_apk_size),
                "" + appModel.getSize()));
    }

    @Override
    public void onSelectionRemoved() {
        changeViewState(false);
    }

    @Override
    public void onProceedSharingData(Bundle bundle) {
        Intent intent = new Intent(this, ShareActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void changeViewState(boolean isDataProvided) {
        this.sendFileBtn.setVisibility(isDataProvided ? View.VISIBLE : View.GONE);
        this.removeCurrentSelectionBtn.setVisibility(isDataProvided ? View.VISIBLE : View.GONE);
        this.pickApkBtn.setVisibility(isDataProvided ? View.GONE : View.VISIBLE);

        this.apkNameTextView.setVisibility(isDataProvided ? View.VISIBLE : View.GONE);
        this.apkIconView.setVisibility(isDataProvided ? View.VISIBLE : View.GONE);
        this.appSizeContainer.setVisibility(isDataProvided ? View.VISIBLE : View.GONE);
    }

    private void navigateToSharedItems() {
        Intent intent = new Intent(this, SharedItemsActivity.class);
        startActivity(intent);

        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    private void navigateToProfilePage() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);

        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }
}
