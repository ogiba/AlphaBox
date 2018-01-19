package pl.alphabox.scenes.shared.details.fragments.install;

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
import pl.alphabox.utils.BasePartFragment;

/**
 * Created by robertogiba on 18.01.2018.
 */

public class SharedItemInstallFragment extends BasePartFragment {

    @BindView(R.id.btn_file_install)
    protected View fileInstallButton;

    public static SharedItemInstallFragment newInstance() {

        Bundle args = new Bundle();

        SharedItemInstallFragment fragment = new SharedItemInstallFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shared_item_install, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.btn_file_install)
    protected void fileInstallAction() {
        showToast("File install clicked");
    }
}
