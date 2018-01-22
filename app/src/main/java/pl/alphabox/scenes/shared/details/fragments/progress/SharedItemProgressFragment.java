package pl.alphabox.scenes.shared.details.fragments.progress;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import pl.alphabox.R;
import pl.alphabox.models.UserFile;
import pl.alphabox.scenes.shared.details.fragments.install.SharedItemInstallFragment;
import pl.alphabox.utils.BasePartFragment;

/**
 * Created by robertogiba on 18.01.2018.
 */

public class SharedItemProgressFragment extends BasePartFragment
        implements ISharedItemProgressView {

    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;

    @BindView(R.id.tv_progress_info)
    protected TextView progressInfoView;

    private ISharedItemProgressPresenter presenter;

    public static SharedItemProgressFragment newInstance(Bundle args) {
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

        presenter.restoreState(savedInstanceState);
        presenter.resolveArguments(getArguments());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        presenter.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onArgumentsResolved() {
        presenter.downloadFileAction();
    }

    @Override
    public void onDownloadFailed(String errorMessage) {
        showToast(errorMessage);
    }

    @Override
    public void onFileDownloaded(String filePath) {
        SharedItemInstallFragment installFragment = new SharedItemInstallFragment.Builder()
                .setFilePath(filePath)
                .build();
        installFragment.replace(getFragmentManager(), R.id.download_fragment_container);
    }

    @Override
    public void onDownloadingProgress(int progress) {
        progressInfoView.setText(String.format("Downloading: %s", progress));
    }

    public static class Builder {
        public static final String ARG_FILE_USER = "userFileArgument";

        private Bundle args;

        public Builder() {
            this.args = new Bundle();
        }

        public Builder setUserFile(UserFile userFile) {
            this.args.putParcelable(ARG_FILE_USER, userFile);
            return this;
        }

        public SharedItemProgressFragment build() {
            return newInstance(args);
        }
    }
}
