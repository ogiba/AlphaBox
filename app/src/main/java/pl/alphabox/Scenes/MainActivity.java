package pl.alphabox.Scenes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.alphabox.R;

public class MainActivity extends AppCompatActivity implements IMainView {
    private static final int REQUEST_STORAGE_READ_ACCESS = 0;
    private static final int REQUEST_PICK_APK = 1;

    @BindView(R.id.btn_load_apk)
    protected View pickApkBtn;
    @BindView(R.id.tv_pick_status)
    protected TextView pickMessageTextView;
    @BindView(R.id.tv_apk_name)
    protected TextView apkNameTextView;
    @BindView(R.id.iv_apk_icon)
    protected ImageView apkIconView;

    private IMainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupPresenter();

        restoreSavedInstance(savedInstanceState);
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

    private void restoreSavedInstance(Bundle savedInstance) {
        if (savedInstance != null) {
            presenter.restoreInstance(savedInstance);
        }
    }

    @OnClick(R.id.btn_load_apk)
    protected void onLoadButtonAction() {
        presenter.requestData();
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
    public void onDataProvided(Drawable appIcon, String appName) {
        if (appIcon == null || appName == null)
            return;

        this.pickMessageTextView.setText(R.string.activity_main_apk_loaded);

        this.apkNameTextView.setText(appName);
        this.apkNameTextView.setVisibility(View.VISIBLE);
        this.apkIconView.setImageDrawable(appIcon);
        this.apkIconView.setVisibility(View.VISIBLE);
    }
}
