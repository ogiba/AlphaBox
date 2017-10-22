package pl.alphabox.Scenes.Share.Fragments;

import android.os.Bundle;

import java.util.ArrayList;

import pl.alphabox.Models.User;

/**
 * Created by robertogiba on 22.10.2017.
 */

public interface IShareUserListPresenter {
    void initData();

    void restoreSavedInstance(Bundle savedInstance);

    void saveInstance(Bundle outState);

    void setUsers(ArrayList<User> users);

    void updateUserState(int position);
}
