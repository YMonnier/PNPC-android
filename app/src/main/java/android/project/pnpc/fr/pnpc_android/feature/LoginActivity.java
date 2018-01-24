package android.project.pnpc.fr.pnpc_android.feature;

import android.content.Intent;
import android.project.pnpc.fr.pnpc_android.R;
import android.project.pnpc.fr.pnpc_android.model.User;
import android.project.pnpc.fr.pnpc_android.navigation.MapActivity_;
import android.project.pnpc.fr.pnpc_android.utils.EmailValidator;
import android.project.pnpc.fr.pnpc_android.utils.Settings;
import android.project.pnpc.fr.pnpc_android.utils.network.GsonSingleton;
import android.project.pnpc.fr.pnpc_android.utils.network.RestApi;
import android.project.pnpc.fr.pnpc_android.utils.view.LoaderDialog;
import android.project.pnpc.fr.pnpc_android.utils.view.Snack;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.JsonObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

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
    private static final String PARAMS_AUTH_NICKNAME = "nickname";

    /**
     * Constant which correspond to POST password Auth parameter.
     */
    private static final String PARAMS_AUTH_PASSWORD = "mdp";

    /**
     * Input email used to authenticate the user.
     */
    @ViewById(R.id.nicknameField)
    EditText nicknameField;

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
    @RestService
    RestApi tcRestApi;

    /**
     * Progress Dialog
     */
    private LoaderDialog progressView;


    @AfterViews
    public void init() {
        progressView = new LoaderDialog(this, getString(R.string.authenticating));
        nicknameField.setText("stephenbellanger");
        passwordView.setText("supermotdepasse");

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
        nicknameField.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String nickname = nicknameField.getText().toString();
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
        if (TextUtils.isEmpty(nickname)) {
            updateErrorUi(nicknameField, getString(R.string.error_field_required));
            focusView = nicknameField;
            cancel = true;
        }

        if (cancel) {
            assert focusView != null;
            if (focusView != null)
                focusView.requestFocus();
        } else {
            progressView.show();
            userLoginTask(nickname, password);
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
        nicknameField.setEnabled(!status);
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
     * @param nickname    user nickname
     * @param password password email
     */
    @Background
    void userLoginTask(final String nickname, final String password) {
        updateLockUi(true);

        Map<String, Object> auth = new HashMap<>();

        auth.put(PARAMS_AUTH_NICKNAME, nickname);
        auth.put(PARAMS_AUTH_PASSWORD, password);

        //startActivity(new Intent(this, MapActivity_.class));

        try {
            ResponseEntity<JsonObject> responseLogin = tcRestApi.login(auth);
            Log.e(TAG, "response login: " + responseLogin);

            if (responseLogin == null) {
                throw new AssertionError("response login should not be null");
            }

            if (responseLogin != null) {
                if (responseLogin.getStatusCode().is2xxSuccessful()) {
                    JsonObject json = responseLogin.getBody();

                    Settings.user = GsonSingleton.getInstance().fromJson(json, User.class);

                    Log.e(TAG, "Token : " + Settings.user.getAuthToken());

                    // Get current user information
                    tcRestApi.setHeader(Settings.AUTHORIZATION_HEADER_NAME, Settings.user.getAuthToken());

                    //Start Map activity
                    startActivity(new Intent(this, MapActivity_.class));

                    updateLockUi(false);
                }
            } else {
                Snack.showFailureMessage(linearLayout, getString(R.string.error_request_4xx_5xx_status), Snackbar.LENGTH_LONG);
            }
            progressView.dismiss();
        } catch (RestClientException e) {
            String error = e.getLocalizedMessage();
            Log.d(TAG, "error HTTP request from userLoginTask: " + error);
            updateLockUi(false);
            progressView.dismiss();
        }
    }

}

