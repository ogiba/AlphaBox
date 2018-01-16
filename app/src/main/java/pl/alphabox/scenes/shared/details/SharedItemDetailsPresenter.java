package pl.alphabox.scenes.shared.details;

import pl.alphabox.models.UserFile;

/**
 * Created by robertogiba on 16.01.2018.
 */

public class SharedItemDetailsPresenter implements ISharedItemDetailsPresenter {
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

        itemView.onDataResoled(userFile.appName, "" + userFile.apkSize, userFile.userId,
                "" + userFile.shareTime);
    }
}
