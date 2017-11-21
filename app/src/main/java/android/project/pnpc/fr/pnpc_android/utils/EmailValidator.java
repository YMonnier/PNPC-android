package android.project.pnpc.fr.pnpc_android.utils;

import java.util.regex.Pattern;

/**
 * Created by stephen on 08/11/17.
 */

public  class EmailValidator {
    /**
     * Regular expression to test an email.
     */
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Validate email with regular expression
     *
     * @param email email for validation
     * @return true valid email, otherwise, false
     */
    public static boolean validate(final String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(email).matches();
    }
}
