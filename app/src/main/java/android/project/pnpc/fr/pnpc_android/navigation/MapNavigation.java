package android.project.pnpc.fr.pnpc_android.navigation;

import android.project.pnpc.fr.pnpc_android.R;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by stephen on 08/11/17.
 */


/**
 * Map navigation facade to manipulate a google map
 */
@EBean
public class MapNavigation  implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener{

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

    public void init(){
        Log.d(TAG, "init");
        // Get Google Map and initialize it
        SupportMapFragment mapFragment = (SupportMapFragment) context.getSupportFragmentManager()
                .findFragmentById(R.id.map_navigation);
        mapFragment.getMapAsync(this);
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
}
