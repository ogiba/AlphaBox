package pl.alphabox.Scenes.Share.Fragments.Upload;

import android.os.Bundle;

/**
 * Created by robertogiba on 23.10.2017.
 */

public interface IShareUploadingPresenter {

    void parseArguments(Bundle arguments);

    void uploadFile();
}
