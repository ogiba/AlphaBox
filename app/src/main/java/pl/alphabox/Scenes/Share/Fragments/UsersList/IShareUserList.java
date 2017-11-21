package pl.alphabox.Scenes.Share.Fragments.UsersList;

import java.util.ArrayList;

import pl.alphabox.Models.User;

/**
 * Created by robertogiba on 22.10.2017.
 */

public interface IShareUserList {
    void onUserLoaded(User user);

    void onUpdateUserState(ArrayList<User> users, boolean userSelected);

    IShareUserListPresenter retrievePresenter();
}
