package pl.alphabox.scenes.share.fragments.upload;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.alphabox.R;
import pl.alphabox.utils.BaseFragment;

/**
 * Created by robertogiba on 23.10.2017.
 */

public class ShareUploadingFragment extends BaseFragment implements IShareUploadingView {
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_uploading, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.uploadFile();
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
        getActivity().finish();
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
