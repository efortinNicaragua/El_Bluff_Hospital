package ethanfortin_nicaragua.elbluffhospital;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class NewVisitHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_visit_history);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pat_gen_info:
                startActivity(new Intent(this, NewPatientGenInfo.class));
                return true;
            case R.id.pat_history:
                startActivity(new Intent(this, NewVisitHistory.class));
                return true;
            case R.id.pat_prescription:
                startActivity(new Intent(this, NewPatientPrescription.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

