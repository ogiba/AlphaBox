package pl.alphabox.scenes.share.fragments.upload;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.alphabox.R;
import pl.alphabox.utils.BaseButterKnifeFragment;
import pl.alphabox.utils.BaseFragment;

/**
 * Created by robertogiba on 23.10.2017.
 */

public class ShareUploadingFragment extends BaseButterKnifeFragment implements IShareUploadingView {
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.tv_progress_info)
    protected TextView progressViewInfo;

    private IShareUploadingPresenter presenter;

    public static ShareUploadingFragment newInstance(Bundle args) {
        ShareUploadingFragment fragment = new ShareUploadingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupPresenter();

        presenter.parseArguments(getArguments());
        presenter.restoreInstance(savedInstanceState);
    }

    @NonNull
    @Override
    protected Integer provideLayoutForFragment() {
        return R.layout.fragment_share_uploading;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.uploadFile();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        presenter.saveInstance(outState);
        super.onSaveInstanceState(outState);
    }

    private void setupPresenter() {
        this.presenter = new ShareUploadingPresenter(this);
    }

    @Override
    public void onProgress(int progress) {
        progressViewInfo.setText(String.format("Uploading: %s", progress));
    }

    @Override
    public void onUploaded() {
        Activity activity = getActivity();

        if (activity == null) {
            return;
        }

        activity.finish();
    }

    public static class Builder {
        private Bundle args;

        public Builder setArgs(Bundle args) {
            this.args = args;
            return this;
        }

        public ShareUploadingFragment build() {
            if (args == null)
                args = new Bundle();

            return ShareUploadingFragment.newInstance(args);
        }
    }
}
