package pl.alphabox.Scenes;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import pl.alphabox.Models.AppModel;

/**
 * Created by ogiba on 29.06.2017.
 * <p>
 * Class responsible for managing {@link MainActivity}
 */

public class MainPresenter implements IMainPresenter {
    private static final String ARGS_APK_URI = "appApkUri";

    private IMainView mainView;
    private PackageManager packageManager;

    private boolean isReadGranted = false;
    private Uri apkUri;

    public MainPresenter(IMainView mainView, PackageManager manager) {
        this.mainView = mainView;
        this.packageManager = manager;
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
        AppModel receivedModel = extractAppInformation();

        if (receivedModel != null)
            this.mainView.onDataProvided(receivedModel.getIcon(), receivedModel.getName());
        else
            this.mainView.onDataProvided(null, null);
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

    @Nullable
    private AppModel extractAppInformation() {
        if (apkUri == null)
            return null;

        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(apkUri.getPath(), PackageManager.GET_META_DATA);

        packageInfo.applicationInfo.sourceDir = apkUri.getPath();
        packageInfo.applicationInfo.publicSourceDir = apkUri.getPath();

        Drawable icon = packageInfo.applicationInfo.loadIcon(packageManager);
        String appName = (String) packageInfo.applicationInfo.loadLabel(packageManager);

        return new AppModel(appName, icon);
    }
}
