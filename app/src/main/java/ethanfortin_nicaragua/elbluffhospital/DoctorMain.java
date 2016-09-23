package ethanfortin_nicaragua.elbluffhospital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DoctorMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
    }

    public void patientHome (View v) {
        Intent patHome = new Intent(this, PatientInfo.class);
        startActivity(patHome);
    }
    public void uploadOldFileHome (View v) {
        Intent uofHome = new Intent(this, UploadOldFileHome.class);
        startActivity(uofHome);
    }


}
