package pl.alphabox.scenes.profile;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by robertogiba on 25.01.2018.
 */

public class ProfilePresenter implements IProfilePresenter {
    private IProfileView profileView;

    public ProfilePresenter(@NonNull IProfileView profileView) {
        this.profileView = profileView;
    }

    @Override
    public void logoutAction() {
        FirebaseAuth authInstance = FirebaseAuth.getInstance();
        authInstance.addAuthStateListener(firebaseAuth -> profileView.onLogoutApply());
        authInstance.signOut();
    }
}
