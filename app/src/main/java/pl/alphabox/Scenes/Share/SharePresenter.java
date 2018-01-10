package pl.alphabox.Scenes.Share;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import pl.alphabox.Models.AppModel;
import pl.alphabox.Models.User;
import pl.alphabox.Scenes.Share.Fragments.Upload.ShareUploadingPresenter;

/**
 * Created by ogiba on 12.07.2017.
 */

public class SharePresenter implements ISharePresenter {
    public static final String BUNDLE_APP_MODEL = "appModelBundle";
    private static final String TAG = "SharePresenter";
    
    final private IShareView shareView;
    final private PackageManager packageManager;

    private AppModel appModel;

    public SharePresenter(IShareView shareView, PackageManager packageManager) {
        this.shareView = shareView;
        this.packageManager = packageManager;
    }

    @Override
    public void transferExtras(Bundle extras) {
        if (extras == null)
            return;

        this.appModel = extras.getParcelable(BUNDLE_APP_MODEL);

        final Drawable appIcon = extractIconFromUri();
        appModel.setIcon(appIcon);

        shareView.onExtrasTransferred(appModel);
    }

    @Override
    public void saveInstanceState(Bundle outState) {
        Log.d(TAG, "Saved current instance");
    }

    @Override
    public void restoreSavedInstance(Bundle savedInstance) {
        if (savedInstance == null)
            return;

        Log.d(TAG, "Restored saved instance");
    }

    @Nullable
    private Drawable extractIconFromUri() {
        if (appModel == null)
            return null;

        final Uri apkUri = Uri.parse(appModel.getApkUri());

        final PackageInfo packageInfo = packageManager.getPackageArchiveInfo(apkUri.getPath(), PackageManager.GET_META_DATA);
//        return packageInfo.applicationInfo.loadIcon(packageManager);
        return packageManager.getApplicationIcon(packageInfo.applicationInfo);
    }

    @Override
    public void doneButtonClicked() {
        shareView.onDoneButtonClicked();
    }

    @Override
    public void transferDataToUpload(User user) {
        Bundle args = new Bundle();
        args.putParcelable(ShareUploadingPresenter.ARGS_SELECTED_USER, user);
        args.putParcelable(ShareUploadingPresenter.ARGS_APK_TO_SHARE, appModel);

        shareView.onTransferData(args);
    }
}
