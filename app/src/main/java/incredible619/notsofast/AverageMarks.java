package incredible619.notsofast;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import pcsma.database.connection.QuizClient;

public class AverageMarks extends AppCompatActivity
{
    String subject;
    QuizClient qc = new QuizClient();
    TextView textViewAvg ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_average_marks);

        textViewAvg = (TextView)findViewById(R.id.avg);

        Intent intent = getIntent();
        subject = intent.getStringExtra("subject");

        new Average().execute();
    }

    private class Average extends AsyncTask<String, Integer, Float>
    {
        String result ;
        protected void onProgressUpdate(Integer... progress)
        {

        }

        @Override
        protected Float doInBackground(String... params)
        {
           return (qc.getAverage(subject));
        }

        @Override
        protected void onPostExecute(Float result)
        {
                textViewAvg.setText("Average of " + subject + " is: " + String.valueOf(result));
        }
    }
}
