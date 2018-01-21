package android.project.pnpc.fr.pnpc_android.utils.network;

import com.google.gson.JsonObject;

import org.androidannotations.rest.spring.annotations.Accept;
import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Header;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.RequiresHeader;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * Created by stephen on 10/10/17.
 */

@Rest(rootUrl = "https://api-smys.herokuapp.com/", converters = {MyGsonHttpMessageConverter.class})

@Accept(MediaType.APPLICATION_JSON)
public interface RestApi {

    void setHeader(String name, String value);

    String getHeader(String name);
    
    @Post("/users/login")
    @Header(name = "Content-Type", value = "application/json")
    ResponseEntity<JsonObject> login(@Body Map<String, Object> formData);

    @Post("/users/{user_id}/passages/{waypoint_id}")
    @Header(name = "Content-Type", value = "application/json")
    ResponseEntity<JsonObject> createPassage(@Path long user_id, @Path String waypoint_id);

}
