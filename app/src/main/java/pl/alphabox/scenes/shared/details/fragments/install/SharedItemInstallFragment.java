package pl.alphabox.scenes.shared.details.fragments.install;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.BindView;
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

    @NonNull
    @Override
    protected Integer provideLayoutForFragment() {
        return R.layout.fragment_shared_item_install;
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
