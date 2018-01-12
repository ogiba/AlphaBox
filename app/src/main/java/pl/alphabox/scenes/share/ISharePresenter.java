package pl.alphabox.scenes.share;

import android.os.Bundle;

import pl.alphabox.models.User;

/**
 * Created by ogiba on 12.07.2017.
 */

public interface ISharePresenter {
    void transferExtras(Bundle extras);

    void saveInstanceState(Bundle outState);

    void restoreSavedInstance(Bundle savedInstance);

    void doneButtonClicked();

    void transferDataToUpload(User user);
}
