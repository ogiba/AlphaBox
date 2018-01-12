package pl.alphabox.scenes.share.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.alphabox.models.User;
import pl.alphabox.R;
import pl.alphabox.Utils.BaseFragment;

/**
 * Created by robertogiba on 22.10.2017.
 */

public class ShareUserFragment extends BaseFragment
        implements IShareUserView {
    @BindView(R.id.btn_accept)
    protected View acceptBtn;
    @BindView(R.id.btn_cancel)
    protected View canceltBtn;

    public static ShareUserFragment newInstance(Bundle args) {
        ShareUserFragment fragment = new ShareUserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_user, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.btn_accept)
    protected void acceptButtonAction() {
        showToast("Pressed accept");
    }

    @OnClick(R.id.btn_cancel)
    protected void cancelButtonAction() {
        showToast("Pressed cancel");
    }

    public static class Builder {
        private User user;

        public Builder setUser(@NonNull User user) {
            this.user = user;
            return this;
        }

        public ShareUserFragment build() {
            Bundle args = new Bundle();

            if (user != null)
                args.putParcelable("", user);

            return newInstance(args);
        }
    }
}
