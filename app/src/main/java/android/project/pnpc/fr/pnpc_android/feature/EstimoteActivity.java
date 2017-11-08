package android.project.pnpc.fr.pnpc_android.feature;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.project.pnpc.fr.pnpc_android.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.RegionUtils;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.observation.utils.Proximity;
import com.estimote.coresdk.recognition.packets.EstimoteLocation;
import com.estimote.coresdk.service.BeaconManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.UUID;

@EActivity(R.layout.activity_estimote)
public class EstimoteActivity extends AppCompatActivity {

    @ViewById
    TextView ok;

    private String estimoteAppId = "pnpc-hiking-dzz";

    private String estimoteToken = "960e0687ed90bfb248b4bc9c0ed268f5";

    private String pinkBeacon = "[48c582894196ab5a855700a0d356e902]";

    private BeaconManager beaconManager;

    @AfterViews
    public void init(){
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
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
                        ok.setText("detected !!!!!");
                    }
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.disconnect();
    }

}
