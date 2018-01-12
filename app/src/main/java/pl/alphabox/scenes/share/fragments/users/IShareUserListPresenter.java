package pl.alphabox.scenes.share.fragments.users;

import android.os.Bundle;

import java.util.ArrayList;

import pl.alphabox.models.User;

/**
 * Created by robertogiba on 22.10.2017.
 */

public interface IShareUserListPresenter {
    void initData();

    void reloadData();

    void restoreSavedInstance(Bundle savedInstance);

    void saveInstance(Bundle outState);

    void setUsers(ArrayList<User> users);

    void updateUserState(int position);

    User getSelectedUser();
}
