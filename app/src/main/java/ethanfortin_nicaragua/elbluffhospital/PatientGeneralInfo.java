package ethanfortin_nicaragua.elbluffhospital;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PatientGeneralInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_general_info);
    }
    public void Patient_History(View v) {
        Intent intent_History = new Intent(this, PatientHistory.class);
        startActivity(intent_History);
    }
    public void Patient_Perscription(View v) {
        Intent intent_Perscription = new Intent(this,PatientPrescription.class);
        startActivity(intent_Perscription);
    }
}

