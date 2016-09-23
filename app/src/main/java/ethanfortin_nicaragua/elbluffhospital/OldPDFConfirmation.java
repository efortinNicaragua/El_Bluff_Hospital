package ethanfortin_nicaragua.elbluffhospital;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OldPDFConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_pdfconfirmation);
    }

    Bundle extras = getIntent().getExtras();
    if(extras != null) {
        String patID = extras.getString("patID");
        String date1st = extras.getString("date1st");

        TextView textView1 = (TextView) findViewById(R.id.reName);
        textView1.setText(patID);

        TextView textView2 = (TextView) findViewById(R.id.reDrug);
        textView2.setText(date1st);

    }
    else {
        // TODO: Error Handling for uninitialized variables.
    }


}
}
