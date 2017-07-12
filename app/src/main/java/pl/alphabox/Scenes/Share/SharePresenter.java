package pl.alphabox.Scenes.Share;

import android.os.Bundle;

/**
 * Created by ogiba on 12.07.2017.
 */

public class SharePresenter implements ISharePresenter {
    public static final String BUNDLE_APP_MODEL = "appModelBundle";

    private IShareView shareView;

    public SharePresenter(IShareView shareView) {
        this.shareView = shareView;
    }

    @Override
    public void transferExtras(Bundle extras) {

    }
}
