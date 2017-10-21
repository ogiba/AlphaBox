package pl.alphabox.Scenes.Share;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import pl.alphabox.Models.AppModel;
import pl.alphabox.Models.User;

/**
 * Created by ogiba on 12.07.2017.
 */

public class SharePresenter implements ISharePresenter {
    public static final String BUNDLE_APP_MODEL = "appModelBundle";

    private IShareView shareView;
    private PackageManager packageManager;

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

        Drawable appIcon = extractIconFromUri();
        appModel.setIcon(appIcon);

        shareView.onExtrasTransferred(appModel);
    }

    @Nullable
    private Drawable extractIconFromUri() {
        if (appModel == null)
            return null;

        Uri apkUri = Uri.parse(appModel.getApkUri());

        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(apkUri.getPath(), PackageManager.GET_META_DATA);
        return packageInfo.applicationInfo.loadIcon(packageManager);
    }
}
