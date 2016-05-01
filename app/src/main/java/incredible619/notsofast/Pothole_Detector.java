package incredible619.notsofast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

//mport com.github.amlcurran.showcaseview.ShowcaseView;
//import com.github.amlcurran.showcaseview.targets.ActionViewTarget;

public class Pothole_Detector extends AppCompatActivity {

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pothole__detector);
       showProgress();
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    });
                    hideProgress();
                    Intent intent = new Intent(Pothole_Detector.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();


    }

   void showProgress()
    {
        pd = new ProgressDialog(this);
        pd.setTitle("");
        pd.setMessage("");
        pd.setIndeterminate(true);
        pd.show();
    }

    void hideProgress()
    {
        if(pd!=null)
            pd.dismiss();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_focom, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
