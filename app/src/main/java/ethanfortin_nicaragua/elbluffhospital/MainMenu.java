package ethanfortin_nicaragua.elbluffhospital;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Intent startLogin = new Intent(this, LoginActivity.class);
        startActivity(startLogin);

    }

    public void MainMenu_PharmacyClick(View v) {
        Intent intent_phmain = new Intent(this, PharmacyMain.class);
        startActivity(intent_phmain);
    }

    public void doctorHome(View v) {
        Intent docHome = new Intent(this, DoctorMain.class);
        startActivity(docHome);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


}
