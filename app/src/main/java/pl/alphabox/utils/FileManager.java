package pl.alphabox.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;

/**
 * Created by robertogiba on 23.01.2018.
 */

public class FileManager {

    private File directory;

    public FileManager(File directory) {
        this.directory = directory;
    }

    public File saveFile(String directoryName, String fileName) throws IOException {
        File file;

        Uri.Builder builder = new Uri.Builder();
        Uri uri = builder.path(directory.getPath()).appendPath(directoryName).build();

        File directoryFile = new File(uri.toString());

        if (!directoryFile.exists()) {
            directoryFile.mkdirs();
        }

        file = new File(directory, fileName);

        boolean isFileCreated = file.exists() || file.createNewFile();

        if (isFileCreated) {
            return file;
        }

        return file;
    }


    @Nullable
    public static File saveFile(File directory, String fileName) throws IOException {
        File file;

        if (!directory.exists()) {
            directory.mkdirs();
        }

        file = new File(directory, fileName);

        boolean isFileCreated = file.exists() || file.createNewFile();

        if (isFileCreated) {
            return file;
        }

        return file;
    }
}
