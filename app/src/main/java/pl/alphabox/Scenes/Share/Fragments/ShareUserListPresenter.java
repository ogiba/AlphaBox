package pl.alphabox.Scenes.Share.Fragments;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import pl.alphabox.Models.User;
import pl.alphabox.Scenes.Share.Fragments.IShareUserList;
import pl.alphabox.Scenes.Share.Fragments.IShareUserListPresenter;

/**
 * Created by robertogiba on 22.10.2017.
 */

public class ShareUserListPresenter implements IShareUserListPresenter, ChildEventListener {
    private static final String BUNDLE_USERS_LIST = "UsersListBundle";

    final private IShareUserList shareUserListView;

    private ArrayList<User> users;

    public ShareUserListPresenter(IShareUserList shareUserListView) {
        this.shareUserListView = shareUserListView;
        this.users = new ArrayList<>();
    }

    @Override
    public void initData() {
        if (users.size() == 0) {
            final DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");
            database.addChildEventListener(this);
        } else {
            for (User user : users) {
                shareUserListView.onUserLoaded(user);
            }
        }
    }

    @Override
    public void reloadData() {
        users.clear();
        initData();
    }

    @Override
    public void saveInstance(Bundle outState) {
        outState.putParcelableArrayList(BUNDLE_USERS_LIST, users);
    }

    @Override
    public void restoreSavedInstance(Bundle savedInstance) {
        if (savedInstance == null)
            return;

        final ArrayList<User> users = savedInstance.getParcelableArrayList(BUNDLE_USERS_LIST);
        this.setUsers(users);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Log.d("USER_ADDED", dataSnapshot.getKey());

        final User user = dataSnapshot.getValue(User.class);
        user.id = dataSnapshot.getKey();

        final FirebaseUser loggedUser = FirebaseAuth.getInstance().getCurrentUser();

        if (loggedUser != null && loggedUser.getEmail() != null) {
            if (loggedUser.getEmail().equals(user.email))
                return;
        }

        users.add(user);
        shareUserListView.onUserLoaded(user);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    @Override
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public void updateUserState(int position) {
        for (int i = 0; i < users.size(); i++) {
            if (i != position) {
                users.get(i).setSelected(false);
            }
        }

        final User user = this.users.get(position);
        user.setSelected(!user.isSelected());

        shareUserListView.onUpdateUserState(users, user.isSelected());
    }
}
