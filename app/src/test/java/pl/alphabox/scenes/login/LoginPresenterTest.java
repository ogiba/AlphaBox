package pl.alphabox.scenes.login;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import static org.mockito.Mockito.when;

/**
 * Created by Daniel Blokus on 16.01.18.
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})
public class LoginPresenterTest {

    private static final String VALID_USER_NAME = "ADMIN";
    private static final String VALID_PASSWORD = "ADMIN";

    @Mock
    private ILoginView loginView;

    @Mock
    private LoginPresenter loginPresenter;

    private DatabaseReference mockedDatabaseReference;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);

        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);

        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);

        loginPresenter = new LoginPresenter(loginView);
    }

    @Test
    public void registerNewUser_WithValidCredentials() {

    }

}