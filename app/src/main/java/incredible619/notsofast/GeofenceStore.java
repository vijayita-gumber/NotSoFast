package incredible619.notsofast;

/**
 * Created by deepak on 07-Oct-15.
 */

import java.util.ArrayList;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class GeofenceStore implements ConnectionCallbacks,
        OnConnectionFailedListener, ResultCallback<Status>, LocationListener {

    private final String TAG = this.getClass().getSimpleName();

    /*private TextView mLatitudeText;
    private TextView mLongitudeText;
    private TextView mProviderText;*/

    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private PendingIntent mPendingIntent;
    private ArrayList<Geofence> mGeofences;
    GeofencingRequest mGeofencingRequest;
    private LocationRequest mLocationRequest;

    public GeofenceStore(Context context, ArrayList<Geofence> geofences) {
        /*mLatitudeText = (TextView) findViewById((R.id.latitude_text));
        mLongitudeText = (TextView) findViewById((R.id.longitude_text));
        mProviderText = (TextView) findViewById(R.id.provider_text);*/

        mContext = context;
        mGeofences = new ArrayList<Geofence>(geofences);
        mPendingIntent = null;

        // Build a new GoogleApiClient, specify that we want to use LocationServices
        // by adding the API to the client, specify the connection callbacks are in
        // this class as well as the OnConnectionFailed method.
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();

        mLocationRequest = new LocationRequest();
        // We want a location update every 30 seconds.
        mLocationRequest.setInterval(30000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mGoogleApiClient.connect();
    }

    public void disconnect() {
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onResult(Status result) {
        if (result.isSuccess()) {
            Log.v(TAG, "Success!");
        } else if (result.hasResolution()) {

        } else if (result.isCanceled()) {
            Log.v(TAG, "Canceled");
        } else if (result.isInterrupted()) {
            Log.v(TAG, "Interrupted");
        } else {

        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.v(TAG, "Connection failed.");
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        // We're connected, now we need to create a GeofencingRequest with
        // the geofences we have stored.
        mGeofencingRequest = new GeofencingRequest.Builder().addGeofences(
                mGeofences).build();

        mPendingIntent = createRequestPendingIntent();

        // This is for debugging only and does not affect
        // geofencing.
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

        // Submitting the request to monitor geofences.
        PendingResult<Status> pendingResult = LocationServices.GeofencingApi
                .addGeofences(mGoogleApiClient, mGeofencingRequest,
                        mPendingIntent);

        // Set the result callbacks listener to this class.
        pendingResult.setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.v(TAG, "Connection suspended.");
    }

    /**
     * This creates a PendingIntent that is to be fired when geofence transitions
     * take place. In this instance, we are using an IntentService to handle the
     * transitions.
     *
     * @return A PendingIntent that will handle geofence transitions.
     */
    private PendingIntent createRequestPendingIntent() {
        if (mPendingIntent == null) {
            Log.v(TAG, "Creating PendingIntent");
            Intent intent = new Intent(mContext, GeofenceIntentService.class);
            mPendingIntent = PendingIntent.getService(mContext, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }
        return mPendingIntent;
    }

    @Override
    public void onLocationChanged(Location location) {

        /*mLatitudeText.setText(String.valueOf(location.getLatitude()));
        mLongitudeText.setText(String.valueOf(location.getLongitude()));
        mProviderText.setText(String.valueOf(location.getProvider()));*/

        Log.v(TAG, "Location Information\n"
                + "==========\n"
                + "Provider:\t" + location.getProvider() + "\n"
                + "Lat & Long:\t" + location.getLatitude() + ", "
                + location.getLongitude() + "\n"
                + "Altitude:\t" + location.getAltitude() + "\n"
                + "Bearing:\t" + location.getBearing() + "\n"
                + "Speed:\t\t" + location.getSpeed() + "\n"
                + "Accuracy:\t" + location.getAccuracy() + "\n");
    }
}

