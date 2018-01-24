package android.project.pnpc.fr.pnpc_android.navigation;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.project.pnpc.fr.pnpc_android.R;
import android.project.pnpc.fr.pnpc_android.location.Coordinate;
import android.project.pnpc.fr.pnpc_android.location.EstimoteService_;
import android.project.pnpc.fr.pnpc_android.location.LocationService;
import android.project.pnpc.fr.pnpc_android.location.LocationService_;
import android.project.pnpc.fr.pnpc_android.utils.network.GsonSingleton;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by stephen on 08/11/17.
 */


/**
 * Map navigation facade to manipulate a google map
 */
@EBean
public class MapNavigation implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener {

    /**
     * Tag used for Logger.
     */
    private static final String TAG = MapNavigation.class.getSimpleName();

    /**
     * Map view
     */
    GoogleMap mapView;

    @RootContext
    MapActivity context;

    /**
     * Gson instance use to map json to object
     * or to convert object to json.
     * Used for `intent` communication.
     */
    private Gson gson;

    public void init() {
        Log.d(TAG, "init");

        this.gson = GsonSingleton.getInstance();

        // Get Google Map and initialize it
        SupportMapFragment mapFragment = (SupportMapFragment) context.getSupportFragmentManager()
                .findFragmentById(R.id.map_navigation);
        mapFragment.getMapAsync(this);

        // Add observer broadcast to ask location settings id gps is disabled.
        LocalBroadcastManager.getInstance(context).registerReceiver(askLocationReceiver,
                new IntentFilter(LocationService.ASK_LOCATION_SETTINGS_BROADCAST));

        // Add observer broadcast messaging for location.
        LocalBroadcastManager.getInstance(context).registerReceiver(locationReceiver,
                new IntentFilter(LocationService.LOCATION_BROADCAST));

        LocationService_.intent(context).start();

        SystemRequirementsChecker.checkWithDefaultDialogs(context);
        EstimoteService_.intent(context).start();
    }

    /**
     * Method to stop observer broadcast messaging and service location.
     * Used when use leave application from session activity(onFinish`, `onDestroy`, `onStop`)
     */
    public void stop() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(locationReceiver);
        LocalBroadcastManager.getInstance(context).unregisterReceiver(askLocationReceiver);
        LocationService_.intent(context)
                .stop();
        EstimoteService_.intent(context).stop();
    }


    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        googleMap.setOnMapLongClickListener(this);
        googleMap.getUiSettings().setAllGesturesEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        Log.d(TAG, "onMapReady");
        mapView = googleMap;
    }

    /**
     * Handler for receive the ask location settings. This will be
     * called whenever an Intent with an action named `LocationService.ASK_LOCATION_SETTINGS`
     * is broadcasted.
     */
    private BroadcastReceiver askLocationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            showSettingsAlert();
        }
    };

    /**
     * Handler for receive location intents. This will be
     * called whenever an Intent with an action named `LocationService.LOCATION_BROADCAST`
     * is broadcasted.
     */
    private BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String jsonLocation = intent.getStringExtra(LocationService_.EXTRA_LOCATION);
            Coordinate coordinate = gson.fromJson(jsonLocation, Coordinate.class);
            Log.d(TAG, "Got location from service: " + coordinate);
            addCoordinate(coordinate);
        }
    };

    /**
     * Add a new point to the specific currentPolyline.
     *
     * @param coordinate coordinate we want to add.
     */
    private void addCoordinate(final Coordinate coordinate) {
        Log.e(TAG, "addCoordinate");
        if (coordinate == null)
            throw new AssertionError("coordinate should not be null");

        if (coordinate != null) {
            LatLng point = new LatLng(coordinate.getLatitude(), coordinate.getLongitude());
            mapView.addMarker(new MarkerOptions()
                    .position(point)
                    .title("Hello world"));
        }
    }

    /**
     * Function to show settings alert dialog.
     * On pressing the Settings button it will launch Settings Options.
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("GPS");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing the Settings button.
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                dialog.cancel();
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }



}
