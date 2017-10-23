package pl.alphabox.Scenes.Share.Fragments.Upload;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import pl.alphabox.Models.AppModel;

/**
 * Created by robertogiba on 23.10.2017.
 */

public class ShareUploadingPresenter implements IShareUploadingPresenter {
    public static final String ARGS_SELECTED_USER = "SelectedUserArgument";
    public static final String ARGS_APK_TO_SHARE = "ApkToShareArgument";
    private static final String TAG = "ShareUploadingPresenter";

    final private IShareUploadingView uploadingView;
    private AppModel appModel;

    public ShareUploadingPresenter(IShareUploadingView uploadingView) {
        this.uploadingView = uploadingView;
    }

    @Override
    public void uploadFile() {
        StorageReference sharedApksRef = FirebaseStorage.getInstance().getReference("shared_files/" +
                appModel.getName());

        Uri file = Uri.parse("file://" + appModel.getApkUri());

        UploadTask uploadTask = sharedApksRef.putFile(file);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @SuppressWarnings("VisibleForTests")
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                if (downloadUrl != null)
                    Log.d(TAG, downloadUrl.toString());
            }
        });

    }
}
