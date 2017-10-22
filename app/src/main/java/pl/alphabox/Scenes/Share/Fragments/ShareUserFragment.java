package pl.alphabox.Scenes.Share.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import pl.alphabox.Models.User;
import pl.alphabox.R;

/**
 * Created by robertogiba on 22.10.2017.
 */

public class ShareUserFragment extends Fragment implements IShareUserView {

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
