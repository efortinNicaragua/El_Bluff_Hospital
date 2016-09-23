package ethanfortin_nicaragua.elbluffhospital;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SubtractMedicine extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtract_medicine);
    }

    // On Button Click Method
    public void createPrescrip(View v) {
        Intent createPre = new Intent(this, PrescriptionConfirmation.class);
        int eFlag = 0; // This flag set if one or more fields are blank when button is pressed

        // Creation of Toast warning that is displayed if fields are blank
        Context context = getApplicationContext();
        CharSequence text = "Por Favor, llena toda la informacion.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);

        // Verifies patient name/id
        EditText editText1 = (EditText) findViewById(R.id.patIdOrName);
        String s_patId = editText1.getText().toString();
        if(s_patId.equals("")) {
            eFlag = 1;
            toast.show();
        }
        else {
            createPre.putExtra("patID", s_patId);
        }

        // Verifies medicine name/id
        EditText editText2 = (EditText) findViewById(R.id.medId);
        String s_medId = editText2.getText().toString();
        if(s_medId.equals("")) {
            eFlag = 1;
            toast.show();
        }
        else {
            createPre.putExtra("medID", s_medId);
        }

        // Verifies pill quantity
        EditText editText3 = (EditText) findViewById(R.id.pillQuantity);
        if(editText3.getText().toString().equals("")) {
            eFlag = 1;
            toast.show();
        }
        else {
            int i_pillQuan = Integer.parseInt(editText3.getText().toString());
            createPre.putExtra("pills", i_pillQuan);
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
            startActivity(createPre);
        }
    }
}
