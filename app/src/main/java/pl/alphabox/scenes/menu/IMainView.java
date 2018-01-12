package pl.alphabox.scenes.menu;

import android.os.Bundle;

import pl.alphabox.models.AppModel;

/**
 * Created by ogiba on 29.06.2017.
 */

public interface IMainView {
    void onLogoutUser();

    void onPermissionRequired();

    void onRequestData();

    void onDataProvided(AppModel appModel);

    void onSelectionRemoved();

    void onProceedSharingData(Bundle bundle);
}
