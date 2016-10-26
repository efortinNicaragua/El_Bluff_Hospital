package ethanfortin_nicaragua.elbluffhospital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PatientHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_history);
    }
    public void Patient_GeneralInfo(View v) {
        Intent intent_GeneralInfo = new Intent(this, PatientGeneralInfo.class);
        startActivity(intent_GeneralInfo);
    }
    public void Patient_Perscription(View v) {
        Intent intent_Perscription = new Intent(this, PatientPrescription.class);
        startActivity(intent_Perscription);
    }
}
