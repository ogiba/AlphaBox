package pl.alphabox.scenes.share.fragments.upload;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import pl.alphabox.models.AppModel;
import pl.alphabox.models.User;
import pl.alphabox.models.UserFile;

/**
 * Created by robertogiba on 23.10.2017.
 */

public class ShareUploadingPresenter
        implements IShareUploadingPresenter, OnFailureListener,
        OnProgressListener<UploadTask.TaskSnapshot>, OnSuccessListener<UploadTask.TaskSnapshot> {
    public static final String ARGS_SELECTED_USER = "SelectedUserArgument";
    public static final String ARGS_APK_TO_SHARE = "ApkToShareArgument";
    private static final String TAG = "ShareUploadingPresenter";

    final private IShareUploadingView uploadingView;
    private AppModel appModel;
    private User selectedUser;

    public ShareUploadingPresenter(IShareUploadingView uploadingView) {
        this.uploadingView = uploadingView;
    }

    @Override
    public void parseArguments(Bundle arguments) {
        if (arguments == null)
            return;

        this.appModel = arguments.getParcelable(ARGS_APK_TO_SHARE);
        this.selectedUser = arguments.getParcelable(ARGS_SELECTED_USER);
    }

    @Override
    public void uploadFile() {
        StorageReference sharedApkRef = FirebaseStorage.getInstance()
                .getReference(String.format("shared_files/app_file_%s", System.currentTimeMillis()));

        Uri file = Uri.parse("file://" + appModel.getApkUri());

        UploadTask uploadTask = sharedApkRef.putFile(file);
        uploadTask.addOnFailureListener(this);
        uploadTask.addOnProgressListener(this);
        uploadTask.addOnSuccessListener(this);
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        e.printStackTrace();
    }

    @SuppressWarnings("VisibleForTests")
    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        Uri downloadUrl = taskSnapshot.getDownloadUrl();

        if (downloadUrl != null) {
            pairUrlWithUser(downloadUrl.toString());
            Log.d(TAG, downloadUrl.toString());
            uploadingView.onUploaded();
        }
    }

    @SuppressWarnings("VisibleForTests")
    @Override
    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
        final Double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
        final int progressInInt = progress.intValue();

        uploadingView.onProgress(progressInInt);
    }

    private void pairUrlWithUser(String urlToFile) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("sharedFiles");
        String key = databaseReference.push().getKey();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            UserFile userFile = new UserFile(selectedUser.id, urlToFile, currentUser.getUid());
            userFile.setAppName(appModel.getName());

            databaseReference.child(key).setValue(userFile);
        }
    }
}
