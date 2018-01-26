package pl.alphabox.scenes.shared.details;

import android.os.Bundle;

import pl.alphabox.models.UserFile;

/**
 * Created by robertogiba on 16.01.2018.
 */

public interface ISharedItemDetailsPresenter {
    void transferData(UserFile userFile);

    void saveInstance(Bundle outState);

    void restoreSavedInstance(Bundle savedState);
}
