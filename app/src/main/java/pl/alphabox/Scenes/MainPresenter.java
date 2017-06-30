package pl.alphabox.Scenes;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

/**
 * Created by ogiba on 29.06.2017.
 * <p>
 * Class responsible for managing {@link MainActivity}
 */

public class MainPresenter implements IMainPresenter {
    private static final String ARGS_APK_URI = "appApkUri";

    private IMainView mainView;

    private boolean isReadGranted = false;
    private Uri apkUri;

    public MainPresenter(IMainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void requestData() {
        if (!isReadGranted)
            checkPermission();
        else
            this.mainView.onRequestData();
    }

    @Override
    public void updatePermission() {
        this.isReadGranted = true;
        requestData();
    }

    @Override
    public void provideData(Uri dataUri) {
        this.apkUri = dataUri;
        this.mainView.onDataProvided();
    }

    @Override
    public void saveInstance(Bundle outState) {
        if (apkUri != null)
            outState.putString(ARGS_APK_URI, apkUri.toString());
    }

    @Override
    public void restoreInstance(Bundle args) {
        String stringUri = args.getString(ARGS_APK_URI);

        if (stringUri != null) {
            this.apkUri = Uri.parse(stringUri);
            this.provideData(apkUri);
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            this.mainView.onPermissionRequired();
        else
            this.mainView.onRequestData();
    }
}
