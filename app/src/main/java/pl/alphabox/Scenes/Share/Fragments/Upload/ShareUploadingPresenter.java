package pl.alphabox.Scenes.Share.Fragments.Upload;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import pl.alphabox.Models.AppModel;
import pl.alphabox.Models.User;

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
        StorageReference sharedApksRef = FirebaseStorage.getInstance().getReference("shared_files/" +
                appModel.getName());

        Uri file = Uri.parse("file://" + appModel.getApkUri());

        UploadTask uploadTask = sharedApksRef.putFile(file);
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
        databaseReference.child(selectedUser.id).setValue(urlToFile);
    }
}
