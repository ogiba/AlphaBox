package pl.alphabox.scenes.shared;

import android.os.Bundle;

import pl.alphabox.models.UserFile;

/**
 * Created by robertogiba on 12.01.2018.
 */

public interface ISharedItemsView {
    void onResolvedItem(UserFile item);

    void onChildrenNotFound();

    void onFileSelected(Bundle bundle);
}
