package ethanfortin_nicaragua.elbluffhospital;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PrescriptionConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_confirmation);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String patID = extras.getString("patID");
            String medID = extras.getString("medID");
            int pills = extras.getInt("pills");

            TextView textView1 = (TextView) findViewById(R.id.reName);
            textView1.setText(patID);

            TextView textView2 = (TextView) findViewById(R.id.reDrug);
            textView2.setText(medID);

            TextView textView3 = (TextView) findViewById(R.id.reQuant);
            textView3.setText(String.valueOf(pills));
        }
        else {
            // TODO: Error Handling for uninitialized variables.
        }


    }
}
