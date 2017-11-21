package pl.alphabox.Scenes.Share;

import java.util.ArrayList;

import pl.alphabox.Models.AppModel;
import pl.alphabox.Models.UserModel;

/**
 * Created by ogiba on 12.07.2017.
 */

public interface IShareView {
    void onExtrasTransferred(AppModel appModel);

    void onLoadData(ArrayList<UserModel> items);
}
