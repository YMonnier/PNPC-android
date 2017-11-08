package android.project.pnpc.fr.pnpc_android.feature;

import android.project.pnpc.fr.pnpc_android.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.service.BeaconManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.UUID;

@EActivity(R.layout.activity_estimote)
public class EstimoteActivity extends AppCompatActivity {

    private BeaconManager beaconManager;

    @AfterViews
    public void init(){
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        beaconManager = new BeaconManager(getApplicationContext());
        // add this below:
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new BeaconRegion(
                        "monitored region",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                        22504, 48827));
            }
        });
    }

}
