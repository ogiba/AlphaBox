package pl.alphabox.scenes.shared.details.fragments.install;

import android.os.Bundle;
import android.util.Log;

import pl.alphabox.scenes.shared.details.fragments.download.SharedItemDownloadFragment;

/**
 * Created by robertogiba on 23.01.2018.
 */

public class SharedItemInstallPresenter implements ISharedItemInstallPresenter {
    private static final String TAG = SharedItemDownloadFragment.class.getSimpleName();

    private ISharedItemInstallView installView;
    private String filePath;

    public SharedItemInstallPresenter(ISharedItemInstallView installView) {
        this.installView = installView;
    }

    @Override
    public void resolveArguments(Bundle arguments) {
        if (arguments == null) {
            return;
        }

        filePath = arguments.getString(SharedItemInstallFragment.Builder.ARG_FILE_PATH);
        Log.d(TAG, String.format("Resolved file path: %s", filePath));
    }

    @Override
    public void installAction() {
        installView.onInstall(filePath);
    }
}
