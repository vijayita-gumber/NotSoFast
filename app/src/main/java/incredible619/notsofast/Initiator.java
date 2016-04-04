package incredible619.notsofast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Initiator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiator);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstTime", false))
        {
            Intent intent = new Intent(this,Studentdetails.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Intent intent = new Intent(this,studentsdisplay.class);
            startActivity(intent);
            finish();
        }
    }
}
