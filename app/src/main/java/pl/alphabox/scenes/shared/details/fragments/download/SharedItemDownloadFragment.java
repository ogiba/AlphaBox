package pl.alphabox.scenes.shared.details.fragments.download;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import pl.alphabox.R;
import pl.alphabox.models.UserFile;
import pl.alphabox.scenes.shared.details.fragments.progress.SharedItemProgressFragment;
import pl.alphabox.utils.BasePartFragment;

/**
 * Created by robertogiba on 18.01.2018.
 */

public class SharedItemDownloadFragment extends BasePartFragment {

    @BindView(R.id.btn_download_file)
    protected View downloadFileButton;

    private UserFile userFile;

    public static SharedItemDownloadFragment newInstance(Bundle args) {

        SharedItemDownloadFragment fragment = new SharedItemDownloadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected Integer provideLayoutForFragment() {
        return R.layout.fragment_shared_item_download;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userFile = getArguments().getParcelable(Builder.ARG_FILE_USER);
    }

    @OnClick(R.id.btn_download_file)
    protected void downloadFileAction() {
        SharedItemProgressFragment progressFragment = new SharedItemProgressFragment.Builder()
                .setUserFile(userFile)
                .build();
        progressFragment.replace(getFragmentManager(), R.id.download_fragment_container);
    }

    public static class Builder {
        public static final String ARG_FILE_USER = "userFileArgument";
        public static final String ARG_BUNDLE = "bundleArgument";

        private Bundle args;

        public Builder() {
            this.args = new Bundle();
        }

        public Builder setUserFile(UserFile userFile) {
            this.args.putParcelable(ARG_FILE_USER, userFile);
            return this;
        }

        @SuppressWarnings("unsued")
        public Builder setBundle(Bundle bundle) {
            this.args.putBundle(ARG_BUNDLE, bundle);
            return this;
        }

        public SharedItemDownloadFragment build() {
            return newInstance(args);
        }
    }
}
