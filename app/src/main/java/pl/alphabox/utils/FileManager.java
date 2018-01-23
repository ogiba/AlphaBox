package pl.alphabox.utils;

import android.net.Uri;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;

/**
 * Created by robertogiba on 23.01.2018.
 */

public class FileManager {
    public static final String DIRECTORY_SHARED_ITEMS = "SharedItems";

    private File directory;

    public FileManager(File directory) {
        this.directory = directory;
    }

    public File createFile(String fileName, String... directoryPathParts) throws IOException {
        File file;

        Uri.Builder builder = new Uri.Builder();
        builder.path(directory.getPath());

        for (String pathPart : directoryPathParts) {
            builder.appendPath(pathPart);
        }

        Uri uri = builder.build();

        File directoryFile = new File(uri.toString());

        if (!directoryFile.exists()) {
            directoryFile.mkdirs();
        }

        file = new File(directoryFile, fileName);

        boolean isFileCreated = file.exists() || file.createNewFile();

        if (isFileCreated) {
            return file;
        }

        return file;
    }


    @Nullable
    @SuppressWarnings("unused")
    public static File createFile(File directory, String fileName) throws IOException {
        File file;

        if (!directory.exists()) {
            directory.mkdirs();
        }

        file = new File(directory, fileName);

        boolean isFileCreated = file.exists() || file.createNewFile();

        if (isFileCreated) {
            return file;
        }

        return null;
    }
}
