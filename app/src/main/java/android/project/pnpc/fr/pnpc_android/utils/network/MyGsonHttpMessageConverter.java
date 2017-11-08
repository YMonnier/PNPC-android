package android.project.pnpc.fr.pnpc_android.utils.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by stephen on 10/10/17.
 */

class MyGsonHttpMessageConverter extends GsonHttpMessageConverter {

    /**
     * Override MediaType pour GsonHttpMessageConverter
     */
    public MyGsonHttpMessageConverter() {
        List<MediaType> types = Arrays.asList(
                new MediaType("text", "html", DEFAULT_CHARSET),
                new MediaType("application", "json", DEFAULT_CHARSET),
                new MediaType("application", "*+json", DEFAULT_CHARSET)
        );

        Gson customGson = new GsonBuilder()
                .setDateFormat(GsonSingleton.DATE_FORMAT)
                .create();

        super.setGson(customGson);
        super.setSupportedMediaTypes(types);
    }
}
