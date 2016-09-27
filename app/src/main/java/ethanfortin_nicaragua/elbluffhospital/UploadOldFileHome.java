package ethanfortin_nicaragua.elbluffhospital;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UploadOldFileHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_old_file_home);
    }


    // On Button Click Method
    public void createFile(View v) {
        Intent createF = new Intent(this, PrescriptionConfirmation.class);
        int eFlag = 0; // This flag set if one or more fields are blank when button is pressed

        // Creation of Toast warning that is displayed if fields are blank
        Context context = getApplicationContext();
        CharSequence text = "Por Favor, llena toda la informacion.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);

        // Verifies patient id
        EditText editText1 = (EditText) findViewById(R.id.patID);
        String s_patID = editText1.getText().toString();
        if(s_patID.equals("")) {
            eFlag = 1;
            toast.show();
        }
        else {
            createF.putExtra("patID", s_patID);
             }

        // Verifies date of first visit
        EditText editText2 = (EditText) findViewById(R.id.date1st);
        String s_date1st = editText2.getText().toString();
        if(s_date1st.equals("")) {
            eFlag = 1;
            toast.show();
        }
        else {
            createF.putExtra("date1st", s_date1st);
        }



        /*
        TODO: - Add functionality that synthesizes this info with the db.
                - Subtracts number of pills from correct store.
                - Stores record of prescription under patient's info.
                  - Include Medicine info (id, name, strength(?), quantity),
                    plus date, reason, etc.
         */

        // Only move to next activity if all fields are populated
        if(eFlag == 0) {
           startActivity(createF);
        }
    }
}
