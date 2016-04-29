package incredible619.notsofast;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.Gmobile;
import com.example.android.GmobileSvc;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.RestAdapter;

public class MapsActivity extends FragmentActivity implements SensorEventListener,LocationListener {
    Context context;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    Sensor accelerometer ;
    SensorManager sm ;
    LocationManager locationManager;

    double latitudeValue , longitudeValue,time ;
    DatabaseHelper myDb;

    ArrayList<Gmobile> l = new ArrayList<Gmobile>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        myDb = new DatabaseHelper(this);
        setUpMapIfNeeded();
    }

    public void startButton(View v)
    {
        sm.registerListener(this, accelerometer, 250000);

        checkPermission();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        Toast.makeText(this, "Accelerometer Started", Toast.LENGTH_SHORT).show();
    }

    public void stopButton(View v)
    {
        sm.unregisterListener(this);
        checkPermission();

        if(locationManager != null){
            locationManager.removeUpdates(this);
            locationManager = null ;
            }
        Toast.makeText(this , "Accelerometer Stopped", Toast.LENGTH_SHORT).show();
        new SendAsync().execute();
    }

    public void showButton(View v)
    {
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0)
        {
            ShowMessage("Error" , "Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext())
        {
            buffer.append("X Axis: " + res.getString(0) + "\n");
            buffer.append("Y Axis: " + res.getString(1) + "\n");
            buffer.append("Z Axis: " + res.getString(2) + "\n");
            buffer.append("Latitude: " + res.getString(3) + "\n");
            buffer.append("Longitude: " + res.getString(4) + "\n\n");
        }
        ShowMessage("Data", buffer.toString());
    }

    public void checkPermission()
    {
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Log.v("err" , "err");
            return  ;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
       Log.v("Values", "X: " + event.values[0] + "  Y: " + event.values[1] + "  Z: " + event.values[2] + "  Latitude: " + latitudeValue + "  Longitude: " + longitudeValue);
        boolean isInserted = myDb.insertData(event.values[0],event.values[1],event.values[2],latitudeValue,longitudeValue);

        if (isInserted == true)
        {
            Log.v("Inserted", "Inserted");
            // Toast.makeText(MapsActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
        }

        else
        {
            Log.v("NotInserted", "NotInserted");
            //  Toast.makeText(MapsActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {    }

    @Override
    public void onLocationChanged(Location location)
    {
        String msg = "New Latitude: " + location.getLatitude()+"\nNew Longitude: "+ location.getLongitude()+"\n Time:"+location.getTime();
       LatLng latLngg = new LatLng(location.getLatitude(),location.getLongitude());


        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();

        latitudeValue = location.getLatitude();
        longitudeValue= location.getLongitude();
        time =location.getTime();
        //altitude =location.getAltitude();

        //speed = location.getSpeed();
       mMap.addMarker(new MarkerOptions().position(latLngg))
       .setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_map_marker_yellow));
       mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngg));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        Log.v("Location", msg);

        // we will now start adding code for the addition of path to the map.
        // we have the destination path with name latLng.

        source = latLngg;
        if(destination!=null){
            String url = getDirectionsUrl(source, destination);

            DownloadTask downloadTask = new DownloadTask();

            downloadTask.execute(url);
        }

    }

    @Override
    public void onProviderDisabled(String provider)
    {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider)
    {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
        Log.d("Latitude","status");
    }

    LatLng source;
    LatLng destination;
    public void onSearch(View view) throws IOException
    {
        mMap.clear();
        EditText location_tf =(EditText)findViewById(R.id.TFaddress);
        String location = location_tf.getText().toString();
        List<Address> addressList = null;
        if(location != null || !location.equals(""))
        {
            Geocoder geocoder = new Geocoder(this);
            Log.v("geocoder", String.valueOf(geocoder.getFromLocationName(location, 1)));

            try{
                addressList =geocoder.getFromLocationName(location ,1);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

            Address addresss = addressList.get(0);
            //LatLng hcmus = new LatLng(10.762984,106.682329);
            //LatLng dhsg = new LatLng(10.759677,106.682387);

            LatLng latLng =  new LatLng(addresss.getLatitude(), addresss.getLongitude());
            destination = latLng;
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
           /* while (addressList != null)
            {
                mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.login1)));
            }*/


            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            // we will now start adding code for the addition of path to the map.
            // we have the destination path with name latLng.


        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
       // mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.v("err", "err");
            return  ;
        }
        mMap.setMyLocationEnabled(true);
    }

    public void ShowMessage(String title , String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    String LOCATION_SERVER = "http://52.35.159.233:80";
    GmobileSvc gmobileControl = new RestAdapter.Builder()
            .setEndpoint(LOCATION_SERVER)
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .build()
            .create(GmobileSvc.class);

    private class SendAsync extends AsyncTask<String,String,String>{

        @Override
        protected  String doInBackground(String... params){
            Cursor res = myDb.getAllData();
            if(res.getCount() == 0)
            {
                //Toast.makeText(this , "No records" , Toast.LENGTH_LONG).show();
            }
            else {
                while(res.moveToNext()) {
                    Gmobile mobile = new Gmobile();
                    mobile.setXaxis(res.getString(0));
                    mobile.setYaxis(res.getString(1));
                    mobile.setZaxis(res.getString(2));

                    mobile.setLattitude(res.getString(3));
                    mobile.setLongitude(res.getString(4));
                   // gmobileControl.submitGmobile(mobile);
                    l.add(mobile);


                }

            }
            String status = gmobileControl.submitGmobileList(l);
            Log.v("status" , status);
            return null;
        }

        @Override
        protected void onPostExecute(String str)
        {
            Toast.makeText(MapsActivity.this,"Data Sent to Cloud",Toast.LENGTH_LONG).show();
        }
    }
   /* private class DrawAsync extends AsyncTask<String,String>
    {
        @Override
        protected  String doInBackground(String... params){
            Cursor res = myDb.getAllData();
            if(res.getCount() == 0)
            {
                //Toast.makeText(this , "No records" , Toast.LENGTH_LONG).show();
            }
            else {



                }

                 return ;
        }
    }*/

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        myDb.deleteData();
    }


    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String>{

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }


    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(4);
                lineOptions.color(Color.BLUE);
            }

            // Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);
        }
    }

}
