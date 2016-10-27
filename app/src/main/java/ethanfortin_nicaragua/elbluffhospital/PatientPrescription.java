package ethanfortin_nicaragua.elbluffhospital;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;

public class PatientPrescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_prescription);
    }

    public void Patient_GeneralInfo(View v) {
        Intent intent_GeneralInfo = new Intent(this, PatientGeneralInfo.class);
        startActivity(intent_GeneralInfo);
    }

    public void Patient_History(View v) {
        Intent intent_History = new Intent(this, PatientHistory.class);
        startActivity(intent_History);
    }
    public void moreinfo1(View v){


      ;
        LayoutInflater inflater = LayoutInflater.from(PatientPrescription.this);
        View subView = inflater.inflate(R.layout.dialog_patient_prescription, null);
        AlertDialog.Builder builderSingle1 = new AlertDialog.Builder(this);
        builderSingle1.setView(subView);
        builderSingle1.setTitle("Prescripción de " + "Nombre");

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
                        dialog.dismiss();
                    }
                });
        builderSingle1.show();
    }

}
