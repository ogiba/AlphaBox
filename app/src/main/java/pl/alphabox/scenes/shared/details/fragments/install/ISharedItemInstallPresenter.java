package pl.alphabox.scenes.shared.details.fragments.install;

import android.os.Bundle;

/**
 * Created by robertogiba on 23.01.2018.
 */

public interface ISharedItemInstallPresenter {
    void resolveArguments(Bundle arguments);

    void installAction();
}
