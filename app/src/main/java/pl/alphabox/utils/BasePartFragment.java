package pl.alphabox.utils;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.IdRes;

/**
 * Created by robertogiba on 19.01.2018.
 */

public abstract class BasePartFragment extends BaseButterKnifeFragment {
    public void replace(FragmentManager fragmentManager, @IdRes int container) {
        if (fragmentManager == null) {
            return;
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, this, "TEST");
        fragmentTransaction.commit();
    }
}
