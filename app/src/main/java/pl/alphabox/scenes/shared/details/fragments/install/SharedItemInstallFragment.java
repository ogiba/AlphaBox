package pl.alphabox.scenes.shared.details.fragments.install;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import pl.alphabox.R;
import pl.alphabox.utils.BasePartFragment;

/**
 * Created by robertogiba on 18.01.2018.
 */

public class SharedItemInstallFragment extends BasePartFragment implements ISharedItemInstallView {
    private static final String TAG = SharedItemInstallFragment.class.getSimpleName();

    @BindView(R.id.btn_file_install)
    protected View fileInstallButton;

    private ISharedItemInstallPresenter presenter;

    public static SharedItemInstallFragment newInstance(Bundle args) {
        SharedItemInstallFragment fragment = new SharedItemInstallFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SharedItemInstallPresenter(this);
    }

    @NonNull
    @Override
    protected Integer provideLayoutForFragment() {
        return R.layout.fragment_shared_item_install;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String filePath = getArguments().getString(Builder.ARG_FILE_PATH);
        Log.d(TAG, String.format("Resolved file path: %s", filePath));
    }

    @OnClick(R.id.btn_file_install)
    protected void fileInstallAction() {
        showToast("File install clicked");
    }

    public static class Builder {
        public static final String ARG_FILE_PATH = "filePathArgument";

        private Bundle args;

        public Builder() {
            this.args = new Bundle();
        }

        @SuppressWarnings("unused")
        public Builder(Bundle args) {
            this.args = args;
        }

        public Builder setFilePath(String filePath) {
            this.args.putString(ARG_FILE_PATH, filePath);
            return this;
        }

        public SharedItemInstallFragment build() {
            return newInstance(args);
        }
    }
}
