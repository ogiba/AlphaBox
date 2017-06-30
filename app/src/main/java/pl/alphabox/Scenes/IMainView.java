package pl.alphabox.Scenes;

/**
 * Created by ogiba on 29.06.2017.
 */

public interface IMainView {
    void onPermissionRequired();

    void onRequestData();

    void onDataProvided();
}
