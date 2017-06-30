package pl.alphabox.Scenes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import pl.alphabox.R;

public class MainActivity extends AppCompatActivity implements IMainView, View.OnClickListener {
    private static final int REQUEST_STORAGE_READ_ACCESS = 0;
    private static final int REQUEST_PICK_APK = 1;

    private View pickApkBtn;
    private TextView pickMessageTextView;

    private IMainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();

        setupPresenter();
        setupListeners();

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
        switch (requestCode){
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

    private void bindViews() {
        this.pickApkBtn = findViewById(R.id.btn_load_apk);
        this.pickMessageTextView = (TextView) findViewById(R.id.tv_pick_status);
    }

    private void setupPresenter() {
        this.presenter = new MainPresenter(this);
    }

    private void setupListeners() {
        this.pickApkBtn.setOnClickListener(this);
    }

    private void restoreSavedInstance(Bundle savedInstance){
        if (savedInstance != null) {
            presenter.restoreInstance(savedInstance);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_load_apk:
                presenter.requestData();
                break;
            default:
                break;
        }
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
    public void onDataProvided() {
        this.pickMessageTextView.setText(R.string.activity_main_apk_loaded);
    }
}
