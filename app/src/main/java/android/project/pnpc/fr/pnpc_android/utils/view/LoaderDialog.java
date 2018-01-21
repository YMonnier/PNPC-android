package android.project.pnpc.fr.pnpc_android.utils.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.project.pnpc.fr.pnpc_android.R;
import android.support.annotation.UiThread;

/**
 * Created by stephen on 21/01/18.
 */

public class LoaderDialog {

    private ProgressDialog progressView;

    /**
     * Create a new `ProgressDialog`with a specific theme and message.
     *
     * @param context target view
     * @param message message to display
     */
    public LoaderDialog(Context context, String message) {
        progressView = new ProgressDialog(context);

        if (progressView == null)
            throw new AssertionError("progress view should not be null");

        if (progressView != null) {
            progressView.setIndeterminate(true);
            progressView.setMessage(message);
        }
    }

    /**
     * Show the current progress view on background ui thread
     */
    @UiThread
    public void show() {
        if (progressView != null)
            progressView.show();
    }

    /**
     * hide the current progress view
     */
    @UiThread
    public void dismiss() {
        if (progressView != null)
            progressView.dismiss();
    }

}
