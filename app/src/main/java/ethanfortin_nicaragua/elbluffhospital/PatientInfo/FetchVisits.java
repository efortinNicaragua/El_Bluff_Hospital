package ethanfortin_nicaragua.elbluffhospital.PatientInfo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.FetchVisits_ExpListAdapter;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.VisitFields;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;

public class FetchVisits extends AppCompatActivity {

    private ExpandableListView listView;
    private FetchVisits_ExpListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;


    final Context context = this;
    ArrayList<VisitFields> patVisitdata = new ArrayList();
    ListView LV_patVisit;
    int count = 0;
    int hash_count = 0;
    String sID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_visits);

        Intent intent = getIntent();
        sID = intent.getStringExtra("patid");
        patientVisitFetch(sID);


        /*listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
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
                                // If this button is clicked, just close
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
        });*/

        final FloatingActionButton addVisit = (FloatingActionButton) findViewById(R.id.fab_add);

        addVisit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(FetchVisits.this);
                final View subView = inflater.inflate(R.layout.dialog_add_visit, null);

                final EditText entryDate = (EditText) subView.findViewById(R.id.etRxID);
                final EditText entryReason = (EditText) subView.findViewById(R.id.etPaciente);
                final EditText entryDoctor = (EditText) subView.findViewById(R.id.etDoctor);
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

                                if (entryReason.getText().toString().trim().length() > 0 && entryDoctor.getText().toString().trim().length() > 0) {
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


    /**
     * ML: This method is accessed from inside the onCLick
     **/
    private void patientVisitFetch(final String argVal) {

        /**ML: This method normally gets called on the onCLick but I think here it may need to be calle don the onCreate**/
        class fetchPatientVisit extends AsyncTask<Void, Void, String> {

            //In here, get the information from the user's selection
            protected String doInBackground(Void... params) {
                RequestHandler reqHan = new RequestHandler();
                HashMap<String, String> map = new HashMap<>();
                String s;

                /**ML: argVal needs to be passed the patient id from the gen info page**/

                map.put("patid", argVal);
                //Search bu passed in patient ID
                s = reqHan.sendGetRequestParam(ConnVars.URL_FETCH_PAT_VISIT, map);


                return s;

            }

            //Once JSON received correctly, parse and display it
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                visitShow(s);
            }
        }


                fetchPatientVisit patVisit = new fetchPatientVisit();
                patVisit.execute();
                System.out.println("Completed fetchPatientVisit method");
        }


        /**ML: This method gets the data returned from the DB in the JSON format and sets it to a variable**/
        public void visitShow(String json) {

            try {
                JSONObject jsonObject = new JSONObject(json);
                System.out.println("made json object");
                JSONArray resArr = jsonObject.getJSONArray(ConnVars.TAG_VISIT_HISTORY);
                System.out.println("made result array");


                /**ML: r_** are the values returned from the DB, resObj is the first data block
                 * returned from the JSON array, getString gets the value associated with the key
                 * that is denoted by the ConnVars tag
                 */

                while (count < resArr.length()) {
                    /**ML: gets the first data block, will repeat if necessary**/
                    JSONObject resObj = resArr.getJSONObject(count);
                    System.out.println("you got past resObj");
                    String r_patid = resObj.getString(ConnVars.TAG_VISITS_PATID);
                    System.out.println("returned pat id" + r_patid);
                    String r_visitdate = resObj.getString(ConnVars.TAG_VISITS_VISITDATE);
                    System.out.println("returned visitdate" +r_visitdate);
                    String r_reason = resObj.getString(ConnVars.TAG_VISITS_REASON);
                    System.out.println("returned reason" +r_reason);
                    String r_doctor = resObj.getString(ConnVars.TAG_VISITS_DOCTOR);
                    System.out.println("returned doctor" +r_doctor);

                    /**ML: This needs to be fixed, JSON exception occurs here because it needs to come from a table join**/
                    //String r_rxid = resObj.getString(ConnVars.TAG_VISITS_RXID);

                    try {
                        patVisitdata.add(new VisitFields(r_patid, r_visitdate, r_reason, r_doctor));
                        System.out.println(patVisitdata.get(count).D_AllData());
                    } catch(NumberFormatException nfe) {
                        System.out.println("Number format exception occurred.");
                    }

                    count++;
                    hash_count++;
                    System.out.println(hash_count);
                }
                } catch(JSONException j){
                    System.out.println("JSON Exception occurred...HEAAA");
                }

                listView = (ExpandableListView) findViewById(R.id.exp_list);
                initData();
                listAdapter = new FetchVisits_ExpListAdapter(this, listDataHeader, listHash);
                listView.setAdapter(listAdapter);
            }




            private void initData() {
            /**ML: Need to make a count variable to select all visits not just the first one (get rid of the o)**/
            int H_count = 0;
            listDataHeader = new ArrayList<>();
            listHash = new HashMap<>();

                while (H_count < hash_count) {
                    VisitFields temp1 = patVisitdata.get(H_count);

                    //This is where you set the prescription IS as the Group Item
                    listDataHeader.add(temp1.C_visitdate);

                    //This is where you set the child items of each group item
                    List<String> visit1 = new ArrayList<>();
                    visit1.add("ID de Paciente:    " + temp1.C_patid);
                    visit1.add("El Doctor:    " + temp1.C_doctor);
                    visit1.add("La Razón:   " + temp1.C_reason);
                    //visit1.add("ID de Prescripcion:   " + temp1.C_rxid);

                    //Put the data in the HashMap
                    listHash.put(listDataHeader.get(H_count), visit1);

                    H_count++;
                }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pat_gen_info:
                Intent newintent = new Intent(getBaseContext(), FetchPatientInfo.class);
                newintent.putExtra("patid", sID);
                startActivity(newintent);
                //startActivity(new Intent(this, FetchPatientInfo.class));
                return true;
            case R.id.pat_history:
                Intent newintent1 = new Intent(getBaseContext(), FetchVisits.class);
                newintent1.putExtra("patid", sID);
                startActivity(newintent1);
                //startActivity(new Intent(this, FetchVisits.class));
                return true;
            case R.id.pat_prescription:
                Intent newintent2 = new Intent(getBaseContext(), FetchPrescriptions.class);
                newintent2.putExtra("patid", sID);
                startActivity(newintent2);
                //startActivity(new Intent(this, FetchPrescriptions.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        Intent go_back_to_PGI_2 = new Intent(this, SearchAddPatients.class);
        startActivity(go_back_to_PGI_2);
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














