package ethanfortin_nicaragua.elbluffhospital;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FetchVisits extends AppCompatActivity {

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_visits);

        final FloatingActionButton addVisit = (FloatingActionButton) findViewById(R.id.fab_add);

        addVisit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(FetchVisits.this);
                final View subView = inflater.inflate(R.layout.dialog_add_visit, null);

                final EditText entryDate = (EditText) subView.findViewById(R.id.ddate);
                final EditText entryReason = (EditText) subView.findViewById(R.id.dReason);
                final EditText entryDoctor = (EditText) subView.findViewById(R.id.dDoctor);
                final EditText entryRx = (EditText) subView.findViewById(R.id.dRx);
                final EditText entryPDF = (EditText) subView.findViewById(R.id.dPDF);


                // Get Current Date
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date date = new Date();
                entryDate.setText(dateFormat.format(date));

                final AlertDialog alertDialogBuilder = new AlertDialog.Builder(context)
                    .setTitle("Anadir la visita nueva")
                    .setView(subView)
                    .setMessage("Ingrese la informacion de la visita")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Verify reason
                                String s_razon = entryReason.getText().toString();
                                if (TextUtils.isEmpty(s_razon)) {
                                    //entryReason.setError("Por Favor, llena toda la informacion.");
                                    //int duration = Toast.LENGTH_LONG;
                                    //Context context = getApplicationContext();
                                    String text1 = "Por Favor, llena toda la informacion.";
                                    Toast toast1 = Toast.makeText(getApplicationContext(), text1, Toast.LENGTH_LONG);
                                    toast1.show();
                                }


                                // Verify doctor
                                String s_doc = entryDoctor.getText().toString();
                                if (TextUtils.isEmpty(s_doc)) {
                                    //entryDoctor.setError("Por Favor, llena toda la informacion.");
                                    // int duration = Toast.LENGTH_LONG;
                                    // Context context = getApplicationContext();
                                    String text1 = "Por Favor, llena toda la informacion.";
                                    Toast toast1 = Toast.makeText(getApplicationContext(), text1, Toast.LENGTH_LONG);
                                    toast1.show();
                                }

                                //Set confirmation toast

                                if(entryReason.getText().toString().trim().length() > 0 && entryDoctor.getText().toString().trim().length() >0) {
                                    Toast toast2 = Toast.makeText(getApplicationContext(), "La visita se ha guardado.", Toast.LENGTH_LONG);
                                    toast2.show();
                                }
                            }

                        })

                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        })


                    .create();
                alertDialogBuilder.show();
            }


        });
    }






/* //Set Positive Button
                    .setPositiveButton("Aceptar", null)
                    //Set Negative Button
                    .setNegativeButton("Cancel", null)*/


/*
                        alertDialogBuilder.setOnShowListener(new DialogInterface.OnShowListener(){

                            @Override
                            public void onShow(DialogInterface alertDialogBuilder){
                                Button button = ((AlertDialog)alertDialogBuilder).getButton(AlertDialog.BUTTON_POSITIVE);
                                button.setOnClickListener(new View.OnClickListener(){

                                    @Override
                                    public void onClick(View view){
                                        // Verify reason
                                        String s_razon = entryReason.getText().toString();
                                        if (TextUtils.isEmpty(s_razon)) {
                                            //entryReason.setError("Por Favor, llena toda la informacion.");
                                            //int duration = Toast.LENGTH_LONG;
                                            //Context context = getApplicationContext();
                                            String text1 = "Por Favor, llena toda la informacion.";
                                            Toast toast1 = Toast.makeText(getApplicationContext(), text1, Toast.LENGTH_LONG);
                                            toast1.show();
                                        }


                                        // // Verify doctor
                                        String s_doc = entryDoctor.getText().toString();
                                        if (TextUtils.isEmpty(s_doc)) {
                                            //entryDoctor.setError("Por Favor, llena toda la informacion.");
                                            // int duration = Toast.LENGTH_LONG;
                                            // Context context = getApplicationContext();
                                            String text1 = "Por Favor, llena toda la informacion.";
                                            Toast toast1 = Toast.makeText(getApplicationContext(), text1, Toast.LENGTH_LONG);
                                            toast1.show();
                                        }

                                        //Set confirmation toast
                                        //int duration = Toast.LENGTH_LONG;
                                        //Context context = getApplicationContext();
                                      *//*  String text1 = "La visita se ha guardado.";
                                        Toast toast1 = Toast.makeText(getApplicationContext(), text1, Toast.LENGTH_LONG);
                                        toast1.show();
                                        alertDialogBuilder.dismiss;*//*

                                    }

                                });*/




















    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pat_gen_info:
                startActivity(new Intent(this, FetchPatientInfo.class));
                return true;
            case R.id.pat_history:
                startActivity(new Intent(this, FetchVisits.class));
                return true;
            case R.id.pat_prescription:
                startActivity(new Intent(this, FetchPrescriptions.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        Intent go_back_to_PGI_2 = new Intent(this, FetchPatientInfo.class);
        startActivity(go_back_to_PGI_2);
    }

//        public void addNewVisit(View v){
//            Intent i = new Intent(this, AddNewVisit.class);
//            startActivity(i);
//        }


}


