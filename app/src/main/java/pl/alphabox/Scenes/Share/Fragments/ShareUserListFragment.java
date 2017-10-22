package pl.alphabox.Scenes.Share.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.alphabox.Models.User;
import pl.alphabox.R;
import pl.alphabox.Scenes.Share.Fragments.Adapter.OnUserClickListener;
import pl.alphabox.Scenes.Share.Fragments.Adapter.ShareUsersAdapter;
import pl.alphabox.Scenes.Share.IShareView;

/**
 * Created by robertogiba on 22.10.2017.
 */

public class ShareUserListFragment extends Fragment
        implements IShareUserList, OnUserClickListener {
    @BindView(R.id.lv_users)
    protected ListView usersListView;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;

    private IShareUserListPresenter presenter;
    private ShareUsersAdapter adapter;

    public static ShareUserListFragment newInstance(Bundle args) {
        final ShareUserListFragment fragment = new ShareUserListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        setupPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_user_list, container, false);
        ButterKnife.bind(this, view);

        presenter.restoreSavedInstance(savedInstanceState);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupAdapter();

        this.presenter.initData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.saveInstance(outState);
    }

    private void setupPresenter() {
        this.presenter = new ShareUserListPresenter(this);
    }

    private void setupAdapter() {
        this.adapter = new ShareUsersAdapter(getContext());
        adapter.setOnUserSelectListener(this);
        usersListView.setAdapter(adapter);
    }

    @Override
    public void onUserLoaded(User user) {
        this.adapter.addItem(user);

        if (progressBar.getVisibility() == View.VISIBLE)
            this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onUserSelected(View view, int position) {
        presenter.updateUserState(position);
    }

    @Override
    public void onUpdateUserState(ArrayList<User> users, boolean userSelected) {
        this.adapter.setItems(users);

        ((IShareView) getActivity()).changeDoneButtonVisibility(userSelected);
    }

    public static class Builder {
        private Bundle args;

        public void setArgs(@NonNull Bundle args) {
            this.args = args;
        }

        public ShareUserListFragment build() {
            if (args == null)
                args = new Bundle();

            return newInstance(args);
        }
    }
}
