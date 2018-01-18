package pl.alphabox.scenes.shared.details.fragments.progress;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import pl.alphabox.R;
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
}
