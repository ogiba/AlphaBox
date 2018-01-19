package pl.alphabox.scenes.shared.details.fragments.download;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.alphabox.R;
import pl.alphabox.scenes.shared.details.fragments.progress.SharedItemProgressFragment;
import pl.alphabox.utils.BaseFragment;
import pl.alphabox.utils.BasePartFragment;

/**
 * Created by robertogiba on 18.01.2018.
 */

public class SharedItemDownloadFragment extends BasePartFragment {

    @BindView(R.id.btn_download_file)
    protected View downloadFileButton;

    public static SharedItemDownloadFragment newInstance() {

        Bundle args = new Bundle();

        SharedItemDownloadFragment fragment = new SharedItemDownloadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shared_item_download, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.btn_download_file)
    protected void downloadFileAction() {
        SharedItemProgressFragment progressFragment = SharedItemProgressFragment.newInstance();
        progressFragment.replace(getFragmentManager(), R.id.download_fragment_container);
    }
}
