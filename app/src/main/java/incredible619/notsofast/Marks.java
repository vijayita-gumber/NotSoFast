package incredible619.notsofast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pcsma.database.connection.QuizClient;
import pcsma.database.connection.QuizMarks;

public class Marks extends AppCompatActivity {

    TextView display ;
    ListView listView;
    String subject ,email;
    ArrayList<QuizMarks> result ;

    QuizClient qc = new QuizClient();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);

        display = (TextView)findViewById(R.id.marks);
        listView = (ListView)findViewById(R.id.listView);

        Intent intent = getIntent();
        subject = intent.getStringExtra("subject");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        email = prefs.getString("email", "");


        new getMarks().execute();
    }

    private class getMarks extends AsyncTask<String, Integer, ArrayList>
    {
        protected void onProgressUpdate(Integer... progress)
        {        }

        @Override
        protected ArrayList<QuizMarks> doInBackground(String... params)
        {
            result = qc.getQuizMarks(subject, email);
            Log.v("result" , result.toString());
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList result)
        {
            ArrayAdapter<QuizMarks>  Values = new ArrayAdapter<QuizMarks>(Marks.this, R.layout.listlayout, result);
            listView.setAdapter(Values);

        }
    }
}
