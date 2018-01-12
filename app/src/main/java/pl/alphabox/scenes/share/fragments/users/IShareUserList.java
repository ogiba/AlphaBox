package pl.alphabox.scenes.share.fragments.users;

import java.util.ArrayList;

import pl.alphabox.models.User;

/**
 * Created by robertogiba on 22.10.2017.
 */

public interface IShareUserList {
    void onUserLoaded(User user);

    void onUpdateUserState(ArrayList<User> users, boolean userSelected);

    IShareUserListPresenter retrievePresenter();
}
