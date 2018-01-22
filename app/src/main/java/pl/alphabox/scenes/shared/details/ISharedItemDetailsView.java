package pl.alphabox.scenes.shared.details;

import pl.alphabox.models.UserFile;

/**
 * Created by robertogiba on 16.01.2018.
 */

public interface ISharedItemDetailsView {
    void onUserFileResolved(UserFile resolvedUserFile);

    void onDataResoled(String appName, String appSize, String userName, String shareTime);

    void onFileDownloaded(String filePath);

    void onDownloadFailed(String message);

    void onDownloadProgress(int progress);
}
