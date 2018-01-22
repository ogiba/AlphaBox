package pl.alphabox.scenes.shared.details.fragments.progress;

/**
 * Created by robertogiba on 22.01.2018.
 */

public interface ISharedItemProgressView {
    void onArgumentsResolved();

    void onDownloadFailed(String errorMessage);

    void onFileDownloaded(String filePath);

    void onDownloadingProgress(int progress);
}
