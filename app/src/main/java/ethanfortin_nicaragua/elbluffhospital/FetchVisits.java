package ethanfortin_nicaragua.elbluffhospital;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FetchVisits extends AppCompatActivity {
    HashMap<String, List<String>> HashMap_hashmap;
    List<String> HastMap_list;
    ExpandableListView Exp_list;
    Adapter_ExpandableList adapter;
    final Context context = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_visits);

        Exp_list = (ExpandableListView) findViewById(R.id.exp_list);
        HashMap_hashmap= DataProvider.getInfo();
        HastMap_list=new ArrayList<String>(HashMap_hashmap.keySet());
        adapter=new Adapter_ExpandableList(this, HashMap_hashmap, HastMap_list);
        Exp_list.setAdapter(adapter);

        //Example code of things you could do but we dont need this
       /* Exp_list.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener(){
            @Override
            public void onGroupExpand(int groupPosition){
                Toast.makeText(getBaseContext(),HastMap_list.get(groupPosition)+" is expanded", Toast.LENGTH_SHORT).show();
            }
        });*/

        Exp_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                LayoutInflater inflater = LayoutInflater.from(FetchVisits.this);
                final View subView = inflater.inflate(R.layout.dialog_patient_history, null);

                final EditText edit_date = (EditText) subView.findViewById(R.id.dialog1_edit_date);
                final EditText edit_reason = (EditText) subView.findViewById(R.id.dialog1_edit_reason);
                final EditText edit_medecine = (EditText) subView.findViewById(R.id.dialog1_edit_medicine);
                final EditText edit_strength = (EditText) subView.findViewById(R.id.dialog1_edit_strength);
                final EditText edit_totalAmount = (EditText) subView.findViewById(R.id.dialog1_edit_totalAmount);
                final EditText edit_directions = (EditText) subView.findViewById(R.id.dialog1_edit_directions);
                final EditText edit_doctor = (EditText) subView.findViewById(R.id.dialog1_edit_doctor);
                final EditText edit_PDFLink = (EditText) subView.findViewById(R.id.dialog1_edit_PDFLink);
                final EditText edit_comments = (EditText) subView.findViewById(R.id.dialog1_edit_treatment_comments);

                final AlertDialog alertDialogBuilder = new AlertDialog.Builder(context)
                        .setTitle("Anadir la visita nueva")
                        .setView(subView)
                        .setMessage("Ingrese la informacion de la visita")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.dismiss();
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

                return false;
            }
        });

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
public static class DataProvider{
    public static HashMap<String, List<String>> getInfo()
    {
        HashMap<String, List<String>> HashMap_details = new HashMap<String, List<String>>();

        List<String> ActionMovies = new ArrayList<String>();
         ActionMovies.add("Doctor:Barquero");
         ActionMovies.add("Prescripcion: No");
         ActionMovies.add("PDF: link");

        List<String>RomanticMovies = new ArrayList<String>();
        RomanticMovies.add("Doctor:Barquero");
        RomanticMovies.add("Prescripcion: 12 Tylenol 125mg");
        RomanticMovies.add("PDF: link");

        List<String>HorrorMovies = new ArrayList<String>();
        HorrorMovies.add("Doctor:Barquero");
        HorrorMovies.add("Prescripcion:No");
        HorrorMovies.add("PDF: link");

        List<String>ComedyMovies = new ArrayList<String>();
        ComedyMovies.add("Doctor:Barquero");
        ComedyMovies.add("Prescripcion: No");
        ComedyMovies.add("PDF: link");

        HashMap_details.put("30/1/2016   Brazo Rompido",ActionMovies);
        HashMap_details.put("23/12/2015 Inflamacion del pie", RomanticMovies);
        HashMap_details.put("13/8/2016   Palpaciones irregulares", HorrorMovies);
        HashMap_details.put("8/11/2015 4 puntadas en la cabeza",ComedyMovies);

        return HashMap_details;
    }

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


