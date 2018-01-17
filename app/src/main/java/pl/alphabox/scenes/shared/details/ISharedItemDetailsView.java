package pl.alphabox.scenes.shared.details;

/**
 * Created by robertogiba on 16.01.2018.
 */

public interface ISharedItemDetailsView {
    void onDataResoled(String appName, String appSize, String userName, String shareTime);

    void onFileDownloaded(String filePath);

    void onDownloadFailed(String message);

    void onDownloadProgress(int progress);
}
