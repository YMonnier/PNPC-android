package android.project.pnpc.fr.pnpc_android.location;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.project.pnpc.fr.pnpc_android.R;
import android.project.pnpc.fr.pnpc_android.model.User;
import android.project.pnpc.fr.pnpc_android.navigation.MapActivity_;
import android.project.pnpc.fr.pnpc_android.utils.Settings;
import android.project.pnpc.fr.pnpc_android.utils.network.GsonSingleton;
import android.project.pnpc.fr.pnpc_android.utils.network.RestApi;
import android.project.pnpc.fr.pnpc_android.utils.view.Snack;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.observation.region.RegionUtils;
import com.estimote.coresdk.observation.utils.Proximity;
import com.estimote.coresdk.recognition.packets.EstimoteLocation;
import com.estimote.coresdk.service.BeaconManager;
import com.google.gson.JsonObject;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EService;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stephen on 21/01/18.
 */

@EService
public class EstimoteService extends Service {

    /**
     * Tag used for Logger.
     */
    private static final String TAG = EstimoteService.class.getSimpleName();

    /**
     * Rest service to get
     * information from server.
     */
    @RestService
    RestApi tcRestApi;

    private String estimoteAppId = "pnpc-hiking-dzz";

    private String estimoteToken = "960e0687ed90bfb248b4bc9c0ed268f5";

    private String pinkBeacon = "[48c582894196ab5a855700a0d356e902]";

    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    /**
     * Start the Estimote Service.
     * Add the Location Update for Network and GPS Provider.
     * If the the application does not follows permission, we
     * ask the user to change it.
     * @param intent
     * @param startId
     */
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "onStart");

        //SystemRequirementsChecker.checkWithDefaultDialogs(this);
        EstimoteSDK.initialize(getApplicationContext(), estimoteAppId, estimoteToken);
        run();
    }

    private void run() {
        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override public void onServiceReady() {
                beaconManager.startLocationDiscovery();
            }
        });
        beaconManager.setLocationListener(new BeaconManager.LocationListener() {
            @Override
            public void onLocationsFound(List<EstimoteLocation> beacons) {
                for (EstimoteLocation beacon : beacons) {
                    Log.d("Test", "id : " + beacon.id.toString() + " : " + (pinkBeacon) + " ?");
                    boolean test = RegionUtils.computeProximity(beacon) == Proximity.NEAR;
                    Log.d("Test", "proximity : " + test + " ?");
                    if (beacon.id.toString().equals(pinkBeacon)){
                        Log.e(TAG, "Detected !");
                        String id = beacon.id.toString();
                        String beaconId = id.substring(1, id.length()-1);
                        savePassage(beaconId);
                    }
                }
            }
        });
    }

    @Background
    public void savePassage(String beaconId) {
        try {
            tcRestApi.setHeader(Settings.AUTHORIZATION_HEADER_NAME, Settings.user.getAuthToken());

            ResponseEntity<JsonObject> responseLogin = tcRestApi.createPassage(Settings.user.getId(), beaconId);

            if (responseLogin == null) {
                throw new AssertionError("response login should not be null");
            }

            if (responseLogin != null) {
                if (responseLogin.getStatusCode().is2xxSuccessful()) {
                    JsonObject json = responseLogin.getBody();
                    Log.e(TAG, "Json passage : " + json);
                }
            } else {
                //Snack.showFailureMessage(linearLayout, getString(R.string.error_request_4xx_5xx_status), Snackbar.LENGTH_LONG);
            }
        } catch (RestClientException e) {
            String error = e.getLocalizedMessage();
            Log.d(TAG, "error HTTP request from savePassage: " + error);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        beaconManager.disconnect();
    }
}
