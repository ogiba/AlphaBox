package pl.alphabox.scenes.shared;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pl.alphabox.models.UserFile;

/**
 * Created by robertogiba on 12.01.2018.
 */

public class SharedItemsPresenter implements ISharedItemsPresenter, ValueEventListener {
    private static final String TAG = SharedItemsPresenter.class.getSimpleName();

    private final ISharedItemsView sharedItemsView;
    private ArrayList<UserFile> files;

    public SharedItemsPresenter(ISharedItemsView sharedItemsView) {
        this.sharedItemsView = sharedItemsView;
        this.files = new ArrayList<>();
    }

    @Override
    public void loadSharedItems() {
        loadItems();
    }

    private void loadItems() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {
            return;
        }

        Query query = FirebaseDatabase.getInstance().getReference("sharedFiles");
        query.orderByChild("userId").equalTo(currentUser.getUid()).addValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.hasChildren()) {
            for (DataSnapshot sharedFilesSnapshot : dataSnapshot.getChildren()) {
                final UserFile userFile = sharedFilesSnapshot.getValue(UserFile.class);

                if (userFile != null) {
                    this.files.add(userFile);
                    this.sharedItemsView.onResolvedItem(userFile);
                }
            }
        } else {
            this.sharedItemsView.onChildrenNotFound();
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.d(TAG, "loadPost:onCancelled", databaseError.toException());
    }

    @Override
    public void sharedItemClicked(int position) {
        final UserFile clickedItem = this.files.get(position);

        Bundle bundle = new Bundle();
        bundle.putParcelable("TEST", clickedItem);

        sharedItemsView.onFileSelected(bundle);
    }
}
