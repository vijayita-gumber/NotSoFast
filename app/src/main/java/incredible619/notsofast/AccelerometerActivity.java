package incredible619.notsofast;

/**
 * Created by deepak on 07-Oct-15.
 */

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class AccelerometerActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private TextView currentX, currentY, currentZ;

    private float lastX, lastY, lastZ;
    private float deltaX = 0;
    private float deltaY = 0;
    private float deltaZ = 0;

    private static int windowCount = 1;
    private double tempAcceleration = 0.0;
    private double avgAcceleration = 0.0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        Intent intent = getIntent();
        String getGeofenceType = intent.getStringExtra(GeofenceActivity.geofenceType);
        Toast.makeText(AccelerometerActivity.this, getGeofenceType, Toast.LENGTH_SHORT).show();
        if(getGeofenceType.equals("Geofence Entered"))
        {
            initializeViews();
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            if (sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null)
            {
                accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                sensorManager.registerListener(this, accelerometer, 1000000);
            }
            else
            {
                Toast.makeText(this, "Accelerometer Not Supported", Toast.LENGTH_LONG).show();
            }
        }
        else
            finish();
    }

    public void initializeViews()
    {
        currentX = (TextView) findViewById(R.id.currentX);
        currentY = (TextView) findViewById(R.id.currentY);
        currentZ = (TextView) findViewById(R.id.currentZ);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, 1000000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        displayCleanValues();
        displayCurrentValues();

        deltaX = Math.abs(lastX - event.values[0]);
        deltaY = Math.abs(lastY - event.values[1]);
        deltaZ = Math.abs(lastZ - event.values[2]);

        //Distinguish noise from considerable change in acceleration
        if (deltaX < 0.0001)
            deltaX = 0;
        if (deltaY < 0.0001)
            deltaY = 0;
        if ((deltaZ <0.0001))
            deltaZ = 0;

        if(windowCount > 30)
        {
            Toast.makeText(this, "Average acceleration: " + avgAcceleration / 30, Toast.LENGTH_SHORT).show();
            if(avgAcceleration/30 >= 2.0)
                sendMail();
            avgAcceleration=0;
            windowCount=0;
        }
        else
        {
            tempAcceleration = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaZ, 2));
            avgAcceleration += tempAcceleration;
            windowCount++;
        }

        lastX = event.values[0];
        lastY = event.values[1];
        lastZ = event.values[2];
    }

    private void sendMail()
    {
        Toast.makeText(this, "Email is now being sent....", Toast.LENGTH_SHORT).show();

        Intent gmailIntent = new Intent();
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);


        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{MainActivity.account});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Email from phone.");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Acceleration exceeded 2 m/s^2.");

        try {

            AccelerometerActivity.this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

        } catch(ActivityNotFoundException ex) {
            Toast.makeText(AccelerometerActivity.this, "No Email Clients Available", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayCleanValues() {
        currentX.setText("0.0");
        currentY.setText("0.0");
        currentZ.setText("0.0");
    }

    private void displayCurrentValues() {
        currentX.setText(Float.toString(deltaX));
        currentY.setText(Float.toString(deltaY));
        currentZ.setText(Float.toString(deltaZ));
    }
}
