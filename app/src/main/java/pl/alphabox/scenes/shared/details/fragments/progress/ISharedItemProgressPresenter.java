package pl.alphabox.scenes.shared.details.fragments.progress;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by robertogiba on 22.01.2018.
 */

public interface ISharedItemProgressPresenter {
    void restoreState(Bundle savedState);

    void savedState(Bundle outState);

    void resolveArguments(@Nullable Bundle arguments);

    void downloadFileAction();
}
