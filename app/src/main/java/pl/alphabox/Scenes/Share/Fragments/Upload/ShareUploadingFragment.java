package pl.alphabox.Scenes.Share.Fragments.Upload;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.alphabox.R;

/**
 * Created by robertogiba on 23.10.2017.
 */

public class ShareUploadingFragment extends Fragment implements IShareUploadingView {
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;

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
    }

    private void setupPresenter() {
        this.presenter = new ShareUploadingPresenter(this);
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
