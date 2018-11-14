package ethanfortin_nicaragua.elbluffhospital.PatientInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.ShipmentAdapter1;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.DruginfoFields;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.ShipmentFields;
import ethanfortin_nicaragua.elbluffhospital.Inventory.FetchSpecificDrug;
import ethanfortin_nicaragua.elbluffhospital.MainMenu;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;

public class AddNewPrescription2 extends AppCompatActivity {




    Button button_fake;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_prescription2);

        button_fake = (Button) findViewById(R.id.button_fake);

        button_fake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void openDialog() {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Set Custom Title
        TextView title = new TextView(this);
        // Title Properties
        title.setText("Fantastico");
        title.setPadding(10, 10, 10, 10);   // Set Position
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        // Set Message
        /*TextView msg = new TextView(this);
        // Message Properties
        msg.setText("I am a Custom Dialog Box. \n Please Customize me.");
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextColor(Color.BLACK);
        alertDialog.setView(msg);
       */

        // Set Button
        // you can more buttons
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Back", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Perform Action on Button
            }
        });
/*
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Perform Action on Button
            }
        });
*/
        new Dialog(getApplicationContext());
        alertDialog.show();

        // Set Properties for back Button
        final Button okBT = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neutralBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        neutralBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        okBT.setPadding(10, 10, 10, 10);   // Set Position
        okBT.setTextColor(Color.BLUE);
        okBT.setLayoutParams(neutralBtnLP);
/*
        final Button cancelBT = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams negBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        negBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        cancelBT.setTextColor(Color.RED);
        cancelBT.setLayoutParams(negBtnLP);
    }
    */
    }




    public void onBackPressed() {
        Intent go_back = new Intent(this, SearchAddPatients.class);
        startActivity(go_back);


    }
    public void FakeNewPresc(View V) {
        Intent donePat = new Intent(AddNewPrescription2.this, FetchPatientInfo.class);
        startActivity(donePat);
    }

}

