package pl.alphabox.Scenes.Menu;

import pl.alphabox.Models.AppModel;

/**
 * Created by ogiba on 29.06.2017.
 */

public interface IMainView {
    void onLogoutUser();

    void onPermissionRequired();

    void onRequestData();

    void onDataProvided(AppModel appModel);
}
