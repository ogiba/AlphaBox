package pl.alphabox.Scenes.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.alphabox.R;
import pl.alphabox.Scenes.Menu.MainActivity;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    @BindView(R.id.et_username_field)
    protected TextInputEditText usernameEditText;
    @BindView(R.id.et_password_field)
    protected TextInputEditText passwordEditText;
    @BindView(R.id.et_password_repeat_field)
    protected TextInputEditText repeatPwEditText;
    @BindView(R.id.btn_login_user)
    protected Button loginUserBtn;
    @BindView(R.id.tv_register_new_user)
    protected TextView registerNewUserTextView;
    @BindView(R.id.tv_cancel_register)
    protected TextView cancelRegisteringTextView;
    @BindView(R.id.et_password_repeat_input_layout)
    protected View passwordInputLayout;
    @BindView(R.id.progress_bar)
    protected View progressBar;

    private ILoginPresenter presenter;
    private boolean registerMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setupPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.presenter.prepareLoginSystem();
    }

    @Override
    protected void onStop() {
        super.onStop();

        this.presenter.releaseLoginSystem();
    }

    @Override
    public void onBackPressed() {
        if (registerMode)
            presenter.changeMode();
        else
            super.onBackPressed();
    }

    @OnClick(R.id.btn_login_user)
    protected void loginUserAction() {
        changeViewState(true);
        final String username = this.usernameEditText.getText().toString();
        final String password = this.passwordEditText.getText().toString();

        presenter.loginUser(username, password);
    }

    @OnClick(R.id.tv_register_new_user)
    protected void registerNewUserAction() {
        Toast.makeText(this, "Register clicked", Toast.LENGTH_SHORT).show();

        this.presenter.changeMode();
    }

    @OnClick(R.id.tv_cancel_register)
    protected void cancelRegisterAction() {
        this.presenter.changeMode();
    }

    @Override
    public void onValidationError(LoginErrorTypes errorType, int errorRes) {
        changeViewState(false);

        switch (errorType) {
            case EMAIL:
                this.usernameEditText.setError(String.format(getResources()
                                .getString(R.string.activity_login_username_required),
                        getResources().getString(errorRes)));
                break;
            case PASSWORD:
                this.passwordEditText.setError(String.format(getResources()
                                .getString(R.string.activity_login_password_required),
                        getResources().getString(errorRes)));
                break;
            case REPASSWORD:
                this.repeatPwEditText.setError(String.format(getResources()
                                .getString(R.string.activity_login_repeat_password_required),
                        getResources().getString(errorRes)));
                break;
        }
    }

    @Override
    public void onLoginFailed(String resInfo) {
        changeViewState(false);
        Toast.makeText(this, resInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFailed(int resInfo) {
        changeViewState(false);
        Toast.makeText(this, resInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChangeMode(boolean isInRegisterMode) {
        this.registerMode = isInRegisterMode;

        if (isInRegisterMode) {
            passwordInputLayout.setVisibility(View.VISIBLE);
            registerNewUserTextView.setVisibility(View.GONE);
            cancelRegisteringTextView.setVisibility(View.VISIBLE);
            loginUserBtn.setText(R.string.activity_login_button_register_label);
        } else {
            passwordInputLayout.setVisibility(View.GONE);
            registerNewUserTextView.setVisibility(View.VISIBLE);
            cancelRegisteringTextView.setVisibility(View.GONE);
            loginUserBtn.setText(R.string.activity_login_button_login_label);
        }
    }

    @Override
    public String onRegistrationStarted() {
        return this.repeatPwEditText.getText().toString();
    }

    @Override
    public void onPasswordNotMatch() {
        changeViewState(false);
        passwordEditText.setError(getResources().getString(R.string.activity_login_different_password));
        repeatPwEditText.setError(getResources().getString(R.string.activity_login_different_password));
    }

    @Override
    public void onLoginSuccess() {
        changeViewState(false);
        navigateToMainActivity();
    }

    @Override
    public void onAuthenticated() {
        navigateToMainActivity();
    }

    private void setupPresenter() {
        this.presenter = new LoginPresenter(this);
    }

    private void changeViewState(boolean isInProgress) {
        this.progressBar.setVisibility(isInProgress ? View.VISIBLE : View.GONE);
        this.passwordEditText.setEnabled(!isInProgress);
        this.usernameEditText.setEnabled(!isInProgress);
        this.repeatPwEditText.setEnabled(!isInProgress);
        this.loginUserBtn.setEnabled(!isInProgress);
        this.cancelRegisteringTextView.setEnabled(!isInProgress);
    }

    private void navigateToMainActivity() {
        final Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
