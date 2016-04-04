package incredible619.notsofast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Subject extends AppCompatActivity {

    Button pcsma , cn , os;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        pcsma = (Button)findViewById(R.id.pcsma);
        cn = (Button)findViewById(R.id.cn);
        os = (Button)findViewById(R.id.os);

    }

    public void pcsmaSubject(View view)
    {
        Intent intent = new Intent(this,Marks.class);
        intent.putExtra("subject","pcsma");
        startActivity(intent);
    }

    public void cnSubject(View view)
    {
        Intent intent = new Intent(this,Marks.class);
        intent.putExtra("subject","cn");
        startActivity(intent);
    }

    public void osSubject(View view)
    {
        Intent intent = new Intent(this,Marks.class);
        intent.putExtra("subject","os");
        startActivity(intent);
    }

}
