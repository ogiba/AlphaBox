package pl.alphabox.scenes.shared;

import pl.alphabox.models.UserFile;

/**
 * Created by robertogiba on 12.01.2018.
 */

public interface ISharedItemsView {
    void onResolvedItem(UserFile item);

    void onChildrenNotFound();

    void onFileSelected(UserFile selectedFile);
}
