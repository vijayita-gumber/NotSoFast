package incredible619.notsofast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.HelloSaveTest;

import java.util.ArrayList;

public class Studentdetails extends AppCompatActivity {


    EditText editTextName , editTextRoll ,editTextlname,editTextemail;
    CheckBox checkbox1,checkbox2,checkbox3;
    String name , roll,lname,email ;

    ArrayList<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentdetails);



        editTextName = (EditText)findViewById(R.id.welcome);
        editTextRoll = (EditText)findViewById(R.id.rollNo);
        editTextemail = (EditText)findViewById(R.id.eMail);
        editTextlname = (EditText)findViewById(R.id.lastname);

        checkbox1 = (CheckBox) findViewById(R.id.pcsma);
        checkbox2 = (CheckBox) findViewById(R.id.os);
        checkbox3 = (CheckBox) findViewById(R.id.cn);
    }

    public void senddetails(View view) {

        name = editTextName.getText().toString();
        roll = editTextRoll.getText().toString();
        email = editTextemail.getText().toString();
        lname = editTextlname.getText().toString();

        if(checkbox1 .isChecked())
            list.add(checkbox1.getText().toString());
        if(checkbox2 .isChecked())
            list.add(checkbox2.getText().toString());
        if(checkbox3 .isChecked())
            list.add(checkbox3.getText().toString());


        if(name.length()>2 && lname.length() > 2)
        {
            if (name.matches("[a-zA-Z ]+") && lname.matches("[a-zA-Z ]+"))
            {
                if(roll.length() > 6 && roll.length() <9)
                {
                    if (email.endsWith("@iiitd.ac.in"))
                    {
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("firstTime", true);
                        editor.putString("name", name);
                        editor.putString("email", email);
                        editor.putString("roll", roll);
                        editor.commit();

                        new DownloadFilesTask().execute();
                        Intent intent = new Intent(this, studentsdisplay.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                        Toast.makeText(this, "You must enter a IIITD EmailId", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(this, "Enter a valid RollNo", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this, "Name must contain characters", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "Length must be >2", Toast.LENGTH_LONG).show();

    }
    private class DownloadFilesTask extends AsyncTask<String, Integer, Long> {


        protected void onProgressUpdate(Integer... progress) {


            //setProgressPercent(progress[0]);
        }

        @Override
        protected Long doInBackground(String... params) {

            HelloSaveTest test = new HelloSaveTest();
           // test.saveObject("vijata1","gumber1","sdfcvvij@gmail.com","msfaft14030",list);
            test.saveObject(name,lname,email,roll,list);
            return null;
        }

        protected void onPostExecute(Long result) {
            //showDialog("Downloaded " + result + " bytes");
        }
    }
}
