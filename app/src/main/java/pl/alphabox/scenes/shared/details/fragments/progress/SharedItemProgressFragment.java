package pl.alphabox.scenes.shared.details.fragments.progress;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import pl.alphabox.R;
import pl.alphabox.scenes.shared.details.fragments.install.SharedItemInstallFragment;
import pl.alphabox.utils.BaseFragment;

/**
 * Created by robertogiba on 18.01.2018.
 */

public class SharedItemProgressFragment extends BaseFragment {
    public static SharedItemProgressFragment newInstance() {

        Bundle args = new Bundle();

        SharedItemProgressFragment fragment = new SharedItemProgressFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shared_item_progress, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //TODO: Change it for right code
        new Handler().postDelayed(() -> getActivity().runOnUiThread(() -> {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.download_fragment_container,
                    SharedItemInstallFragment.newInstance(), "INSTALL");
            fragmentTransaction.commit();
        }), 2000);
    }
}
