package pl.alphabox.Utils;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by robertogiba on 10.01.2018.
 */

public abstract class BaseFragment extends Fragment {

    protected void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show());
    }

    protected void showToast(@StringRes int stringId) {
        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), stringIds, Toast.LENGTH_SHORT).show());
    }
}
