package pl.alphabox.scenes.shared.details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import pl.alphabox.models.User;
import pl.alphabox.models.UserFile;

/**
 * Created by robertogiba on 16.01.2018.
 */

public class SharedItemDetailsPresenter
        implements ISharedItemDetailsPresenter, ValueEventListener {
    private static final String TAG = ISharedItemDetailsPresenter.class.getSimpleName();

    private final ISharedItemDetailsView itemView;

    private UserFile userFile;

    public SharedItemDetailsPresenter(ISharedItemDetailsView itemView) {
        this.itemView = itemView;
    }

    @Override
    public void transferData(UserFile userFile) {
        if (userFile == null) {
            return;
        }

        this.userFile = userFile;

        itemView.onUserFileResolved(userFile);
        retrieveUserEmail();
    }

    @Override
    public void saveInstance(Bundle outState) {
        Log.d(TAG, "Save instance called");
    }

    @Override
    public void restoreSavedInstance(Bundle savedState) {
        Log.d(TAG, "Restoring saved instance");
    }

    private void retrieveUserEmail() {
        if (userFile.sharedByUser == null || userFile.sharedByUser.isEmpty()) {
            return;
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(userFile.sharedByUser).addValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
            User user = dataSnapshot.getValue(User.class);

            String formattedShareTime = formatDate(userFile.shareTime);

            if (user != null) {
                itemView.onDataResoled(String.format("%s v%s", userFile.appName, userFile.versionName != null
                                ? userFile.versionName : "0.0.0"),
                        "" + userFile.apkSize, user.email,
                        formattedShareTime);
            }
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.e(TAG, databaseError.getMessage());
    }

    private String formatDate(long shareTime) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(shareTime);
    }
}
