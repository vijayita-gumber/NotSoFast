package incredible619.notsofast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AverageSubjects extends AppCompatActivity {

    Button pcsma , cn , os;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_average_subjects);

        pcsma = (Button)findViewById(R.id.pcsma);
        cn = (Button)findViewById(R.id.cn);
        os = (Button)findViewById(R.id.os);
    }

    public void pcsmaSubjectAvg(View view)
    {
        Intent intent = new Intent(this,AverageMarks.class);
        intent.putExtra("subject","pcsma");
        startActivity(intent);
    }

    public void cnSubjectAvg(View view)
    {
        Intent intent = new Intent(this,AverageMarks.class);
        intent.putExtra("subject","cn");
        startActivity(intent);
    }

    public void osSubjectAvg(View view)
    {
        Intent intent = new Intent(this,AverageMarks.class);
        intent.putExtra("subject","os");
        startActivity(intent);
    }
}
