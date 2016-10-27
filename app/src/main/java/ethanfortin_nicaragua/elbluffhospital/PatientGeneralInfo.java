package ethanfortin_nicaragua.elbluffhospital;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static ethanfortin_nicaragua.elbluffhospital.R.id.edit_Allergies;
import static ethanfortin_nicaragua.elbluffhospital.R.id.edit_Birthdate;
import static ethanfortin_nicaragua.elbluffhospital.R.id.edit_Children;
import static ethanfortin_nicaragua.elbluffhospital.R.id.edit_Height;
import static ethanfortin_nicaragua.elbluffhospital.R.id.edit_ID;
import static ethanfortin_nicaragua.elbluffhospital.R.id.edit_Married;
import static ethanfortin_nicaragua.elbluffhospital.R.id.edit_MedicalConditions;
import static ethanfortin_nicaragua.elbluffhospital.R.id.edit_Telephone;
import static ethanfortin_nicaragua.elbluffhospital.R.id.edit_Weight;
import static ethanfortin_nicaragua.elbluffhospital.R.id.edit_adress;
import static ethanfortin_nicaragua.elbluffhospital.R.id.edit_gender;
import static ethanfortin_nicaragua.elbluffhospital.R.id.edit_name;

public class PatientGeneralInfo extends AppCompatActivity {
    //declare variables here so they can be referanced by all
    private TextView temp_name1;
    private TextView temp_ID1;
    private TextView temp_adress1;
    private TextView temp_telephone1;
    private TextView temp_gender1;
    private TextView temp_married1;
    private TextView temp_birthday1;
    private TextView temp_children1;
    private TextView temp_height1;
    private TextView temp_weight1;
    private TextView temp_allergies1;
    private TextView temp_medicalConditions1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_general_info);
        //Set global variables equal to their text view in PatientPrescription
         TextView temp_name1=(TextView) findViewById(edit_name);
         TextView temp_ID1=(TextView) findViewById(edit_ID);
         TextView temp_adress1=(TextView) findViewById(edit_adress);
         TextView temp_telephone1=(TextView) findViewById(edit_Telephone);
         TextView temp_gender1=(TextView) findViewById(edit_gender);
         TextView temp_married1=(TextView) findViewById(edit_Married);
         TextView temp_birthday1=(TextView) findViewById(edit_Birthdate);
         TextView temp_children1=(TextView) findViewById(edit_Children);
         TextView temp_height1=(TextView) findViewById(edit_Height);
         TextView temp_weight1=(TextView) findViewById(edit_Weight);
         TextView temp_allergies1=(TextView) findViewById(edit_Allergies);
         TextView temp_medicalConditions1=(TextView) findViewById(edit_MedicalConditions);
    }
    //Click to return to Patient Histroy
    //Need to fix this it starts a new screen everytime instead of reloading
    //Need to fix this it doesn't return to right screen on back button
    public void Patient_History(View v) {
        Intent intent_History = new Intent(this, PatientHistory.class);
        startActivity(intent_History);
    }
    //Click to return to Prescription, parden my typos
    //Need to fix this it starts a new screen everytime instead of reloading
    //Need to fix this it doesn't return to right screen on back button
    public void Patient_Perscription(View v) {
        Intent intent_Perscription = new Intent(this,PatientPrescription.class);
        startActivity(intent_Perscription);
    }

    //Click loads dialog to edit
    public void Editar(View v){
        //Need to make dialog_patient_preccription pull data from DB not my made up stuff
        //create layout inflater and subView assign sub view to dialog xml
        LayoutInflater inflater = LayoutInflater.from(PatientGeneralInfo.this);
        View subView = inflater.inflate(R.layout.dialog_edit_general_patient, null);
        //Build dialog set it to subview
        AlertDialog.Builder builderSingle1 = new AlertDialog.Builder(this);
        builderSingle1.setView(subView);
        //Set dialog        title
        builderSingle1.setTitle("Información General de  " + "Nombre");


        //Initilaze Dialog variables to other variables
        //Need to initialize other variables in dialog XML so we can push to DB
        final EditText edit_name2 = (EditText) subView.findViewById(R.id.dialog_edit_name);
        final EditText edit_ID2 = (EditText) subView.findViewById(R.id.dialog_edit_ID);
        final EditText edit_adress2 = (EditText) subView.findViewById(R.id.dialog_edit_adress);
        final EditText edit_telephone2 = (EditText) subView.findViewById(R.id.dialog_edit_Telephone);
        final EditText edit_gender2 = (EditText) subView.findViewById(R.id.dialog_edit_gender);
        final EditText edit_married2 = (EditText) subView.findViewById(R.id.dialog_edit_Married);
        final EditText edit_birthday2 = (EditText) subView.findViewById(R.id.dialog_edit_Birthdate);
        final EditText edit_children2 = (EditText) subView.findViewById(R.id.dialog_edit_Children);
        final EditText edit_height2 = (EditText) subView.findViewById(R.id.dialog_edit_Height);
        final EditText edit_weight2 = (EditText) subView.findViewById(R.id.dialog_edit_Weight);
        final EditText edit_allergies2 = (EditText) subView.findViewById(R.id.dialog_edit_Allergies);
        final EditText edit_medicalConditions2 = (EditText) subView.findViewById(R.id.dialog_edit_MedicalConditions);
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
                      //temp_name1.setText(edit_name2.getText());
                      //temp_ID1.setText(edit_ID2.getText());
                      //temp_adress1.setText(edit_adress2.getText());
                      //temp_telephone1.setText(edit_telephone2.getText());
                      //temp_gender1.setText(edit_gender2.getText());
                      //temp_married1.setText(edit_married2.getText());
                      //temp_birthday1.setText(edit_birthday2.getText());
                      //temp_children1.setText(edit_children2.getText());
                      //temp_height1.setText(edit_height2.getText());
                      //temp_weight1.setText(edit_weight2.getText());
                      //temp_allergies1.setText(edit_allergies2.getText());
                      //temp_medicalConditions1.setText(edit_medicalConditions2.getText());
//
                        //Push to DB including ones not above but in Dialog
                        dialog.dismiss();
                    }
                });
        builderSingle1.show();
    }
}

