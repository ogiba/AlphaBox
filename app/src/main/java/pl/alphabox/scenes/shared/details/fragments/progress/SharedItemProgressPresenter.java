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
import pl.alphabox.utils.FileManager;

/**
 * Created by robertogiba on 22.01.2018.
 */

public class SharedItemProgressPresenter implements ISharedItemProgressPresenter,
        OnSuccessListener<FileDownloadTask.TaskSnapshot>, OnProgressListener<FileDownloadTask.TaskSnapshot>,
        OnFailureListener {
    private static final String TAG = SharedItemProgressPresenter.class.getSimpleName();
    private static final String STATE_STORAGE_REFERENCE = "storageReferenceState";
    private static final String STATE_DOWNLOAD_FILE = "downloadFileState";

    private final ISharedItemProgressView itemProgressView;
    private final FileManager fileManager;

    private StorageReference storageReference;
    private UserFile userFile;
    private String downloadedFilePath;
    private boolean isStorageReferenceRestored = false;

    public SharedItemProgressPresenter(@NonNull ISharedItemProgressView itemProgressView,
                                       @NonNull FileManager fileManager) {
        this.itemProgressView = itemProgressView;
        this.fileManager = fileManager;
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
    public void saveInstanceState(Bundle outState) {
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

        userFile = arguments.getParcelable(SharedItemProgressFragment.Builder.ARG_FILE_USER);

        if (!isStorageReferenceRestored) {
            itemProgressView.onArgumentsResolved();
        }
    }

    @Override
    public void downloadFileAction() {
        if (userFile == null) {
            Log.e(TAG, "UserFile cannot be null");
            return;
        }

        try {
            downloadFile();
        } catch (IOException e) {
            Log.e(TAG, "", e);
        }
    }

    private void downloadFile() throws IOException {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReferenceFromUrl(userFile.urlToFile);

        File localFile = fileManager.createFile(userFile.appName + ".apk",
                FileManager.DIRECTORY_SHARED_ITEMS, userFile.appName);

        if (localFile != null) {
            this.downloadedFilePath = localFile.getAbsolutePath();
            storageReference.getFile(localFile).addOnSuccessListener(this).addOnProgressListener(this);
        }
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

        itemProgressView.onDownloadFailed(e.getMessage());
    }

    @Override
    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
        Log.d(TAG, String.format("File downloaded: %s", downloadedFilePath));

        itemProgressView.onFileDownloaded(downloadedFilePath);
    }

    @Override
    public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
        final Double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
        final int progressInInt = progress.intValue();

        Log.d(TAG, String.format("File download progress: %s", progressInInt));

        itemProgressView.onDownloadingProgress(progressInInt);
    }
}
