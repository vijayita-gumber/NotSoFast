package incredible619.notsofast;

/**
 * Created by deepak on 07-Oct-15.
 */

import java.util.List;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class GeofenceIntentService extends IntentService {

    public static String geofenceType;
    private final String TAG = this.getClass().getCanonicalName();

    public GeofenceIntentService() {
        super("GeofenceIntentService");
        Log.v(TAG, "Constructor.");
    }

    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "onCreate");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        Log.v(TAG, "onHandleIntent");

        if(!geofencingEvent.hasError()) {
            int transition = geofencingEvent.getGeofenceTransition();
            String notificationTitle;

            switch(transition) {
                case Geofence.GEOFENCE_TRANSITION_ENTER:
                    notificationTitle = "Geofence Entered";
                    Log.v(TAG, "Geofence Entered");
                    Toast.makeText(GeofenceIntentService.this, "Welcome to IIIT Delhi", Toast.LENGTH_SHORT).show();
                    Toast.makeText(GeofenceIntentService.this, "Maintain the Speed Limit", Toast.LENGTH_SHORT).show();
                    break;

                case Geofence.GEOFENCE_TRANSITION_EXIT:
                    notificationTitle = "Geofence Exit";
                    Log.v(TAG, "Geofence Exited");
                    Toast.makeText(GeofenceIntentService.this, notificationTitle, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    notificationTitle = "Geofence Unknown";
            }

            //sendNotification(this, getTriggeringGeofences(intent), notificationTitle);
            Intent accelerometer = new Intent(GeofenceIntentService.this, AccelerometerActivity.class);
            accelerometer.putExtra(GeofenceActivity.geofenceType, notificationTitle);
            accelerometer.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(accelerometer);
        }
    }

    private void sendNotification(Context context, String notificationText,
                                  String notificationTitle) {
        Toast.makeText(GeofenceIntentService.this, notificationText, Toast.LENGTH_SHORT).show();

        PowerManager pm = (PowerManager) context
                .getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK, "");
        wakeLock.acquire();


    }

    private String getTriggeringGeofences(Intent intent) {
        GeofencingEvent geofenceEvent = GeofencingEvent.fromIntent(intent);
        List<Geofence> geofences = geofenceEvent
                .getTriggeringGeofences();

        String[] geofenceIds = new String[geofences.size()];

        for (int i = 0; i < geofences.size(); i++) {
            geofenceIds[i] = geofences.get(i).getRequestId();
        }

        return TextUtils.join(", ", geofenceIds);
    }
}
