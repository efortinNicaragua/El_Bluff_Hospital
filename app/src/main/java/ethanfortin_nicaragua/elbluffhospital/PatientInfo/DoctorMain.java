package ethanfortin_nicaragua.elbluffhospital.PatientInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ethanfortin_nicaragua.elbluffhospital.MainMenu;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.UploadFile;

public class DoctorMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);
    }

    public void patientHome (View v) {
        Intent patHome = new Intent(this, SearchAddPatients.class);
        startActivity(patHome);
    }
    public void uploadOldFileHome (View v) {
        Intent uofHome = new Intent(this, UploadFile.class);
        startActivity(uofHome);
    }

    @Override
    public void onBackPressed(){
        Intent go_back = new Intent(this, MainMenu.class);
        startActivity(go_back);
    }



}
