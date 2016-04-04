package incredible619.notsofast;

/**
 * Created by deepak on 07-Oct-15.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class GeofenceActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Geofence> mGeofences;
    ArrayList<LatLng> mGeofenceCoordinates;
    ArrayList<Integer> mGeofenceRadius;
    private GeofenceStore mGeofenceStore;
    public static final String geofenceType = "incredible619.notsofast.GEOFENCETYPE";
    private Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_geofence);

        btnSignOut = (Button)findViewById(R.id.add_geofences_button);
        btnSignOut.setOnClickListener(this);

        mGeofences = new ArrayList<Geofence>();
        mGeofenceCoordinates = new ArrayList<LatLng>();
        mGeofenceRadius = new ArrayList<Integer>();
        mGeofenceCoordinates.add(new LatLng(28.5444498, 77.2726199));
        mGeofenceRadius.add(1000);

        // IIITD Academic Building
        mGeofences.add(new Geofence.Builder()
                .setRequestId("IIITD Academic Building")
                        // The coordinates of the center of the geofence and the radius in meters.
                .setCircularRegion(mGeofenceCoordinates.get(0).latitude, mGeofenceCoordinates.get(0).longitude, mGeofenceRadius.get(0).intValue())
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                        // Required when we use the transition type of GEOFENCE_TRANSITION_DWELL
                        //.setLoiteringDelay(10000)
                .setTransitionTypes(
                        Geofence.GEOFENCE_TRANSITION_ENTER
                                //| Geofence.GEOFENCE_TRANSITION_DWELL
                                | Geofence.GEOFENCE_TRANSITION_EXIT).build());

        // Add the geofences to the GeofenceStore object.
        mGeofenceStore = new GeofenceStore(this, mGeofences);
    }

    @Override
    public void onClick(View v) {
        MainActivity.is_Signed_in=false;
        Intent intent = new Intent(GeofenceActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
