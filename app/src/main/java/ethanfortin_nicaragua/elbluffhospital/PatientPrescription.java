package ethanfortin_nicaragua.elbluffhospital;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import static ethanfortin_nicaragua.elbluffhospital.R.id.edit_finish_date1;
import static ethanfortin_nicaragua.elbluffhospital.R.id.edit_medecine1;
import static ethanfortin_nicaragua.elbluffhospital.R.id.edit_quantity1;
import static ethanfortin_nicaragua.elbluffhospital.R.id.edit_start_date1;
import static ethanfortin_nicaragua.elbluffhospital.R.id.edit_strength1;

public class PatientPrescription extends AppCompatActivity {
    //declare variables here so they can be referanced by all
    private TextView temp_start_date1;
    private TextView temp_finish_date1;
    private TextView temp_medecine1;
    private TextView temp_strength1;
    private TextView temp_quantity1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_prescription);

        //Set global variables equal to their text view in PatientPrescription
         temp_start_date1= (TextView) findViewById(edit_start_date1);
         temp_finish_date1=(TextView) findViewById(edit_finish_date1);
         temp_medecine1=(TextView) findViewById(edit_medecine1);
         temp_strength1=(TextView) findViewById(edit_strength1);
         temp_quantity1=(TextView) findViewById(edit_quantity1);
    }

    //Click to return to general info
    //Need to fix this it starts a new screen everytime instead of reloading
    //Need to fix this it doesn't return to right screen on back button
    public void Patient_GeneralInfo(View v) {
        Intent intent_GeneralInfo = new Intent(this, PatientGeneralInfo.class);
        startActivity(intent_GeneralInfo);
    }

    //Click to return to History info
    //Need to fix this it starts a new screen everytime instead of reloading
    //Need to fix this it doesn't return to right screen on back button
    public void Patient_History(View v) {
        Intent intent_History = new Intent(this, PatientHistory.class);
        startActivity(intent_History);
    }

    //Click on table row 1 it sends you to dialog to learn more
    public void moreinfo1(View v){

        //Need to make dialog_patient_preccription pull data from DB not my made up stuff
        //create layout inflater and subView assign sub view to dialog xml
        LayoutInflater inflater = LayoutInflater.from(PatientPrescription.this);
        View subView = inflater.inflate(R.layout.dialog_patient_prescription, null);
        //Build dialog set it to subview
        AlertDialog.Builder builderSingle1 = new AlertDialog.Builder(this);
        builderSingle1.setView(subView);
        //Set dialog title
        builderSingle1.setTitle("Prescripción de " + "Nombre");

        //Initilaze Dialog variables to other variables
        //Need to initialuze other variables not in Patient Prescription XML so we can push to DB
        final EditText edit_start_date2 = (EditText) subView.findViewById(R.id.dialog1_edit_start);
        final EditText edit_finish_date2=(EditText) subView.findViewById(R.id.dialog1_edit_end);
        final EditText edit_medecine2=(EditText) subView.findViewById(R.id.dialog1_edit_medicine);
        final EditText edit_strength2=(EditText) subView.findViewById(R.id.dialog1_edit_strength);
        final EditText edit_quantity2=(EditText) subView.findViewById(R.id.dialog1_edit_totalAmount);

        builderSingle1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builderSingle1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        temp_start_date1.setText(edit_start_date2.getText());
                        temp_finish_date1.setText(edit_finish_date2.getText());
                        temp_medecine1.setText(edit_medecine2.getText());
                        temp_strength1.setText(edit_strength2.getText());
                        temp_quantity1.setText(edit_quantity2.getText());

                        //Push to DB including ones not above but in Dialog
                        dialog.dismiss();
                    }
                });
        builderSingle1.show();
    }

}
