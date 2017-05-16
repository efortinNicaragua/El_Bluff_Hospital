package ethanfortin_nicaragua.elbluffhospital.PatientInfo;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import ethanfortin_nicaragua.elbluffhospital.R;

public class AddPrescription extends AppCompatActivity {

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);

        Button newMed = (Button) findViewById(R.id.button);
        newMed.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(AddPrescription.this);
                View subView = inflater.inflate(R.layout.dialog_rx_confirm, null);

                final EditText entryPatIdOrName = (EditText)findViewById(R.id.patIdOrName);
                final EditText entryMedId = (EditText)findViewById(R.id.medId);
                final EditText entryPillQuant = (EditText)findViewById(R.id.pillQuantity);
                final EditText entryDirections = (EditText)findViewById(R.id.etDirections);
                final EditText entryDuration = (EditText)findViewById(R.id.etDuration);
                final EditText entryDoctor = (EditText)findViewById(R.id.popout_calendar);
                final EditText entryReason = (EditText)findViewById(R.id.etReason);

                TextView tv1 = (TextView)subView.findViewById(R.id.dPatIdOrName);
                TextView tv2 = (TextView)subView.findViewById(R.id.dMedId);
                TextView tv3 = (TextView)subView.findViewById(R.id.dPillQuantity);
                TextView tv4 = (TextView)subView.findViewById(R.id.popout_calendar);
                TextView tv5 = (TextView)subView.findViewById(R.id.date);

                int eFlag = 0;

                // Verify pat name
                String s_patId = entryPatIdOrName.getText().toString();
                if(s_patId.equals("")) {
                    eFlag = 1;
                }
                else {
                    tv1.setText(s_patId);
                    // INSERT in database
                }

                // Verify drug id
                String s_drugId = entryMedId.getText().toString();
                if(s_drugId.equals("")) {
                    eFlag = 1;
                }
                else {
                    tv2.setText(s_drugId);
                    // INSERT in database
                }

                // Verify drug quantity
                String s_drugQuant = entryPillQuant.getText().toString();
                if(s_drugQuant.equals("")) {
                    eFlag = 1;
                }
                else {
                    tv3.setText(s_drugQuant);
                    // INSERT in database
                }

                // Verify directions
                String s_directions = entryDirections.getText().toString();
                if(s_directions.equals("")) {
                    eFlag = 1;
                }
                else {
                    // INSERT in database
                }

                // Verify duration
                String s_duration = entryDuration.getText().toString();
                if(s_duration.equals("")) {
                    eFlag = 1;
                }
                else {
                    // INSERT in database
                }

                // Verify Doctor
                String s_doctor = entryDoctor.getText().toString();
                if(s_doctor.equals("")) {
                    eFlag = 1;
                }
                else {
                    tv4.setText(s_doctor);
                    // INSERT in database
                }

                // Verify Reason
                String s_reason = entryReason.getText().toString();
                if(s_reason.equals("")) {
                    eFlag = 1;
                }
                else {
                    // INSERT in database
                }

                // Get Current Date and Time
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                Date date = new Date();
                tv5.setText(dateFormat.format(date));

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Confirmación de la prescripción.");

                // set Dialog message
                alertDialogBuilder
                        .setView(subView)
                        .setPositiveButton("Confirma",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // for now...

                                // Creation of Toast warning that is displayed if fields are blank
                                Context apContext2 = getApplicationContext();
                                CharSequence text = "La prescripción ha sido creado.";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast2 = Toast.makeText(apContext2, text, duration);
                                toast2.show();

                                /*
                                    TODO: - Add functionality that synthesizes this info with the db.
                                            - if drug Id is used, convert to name and display.
                                            - Subtracts number of pills from correct store.
                                            - Stores record of prescription under patient's info.
                                            - Include Medicine info (id, name, strength(?), quantity),
                                                plus reason, etc.
                                */

                            }
                        })
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                if(eFlag == 0) {
                    alertDialog.show();
                }
                else {
                    // Creation of Toast warning that is displayed if fields are blank
                    Context apContext1 = getApplicationContext();
                    CharSequence text = "Por Favor, llena toda la informacion.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(apContext1, text, duration);

                    toast.show();
                }
            }

        });
    }
}
