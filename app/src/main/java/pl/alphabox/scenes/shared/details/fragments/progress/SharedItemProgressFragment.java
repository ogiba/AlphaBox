package pl.alphabox.scenes.shared.details.fragments.progress;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import pl.alphabox.R;
import pl.alphabox.scenes.shared.details.fragments.install.SharedItemInstallFragment;
import pl.alphabox.utils.BasePartFragment;

/**
 * Created by robertogiba on 18.01.2018.
 */

public class SharedItemProgressFragment extends BasePartFragment
        implements ISharedItemProgressView {

    private ISharedItemProgressPresenter presenter;

    public static SharedItemProgressFragment newInstance() {
        Bundle args = new Bundle();

        SharedItemProgressFragment fragment = new SharedItemProgressFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SharedItemProgressPresenter(this);
    }

    @NonNull
    @Override
    protected Integer provideLayoutForFragment() {
        return R.layout.fragment_shared_item_progress;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //TODO: Change it for right code
        new Handler().postDelayed(() -> getActivity().runOnUiThread(() -> {
            SharedItemInstallFragment installFragment = SharedItemInstallFragment.newInstance();
            installFragment.replace(getFragmentManager(), R.id.download_fragment_container);
        }), 2000);
    }
}
