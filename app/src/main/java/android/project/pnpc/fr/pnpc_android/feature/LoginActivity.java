package android.project.pnpc.fr.pnpc_android.feature;

import android.project.pnpc.fr.pnpc_android.R;
import android.project.pnpc.fr.pnpc_android.utils.EmailValidator;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

@EActivity(R.layout.login_activity)
public class LoginActivity extends AppCompatActivity {

    /**
     * Tag used for Logger.
     */
    private static final String TAG = LoginActivity.class.getSimpleName();

    /**
     * Minimum password length
     */
    public static final int MIN_PASSWORD_LENGTH = 1;

    /**
     * Constant which correspond to POST email Auth parameter.
     */
    private static final String PARAMS_AUTH_EMAIL = "email";

    /**
     * Constant which correspond to POST password Auth parameter.
     */
    private static final String PARAMS_AUTH_PASSWORD = "password";

    /**
     * Input email used to authenticate the user.
     */
    @ViewById(R.id.emailField)
    EditText emailView;

    /**
     * Input password used to authenticate the user.
     */
    @ViewById(R.id.passwordField)
    EditText passwordView;

    /**
     * Button action to login to the API.
     */
    @ViewById(R.id.loginButton)
    Button loginButton;

    @ViewById(R.id.linearLayout)
    LinearLayout linearLayout;

    /**
     * Rest service to get
     * information from server.
     */
    //@RestService
    //RestApi tcRestApi;

    /**
     * Progress Dialog
     */
    //private LoaderDialog progressView;


    @AfterViews
    public void init() {
        //progressView = new LoaderDialog(getContext(), getString(R.string.authenticating));
    }

    /**
     * Sign in button action.
     */
    @Click(R.id.loginButton)
    void onClickOnSigninButton() {
        attemptLogin();
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        emailView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            updateErrorUi(passwordView, getString(R.string.error_field_required));
            focusView = passwordView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            updateErrorUi(passwordView, getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            updateErrorUi(emailView, getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            updateErrorUi(emailView, getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }

        if (cancel) {
            assert focusView != null;
            if (focusView != null)
                focusView.requestFocus();
        } else {
            //progressView.show();

            userLoginTask(email, password);
        }
    }

    /**
     * Update the current view by settings error
     * message to the input view.
     *
     * @param view  input view
     * @param error error message
     */
    @UiThread
    void updateErrorUi(final EditText view, final String error) {
        view.setError(error);
    }

    /**
     * Update clickable button depending on a status
     *
     * @param status true if we want to
     *               disable all clickable buttons, otherwise, false
     */
    @UiThread
    void updateLockUi(boolean status) {
        passwordView.setEnabled(!status);
        emailView.setEnabled(!status);
        loginButton.setEnabled(!status);
    }

    /**
     * Check if the email is valid.
     * That is to say, check if the pattern is valid: mail@mail.com
     *
     * @param email string email
     * @return true if valid, otherwise, false
     */
    private boolean isEmailValid(final String email) {
        return EmailValidator.validate(email);
    }

    /**
     * Check the password length
     *
     * @param password password for validation
     * @return true if password length >= 8, otherwise, false
     */
    private boolean isPasswordValid(final String password) {
        return password.length() >= MIN_PASSWORD_LENGTH;
    }


    /**
     * Background task which allows
     * to send credential data to the server.
     * If the connexion is succeed, we go to the sessions list.
     * Otherwise, the user put wrong data and should try again...
     *
     * @param email    user email
     * @param password password email
     */
    @Background
    void userLoginTask(final String email, final String password) {
        updateLockUi(true);

        Map<String, Object> auth = new HashMap<>();

        auth.put(PARAMS_AUTH_EMAIL, email);
        auth.put(PARAMS_AUTH_PASSWORD, password);
    }

}

