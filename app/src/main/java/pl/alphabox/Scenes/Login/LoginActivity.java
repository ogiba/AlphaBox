package pl.alphabox.Scenes.Login;

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
        final String username = this.usernameEditText.getText().toString();
        final String password = this.passwordEditText.getText().toString();

        if (!username.trim().equals("") && !password.trim().equals("") && !registerMode)
            this.presenter.loginUser(username, password);
        else if (username.trim().equals("")) {
            this.usernameEditText.setError(getResources()
                    .getString(R.string.activity_login_username_required));
        } else if (password.trim().equals("")) {
            this.passwordEditText.setError(getResources()
                    .getString(R.string.activity_login_password_required));
        } else if (registerMode) {
            final String repeatedPw = this.repeatPwEditText.getText().toString();

            if (repeatedPw.trim().equals("")) {
                this.repeatPwEditText.setError("Test");
            } else {
                presenter.registerNewUser(username, password, repeatedPw);
            }
        }
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
    public void onLoginFailed(String resInfo) {
        Toast.makeText(this, resInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFailed(int resInfo) {
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
    public void onPasswordNotMatch() {
        passwordEditText.setError("Password not matches");
        repeatPwEditText.setError("Password not matches");
    }

    private void setupPresenter() {
        this.presenter = new LoginPresenter(this);
    }
}
