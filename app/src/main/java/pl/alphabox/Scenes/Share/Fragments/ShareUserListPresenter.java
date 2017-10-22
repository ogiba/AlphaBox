package pl.alphabox.Scenes.Share.Fragments;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pl.alphabox.Models.User;
import pl.alphabox.Scenes.Share.Fragments.IShareUserList;
import pl.alphabox.Scenes.Share.Fragments.IShareUserListPresenter;

/**
 * Created by robertogiba on 22.10.2017.
 */

public class ShareUserListPresenter implements IShareUserListPresenter, ChildEventListener {

    final private IShareUserList shareUserListView;

    public ShareUserListPresenter(IShareUserList shareUserListView) {
        this.shareUserListView = shareUserListView;
    }

    @Override
    public void initData() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");
        database.addChildEventListener(this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Log.d("USER_ADDED", dataSnapshot.getKey());

        User user = dataSnapshot.getValue(User.class);

        FirebaseUser loggedUser = FirebaseAuth.getInstance().getCurrentUser();

        if (loggedUser != null && loggedUser.getEmail() != null) {
            if (loggedUser.getEmail().equals(user.email))
                return;
        }
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
}
