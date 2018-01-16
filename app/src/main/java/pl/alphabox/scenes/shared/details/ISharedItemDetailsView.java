package pl.alphabox.scenes.shared.details;

import pl.alphabox.models.UserFile;

/**
 * Created by robertogiba on 16.01.2018.
 */

public interface ISharedItemDetailsView {
    void onDataResoled(String appName, String appSize, String sharedByUser, String sharingTime);
}
