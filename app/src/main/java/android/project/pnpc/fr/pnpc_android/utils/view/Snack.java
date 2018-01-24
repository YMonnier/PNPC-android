package android.project.pnpc.fr.pnpc_android.utils.view;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by stephen on 21/01/18.
 */

public class Snack {

    /**
     * Create a snackbar view to show a succesfull message.
     * @param view target view
     * @param message message to display
     * @param duration duration constant
     */
    public static void showSuccessfulMessage(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    /**
     * CReate a snackbar view to show a failure message.
     * @param view target view
     * @param message message to display
     * @param duration duration constant
     */
    public static void showFailureMessage(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

}
