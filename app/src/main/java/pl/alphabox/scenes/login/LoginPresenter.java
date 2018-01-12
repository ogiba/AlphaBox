package pl.alphabox.scenes.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pl.alphabox.models.User;
import pl.alphabox.R;

/**
 * Created by ogiba on 04.07.2017.
 */

public class LoginPresenter
        implements ILoginPresenter, FirebaseAuth.AuthStateListener, OnCompleteListener<AuthResult> {
    private static final String TAG = "LoginPresenter";

    private ILoginView loginView;
    private FirebaseAuth firebaseAuth;

    private boolean registerMode = false;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;

        setupAuthorization();
    }

    private void setupAuthorization() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void prepareLoginSystem() {
        this.firebaseAuth.addAuthStateListener(this);
    }

    @Override
    public void releaseLoginSystem() {
        this.firebaseAuth.removeAuthStateListener(this);
    }

    @Override
    public void loginUser(String username, String pw) {

        if (!username.trim().equals("") && !pw.trim().equals("") && !registerMode)
            this.firebaseAuth.signInWithEmailAndPassword(username, pw).addOnCompleteListener(this);
        else if (username.trim().equals("")) {
            loginView.onValidationError(LoginErrorTypes.EMAIL, R.string.activity_login_login_error_label);
        } else if (pw.trim().equals("")) {
            loginView.onValidationError(LoginErrorTypes.PASSWORD, R.string.activity_login_register_error_label);
        } else if (registerMode) {
            final String repeatedPassword = loginView.onRegistrationStarted();

            registerNewUser(username, pw, repeatedPassword);
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            loginView.onAuthenticated();
        } else {
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

        if (!task.isSuccessful()) {
            Exception taskException = task.getException();
            Log.w(TAG, "signInWithEmail:failed", taskException);

            if (taskException != null) {
                loginView.onLoginFailed(taskException.getLocalizedMessage());
            } else {
                loginView.onLoginFailed(R.string.activity_login_auth_failed);
            }
        } else {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (registerMode) {
                addNewUserToDB(user);
            }
            loginView.onLoginSuccess();
        }
    }

    @Override
    public void changeMode() {
        this.registerMode = !registerMode;
        this.loginView.onChangeMode(registerMode);
    }

    @Override
    public void registerNewUser(String username, String pw, String repeatedPw) {
        if (repeatedPw.trim().equals("")) {
            loginView.onValidationError(LoginErrorTypes.REPASSWORD, R.string.activity_login_register_error_label);
            return;
        }

        if (!pw.equals(repeatedPw)) {
            loginView.onPasswordNotMatch();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(username, pw).addOnCompleteListener(this);
    }

    private void addNewUserToDB(FirebaseUser user) {
        if (user.getUid().equals("") || user.getEmail() == null ){
            return;
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users");
        User userModel = new User(user.getEmail());
        reference.child(user.getUid()).setValue(userModel);
    }
}
