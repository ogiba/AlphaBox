package pl.alphabox.scenes.shared.details.fragments.progress;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

import pl.alphabox.models.UserFile;

/**
 * Created by robertogiba on 22.01.2018.
 */

public class SharedItemProgressPresenter implements ISharedItemProgressPresenter,
        OnSuccessListener<FileDownloadTask.TaskSnapshot>, OnProgressListener<FileDownloadTask.TaskSnapshot>,
        OnFailureListener {
    private static final String TAG = SharedItemProgressPresenter.class.getSimpleName();
    private static final String ARG_FILE_USER = "userFileArgument";
    private static final String STATE_STORAGE_REFERENCE = "storageReferenceState";
    private static final String STATE_DOWNLOAD_FILE = "downloadFileState";

    final private ISharedItemProgressView itemProgressView;

    private StorageReference storageReference;
    private UserFile userFile;
    private String downloadedFilePath;
    private boolean isStorageReferenceRestored = false;

    public SharedItemProgressPresenter(ISharedItemProgressView itemProgressView) {
        this.itemProgressView = itemProgressView;
    }

    @Override
    public void restoreState(Bundle savedState) {
        if (savedState == null) {
            return;
        }
        this.downloadedFilePath = savedState.getString(STATE_DOWNLOAD_FILE);
        final String stringStorageRef = savedState.getString(STATE_STORAGE_REFERENCE);
        restoreStorageReference(stringStorageRef);
    }

    @Override
    public void savedState(Bundle outState) {
        if (storageReference != null) {
            outState.putString(STATE_STORAGE_REFERENCE, storageReference.toString());
        }

        if (downloadedFilePath != null) {
            outState.putString(STATE_DOWNLOAD_FILE, downloadedFilePath);
        }
    }

    @Override
    public void resolveArguments(@Nullable Bundle arguments) {
        if (arguments == null) {
            return;
        }

        userFile = arguments.getParcelable(ARG_FILE_USER);

        if (!isStorageReferenceRestored) {
            itemProgressView.onArgumentsResolved();
        }
    }

    @Override
    public void downloadFileAction() {
        try {
            downloadFile();
        } catch (IOException e) {
            Log.e(TAG, "", e);
        }
    }

    private void downloadFile() throws IOException {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReferenceFromUrl(userFile.urlToFile);

        File localFile = File.createTempFile(userFile.appName, ".apk");
        this.downloadedFilePath = localFile.getAbsolutePath();

        storageReference.getFile(localFile).addOnSuccessListener(this).addOnProgressListener(this);
    }

    private void restoreStorageReference(String stringRef) {
        if (stringRef == null || stringRef.isEmpty()) {
            return;
        }

        this.isStorageReferenceRestored = true;

        this.storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(stringRef);

        List<FileDownloadTask> tasks = storageReference.getActiveDownloadTasks();
        if (tasks.size() > 0) {
            FileDownloadTask task = tasks.get(0);

            task.addOnSuccessListener(this)
                    .addOnProgressListener(this);
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Log.d(TAG, "File downloading failed", e);

        if (itemProgressView == null) {
            return;
        }

        itemProgressView.onDownloadFailed(e.getMessage());
    }

    @Override
    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
        Log.d(TAG, String.format("File downloaded: %s", downloadedFilePath));

        if (itemProgressView == null) {
            return;
        }

        itemProgressView.onFileDownloaded(downloadedFilePath);
    }

    @Override
    public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
        final Double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
        final int progressInInt = progress.intValue();

        Log.d(TAG, String.format("File download progress: %s", progressInInt));

        if (itemProgressView == null) {
            return;
        }

        itemProgressView.onDownloadingProgress(progressInInt);
    }
}
