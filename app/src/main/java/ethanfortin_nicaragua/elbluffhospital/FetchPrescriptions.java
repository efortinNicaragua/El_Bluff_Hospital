package ethanfortin_nicaragua.elbluffhospital;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class FetchPrescriptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_prescriptions);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pat_gen_info:
                startActivity(new Intent(this, FetchPatientInfo.class));
                return true;
            case R.id.pat_history:
                startActivity(new Intent(this, FetchVisits.class));
                return true;
            case R.id.pat_prescription:
                startActivity(new Intent(this, FetchPrescriptions.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed(){
        Intent go_back_to_PGI = new Intent(this, FetchPatientInfo.class);
        startActivity(go_back_to_PGI);
    }
}


