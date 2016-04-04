package incredible619.notsofast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.HelloSaveTest;

import pcsma.database.connection.QuizClient;
import pcsma.database.connection.QuizDetails;
import pcsma.database.connection.QuizSvc;
import retrofit.RestAdapter;

public class Quizes extends AppCompatActivity {

    TextView textQues ;
    RadioButton radio_b;
    RadioGroup radio_g;
    Button submit ;

    String name , radio_selected ;
    int selected_id ;

    String email , roll ;
    QuizClient qc = new QuizClient();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizes);

        radio_g = (RadioGroup) findViewById(R.id.radioGroup);

        textQues = (TextView)findViewById(R.id.ques);
        submit = (Button)findViewById(R.id.submit);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        email= prefs.getString("email", "");
        roll = prefs.getString("roll", "");

        new Question().execute();
    }

    public void senddetails(View view)
    {
        selected_id = radio_g.getCheckedRadioButtonId();
        Log.v("iddd", Integer.toString(selected_id));

        if(selected_id == -1)
        {
            Toast.makeText(this , "Select an option" , Toast.LENGTH_LONG).show();
        }
        else
        {
            radio_b = (RadioButton) findViewById(selected_id);
            radio_selected = radio_b.getText().toString();
            new SendToDatabase().execute();
        }

    }

    private class SendToDatabase extends AsyncTask<String, Integer, Integer>
        {
            protected void onProgressUpdate(Integer... progress)
            {

            }

            @Override
        protected Integer doInBackground(String... params)
        {
            try
            {
                qc.testVideoAddAndList(email, radio_selected, roll);
                return 0 ;
            }
            catch (Exception e){ return 1 ;}
            //return null;
        }

        protected void onPostExecute(Integer result)
        {
            if(result == 0)
            {
                Intent intent = new Intent(Quizes.this,studentsdisplay.class);
                startActivity(intent);
                finish();
            }

            //showDialog("Downloaded " + result + " bytes");
        }
    }

    private class Question extends AsyncTask<String, Integer, String>
    {
        String result ;
        protected void onProgressUpdate(Integer... progress)
        {

        }

        @Override
        protected String doInBackground(String... params)
        {
                result = qc.getQuizQues(email);
                return result;

        }

        @Override
        protected void onPostExecute(String result)
        {
            if(result.equals("0"))
            {
                Intent intent = new Intent(Quizes.this,NoQuiz.class);
                startActivity(intent);
            }

            else
             textQues.setText(result);
        }
    }
}
