package pl.alphabox.Scenes.Menu;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.alphabox.Models.AppModel;
import pl.alphabox.R;
import pl.alphabox.Scenes.Login.LoginActivity;

public class MainActivity extends AppCompatActivity implements IMainView {
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

    private IMainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupToolbar();
        setupPresenter();

        restoreSavedInstance(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_user:
                showLogoutWarningDialog();
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
    protected void onSaveInstanceState(Bundle outState) {
        presenter.saveInstance(outState);
        super.onSaveInstanceState(outState);
    }

    private void setupPresenter() {
        this.presenter = new MainPresenter(this, getPackageManager());
    }

    private void setupToolbar() {
        this.setSupportActionBar(toolbar);
        final ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayShowTitleEnabled(false);
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
        Toast.makeText(this, "Sending file", Toast.LENGTH_SHORT).show();
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

    private void changeViewState(boolean isDataProvided) {
        this.sendFileBtn.setVisibility(isDataProvided ? View.VISIBLE : View.GONE);
        this.removeCurrentSelectionBtn.setVisibility(isDataProvided ? View.VISIBLE : View.GONE);
        this.pickApkBtn.setVisibility(isDataProvided ? View.GONE : View.VISIBLE);

        this.apkNameTextView.setVisibility(isDataProvided ? View.VISIBLE : View.GONE);
        this.apkIconView.setVisibility(isDataProvided ? View.VISIBLE : View.GONE);
        this.appSizeContainer.setVisibility(isDataProvided ? View.VISIBLE : View.GONE);
    }

    private void showLogoutWarningDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.activity_main_dialog_logout_title)
                .setMessage(R.string.activity_main_dialog_logout_message)
                .setPositiveButton(R.string.dialog_positive_action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        presenter.logoutUser();
                    }
                })
                .setNegativeButton(R.string.dialog_negative_action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }
}
