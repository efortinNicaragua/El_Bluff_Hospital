package ethanfortin_nicaragua.elbluffhospital;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class MainMenu extends AppCompatActivity {
//    public final static String EXTRA_MESSSAGE="com.example.ElBluffHospital.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

    }

   /* public void MainMenu_PharmacyClick(View v) {
        Intent intent_phmain = new Intent(this, PharmacyMain.class);
        //EditText editText=(EditText) findViewById(R.id.edit_message);
        //String message =editText.getText().toString();
        //intent.putExtra(EXTRA_MESSSAGE,message);
        startActivity(intent_phmain);
    }*/


}
