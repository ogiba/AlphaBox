package pl.alphabox.Scenes.Login;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
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
    @BindView(R.id.btn_login_user)
    protected Button loginUserBtn;
    @BindView(R.id.tv_register_new_user)
    protected TextView registerNewUserTextView;

    private ILoginPresenter presenter;

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

    @OnClick(R.id.btn_login_user)
    protected void loginUserAction() {
        final String username = this.usernameEditText.getText().toString();
        final String password = this.passwordEditText.getText().toString();

        if (!username.trim().equals("") && !password.trim().equals(""))
            this.presenter.loginUser(username, password);
        else if (username.trim().equals("")) {
            this.usernameEditText.setError(getResources()
                    .getString(R.string.activity_login_username_required));
        } else if (password.trim().equals("")) {
            this.passwordEditText.setError(getResources()
                    .getString(R.string.activity_login_password_required));
        }
    }

    @OnClick(R.id.tv_register_new_user)
    protected void registerNewUserAction() {
        Toast.makeText(this, "Register clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFailed(String resInfo) {
        Toast.makeText(this, resInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFailed(int resInfo) {
        Toast.makeText(this, resInfo, Toast.LENGTH_SHORT).show();
    }

    private void setupPresenter() {
        this.presenter = new LoginPresenter(this);
    }
}
