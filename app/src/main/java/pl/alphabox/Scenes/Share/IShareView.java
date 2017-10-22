package pl.alphabox.Scenes.Share;

import pl.alphabox.Models.AppModel;
import pl.alphabox.Models.User;

/**
 * Created by ogiba on 12.07.2017.
 */

public interface IShareView {
    void onExtrasTransferred(AppModel appModel);

    void navigateToShareToSelectedUser(User user);

    void changeDoneButtonVisibility(boolean shouldShow);
}
