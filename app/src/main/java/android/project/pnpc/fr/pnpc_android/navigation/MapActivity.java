package android.project.pnpc.fr.pnpc_android.navigation;

import android.project.pnpc.fr.pnpc_android.R;
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
     * Creating facade for
     * the map navigation manipulation.
     */
    @Bean(MapNavigation.class)
    MapNavigation mapNavigation;

    @AfterViews
    public void init(){
        mapNavigation.init();
    }

}
