package pl.alphabox.utils;

import android.app.Fragment;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by robertogiba on 10.01.2018.
 */

public abstract class BaseFragment extends Fragment {

    protected void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show());
    }

    protected void showToast(@StringRes int stringId) {
        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), stringId, Toast.LENGTH_SHORT).show());
    }
}
