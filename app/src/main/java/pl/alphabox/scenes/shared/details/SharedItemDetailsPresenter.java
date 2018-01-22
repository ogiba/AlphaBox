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
import java.util.List;

import pl.alphabox.models.User;
import pl.alphabox.models.UserFile;

/**
 * Created by robertogiba on 16.01.2018.
 */

public class SharedItemDetailsPresenter
        implements ISharedItemDetailsPresenter, ValueEventListener {
    private static final String TAG = ISharedItemDetailsPresenter.class.getSimpleName();
    private static final String STATE_STORAGE_REFERENCE = "storageReferenceState";
    private static final String STATE_DOWNLOAD_FILE = "downloadFileState";

    private final ISharedItemDetailsView itemView;

    private UserFile userFile;
    private StorageReference storageReference;
    private String downloadedFilePath;

    public SharedItemDetailsPresenter(ISharedItemDetailsView itemView) {
        this.itemView = itemView;
    }

    @Override
    public void transferData(UserFile userFile) {
        if (userFile == null) {
            return;
        }

        this.userFile = userFile;

        retrieveUserEmail();
    }

    @Override
    public void saveInstance(Bundle outState) {
        if (storageReference != null) {
            outState.putString(STATE_STORAGE_REFERENCE, storageReference.toString());
        }

        if (downloadedFilePath != null) {
            outState.putString(STATE_DOWNLOAD_FILE, downloadedFilePath);
        }
    }

    @Override
    public void restoreSavedInstance(Bundle savedState) {
        if (savedState == null) {
            return;
        }
        this.downloadedFilePath = savedState.getString(STATE_DOWNLOAD_FILE);
        final String stringStorageRef = savedState.getString(STATE_STORAGE_REFERENCE);
//        restoreStorageReference(stringStorageRef);
    }

    @Override
    public void downloadButtonClicked() {
//        try {
//            downloadFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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

            if (user != null) {
                itemView.onDataResoled(userFile.appName, "" + userFile.apkSize, user.email,
                        "" + userFile.shareTime);
            }
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
