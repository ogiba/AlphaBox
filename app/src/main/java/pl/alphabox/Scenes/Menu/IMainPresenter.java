package pl.alphabox.Scenes.Menu;

import android.net.Uri;
import android.os.Bundle;

/**
 * Created by ogiba on 29.06.2017.
 */

public interface IMainPresenter {
    void requestData();

    void provideData(Uri dataUri);

    void updatePermission();

    void saveInstance(Bundle outState);

    void restoreInstance(Bundle args);

    void logoutUser();
}
