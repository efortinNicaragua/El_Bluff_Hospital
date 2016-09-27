package ethanfortin_nicaragua.elbluffhospital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {
//    public final static String EXTRA_MESSSAGE="com.example.ElBluffHospital.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

    }

    public void MainMenu_PharmacyClick(View v) {
        Intent intent_phmain = new Intent(this, PharmacyMain.class);
        startActivity(intent_phmain);
    }
   /* public void MainMenu_DoctorClick(View v) {
        Intent intent_drmain = new Intent(this, PatientInfo.class);
        //EditText editText=(EditText) findViewById(R.id.edit_message);
        //String message =editText.getText().toString();
        //intent.putExtra(EXTRA_MESSSAGE,message);
        startActivity(intent_drmain);
    }*/

    public void doctorHome(View v) {
        Intent docHome = new Intent(this, DoctorMain.class);
        startActivity(docHome);
    }



}
