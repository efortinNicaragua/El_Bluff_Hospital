package ethanfortin_nicaragua.elbluffhospital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ethanfortin_nicaragua.elbluffhospital.Inventory.PharmacyMain;
import ethanfortin_nicaragua.elbluffhospital.PatientInfo.DoctorMain;
import ethanfortin_nicaragua.elbluffhospital.PatientInfo.SearchAddPatients;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Intent startLogin = new Intent(this, LoginActivity.class);
        //startActivity(startLogin);

    }

    public void MainMenu_PharmacyClick(View v) {
        Intent intent_phmain = new Intent(this, PharmacyMain.class);
        startActivity(intent_phmain);
    }

    public void doctorHome(View v) {
        Intent docHome = new Intent(this, SearchAddPatients.class);
        startActivity(docHome);
    }

    @Override
    public void onBackPressed() {
        //finish();
        moveTaskToBack(true);
    }


}
