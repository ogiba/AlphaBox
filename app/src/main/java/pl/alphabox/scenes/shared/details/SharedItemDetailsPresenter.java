package pl.alphabox.scenes.shared.details;

import android.util.Log;

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

        retrieveUserEmail();
    }

    @Override
    public void downloadButtonClicked() {
        try {
            downloadFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void downloadFile() throws IOException {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReferenceFromUrl(userFile.urlToFile);

        File localFile = File.createTempFile(userFile.appName, ".apk");

        storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, String.format("File downloaded: %s", localFile.getAbsolutePath()));
            }
        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                final Double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                final int progressInInt = progress.intValue();

                Log.d(TAG, String.format("File download progress: %s", progressInInt));
            }
        });

    }
}
