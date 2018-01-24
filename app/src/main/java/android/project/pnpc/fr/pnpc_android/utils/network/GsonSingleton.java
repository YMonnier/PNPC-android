package android.project.pnpc.fr.pnpc_android.utils.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by stephen on 10/10/17.
 */

public class GsonSingleton {
    private static Gson gson;
    public final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static Gson getInstance() {
        if (gson == null) {
            return new GsonBuilder()
                    .setDateFormat(DATE_FORMAT)
                    .create();
        }
        if (gson == null)
            throw new AssertionError("The gson instance should noy be null");
        return gson;
    }
}
