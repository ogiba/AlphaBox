package pl.alphabox.scenes.profile;

/**
 * Created by robertogiba on 25.01.2018.
 */

public class ProfilePresenter implements IProfilePresenter {
    private IProfileView profileView;

    public ProfilePresenter(IProfileView profileView) {
        this.profileView = profileView;
    }
}
