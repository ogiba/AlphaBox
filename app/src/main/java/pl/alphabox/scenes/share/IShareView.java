package pl.alphabox.scenes.share;

import android.os.Bundle;

import pl.alphabox.models.AppModel;
import pl.alphabox.models.User;

/**
 * Created by ogiba on 12.07.2017.
 */

public interface IShareView {
    void onExtrasTransferred(AppModel appModel);

    void onDoneButtonClicked();

    void navigateToShareToSelectedUser(User user);

    void changeDoneButtonVisibility(boolean shouldShow);

    void onTransferData(Bundle args);
}
