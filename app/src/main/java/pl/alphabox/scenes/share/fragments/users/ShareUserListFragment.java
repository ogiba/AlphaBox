package pl.alphabox.scenes.share.fragments.users;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.alphabox.models.User;
import pl.alphabox.R;
import pl.alphabox.scenes.share.fragments.users.adapter.OnUserClickListener;
import pl.alphabox.scenes.share.fragments.users.adapter.ShareUsersAdapter;
import pl.alphabox.scenes.share.IShareView;
import pl.alphabox.utils.BaseFragment;

/**
 * Created by robertogiba on 22.10.2017.
 */

public class ShareUserListFragment extends BaseFragment
        implements IShareUserList, OnUserClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.lv_users)
    protected ListView usersListView;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.swipe_refresh_layout)
    protected SwipeRefreshLayout refreshLayout;

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
        final View view = inflater.inflate(R.layout.fragment_share_user_list, container, false);
        ButterKnife.bind(this, view);

        presenter.restoreSavedInstance(savedInstanceState);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupAdapter();
        setupSwipeRefresh();

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
        this.adapter = new ShareUsersAdapter(getActivity());
        adapter.setOnUserSelectListener(this);
        usersListView.setAdapter(adapter);
    }

    private void setupSwipeRefresh() {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public void onRefresh() {
        if (getActivity() instanceof IShareView) {
            ((IShareView) getActivity()).changeDoneButtonVisibility(false);
        }

        adapter.clearItems();
        presenter.reloadData();
    }

    @Override
    public void onUserLoaded(User user) {
        this.adapter.addItem(user);

        if (progressBar.getVisibility() == View.VISIBLE)
            this.progressBar.setVisibility(View.GONE);

        if (refreshLayout.isRefreshing())
            refreshLayout.setRefreshing(false);
    }

    @Override
    public void onUserSelected(View view, int position) {
        presenter.updateUserState(position);
    }

    @Override
    public void onUpdateUserState(ArrayList<User> users, boolean userSelected) {
        this.adapter.setItems(users);

        if (getActivity() instanceof IShareView)
            ((IShareView) getActivity()).changeDoneButtonVisibility(userSelected);
    }

    @Override
    public IShareUserListPresenter retrievePresenter() {
        return presenter;
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
