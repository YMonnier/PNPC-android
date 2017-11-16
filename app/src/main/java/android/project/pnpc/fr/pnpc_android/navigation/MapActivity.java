package android.project.pnpc.fr.pnpc_android.navigation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.project.pnpc.fr.pnpc_android.R;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

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

    @AfterViews
    public void init() {
        mapNavigation.init();
    }
}
