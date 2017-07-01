package pl.alphabox.Scenes;

import android.graphics.drawable.Drawable;

/**
 * Created by ogiba on 29.06.2017.
 */

public interface IMainView {
    void onPermissionRequired();

    void onRequestData();

    void onDataProvided(Drawable appIcon, String appName);
}
