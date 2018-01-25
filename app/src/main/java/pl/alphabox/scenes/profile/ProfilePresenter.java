package pl.alphabox.scenes.profile;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by robertogiba on 25.01.2018.
 */

public class ProfilePresenter implements IProfilePresenter {
    private IProfileView profileView;

    public ProfilePresenter(IProfileView profileView) {
        this.profileView = profileView;
    }

    @Override
    public void logoutAction() {
        FirebaseAuth.getInstance().signOut();
        profileView.onLogoutApply();
    }
}
