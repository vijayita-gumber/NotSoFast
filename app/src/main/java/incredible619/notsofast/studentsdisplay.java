package incredible619.notsofast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import pcsma.database.connection.QuizClient;

public class studentsdisplay extends AppCompatActivity {

    TextView textViewName , textViewRoll ;
    QuizClient qc = new QuizClient();
    String email ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentsdisplay);

        textViewName = (TextView) findViewById(R.id.welcome);
        textViewRoll = (TextView)findViewById(R.id.roll);

        Intent intent = getIntent();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String name = prefs.getString("name", "");
        String roll = prefs.getString("roll", "");
        email = prefs.getString("email", "");

        textViewName.setText("Welcome " + name);
        textViewRoll.setText("Roll No  : " + roll);
    }

    public void showmydetails(View view)
    {
       new isValid().execute();
    }

    public void showMarks(View view)
    {
        Intent intent = new Intent(this,Subject.class);
        startActivity(intent);
    }

    public void showAverage(View view)
    {
        Intent intent = new Intent(this,AverageSubjects.class);
        startActivity(intent);
    }

    private class isValid extends AsyncTask<String, Integer, Integer>
    {
        protected void onProgressUpdate(Integer... progress)
        {

        }

        @Override
        protected Integer doInBackground(String... params)
        {
            try
            {
                if(qc.getQuizQues(email).equals("0"))
                    return  1;

                else if(qc.getIsAlready(email) == false)
                    return 0 ;

                else
                    return 2;
            }
            catch (Exception e){ return 2 ;}
        }

        protected void onPostExecute(Integer result)
        {
            if(result == 0)
            {
                Intent intent = new Intent(studentsdisplay.this,Quizes.class);
                startActivity(intent);
            }
            else if(result == 1)
            {
                Intent intent = new Intent(studentsdisplay.this,NoQuiz.class);
                startActivity(intent);
            }
            else
            {
               Toast.makeText(studentsdisplay.this ,"Already Submitted" ,Toast.LENGTH_LONG ).show();
            }
        }
    }
}
