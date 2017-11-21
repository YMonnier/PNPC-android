package android.project.pnpc.fr.pnpc_android.navigation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.project.pnpc.fr.pnpc_android.R;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

/**
 * Created by stephen on 08/11/17.
 */

@EActivity(R.layout.map_activity)
public class MapActivity extends AppCompatActivity {

    /**
     * Tag used for Logger.
     */
    private static final String TAG = MapActivity.class.getSimpleName();

    /**
     * Creating facade for
     * the map navigation manipulation.
     */
    @Bean(MapNavigation.class)
    MapNavigation mapNavigation;

    /**
     * Check permission
     */
    private static final int REQUEST_ACCESS_COARSE_LOCATION_PERMISSION = 99;
    private static final int REQUEST_ACCESS_FINE_LOCATION_PERMISSION = 100;
    private boolean permissionToGeolocationAccepted = false;
    private String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};


    @AfterViews
    public void init() {
        mapNavigation.init();
        if(!(this.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED))
            ActivityCompat.requestPermissions(this, permissions, REQUEST_ACCESS_FINE_LOCATION_PERMISSION);

        if (!(this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED))
            ActivityCompat.requestPermissions(this, permissions, REQUEST_ACCESS_COARSE_LOCATION_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_ACCESS_COARSE_LOCATION_PERMISSION:
                permissionToGeolocationAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
            case REQUEST_ACCESS_FINE_LOCATION_PERMISSION:
                permissionToGeolocationAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToGeolocationAccepted ) finish();
    }
}
