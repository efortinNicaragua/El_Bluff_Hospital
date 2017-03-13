package ethanfortin_nicaragua.elbluffhospital.PatientInfo;

import android.app.Activity;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.ArrayAdapter_FetchPrescriptions;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.Class_FetchPrescriptions;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;

public class FetchPrescriptions extends Activity {

    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_prescriptions);

        /**ML: Testing to see if the data that comes back is correct**/


        ArrayList< Class_FetchPrescriptions> patRXdata = new ArrayList();
        ArrayAdapter<Class_FetchPrescriptions> adapter = new ArrayAdapter_FetchPrescriptions(this, patRXdata);
        ListView LV = (ListView) findViewById(R.id.LV_fetchPrescriptions);
        LV.setAdapter(adapter);



        /**ML: Use FAB to create a new prescription **/
        final FloatingActionButton addRx = (FloatingActionButton) findViewById(R.id.fab_add);

        addRx.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(FetchPrescriptions.this);
                final View subView = inflater.inflate(R.layout.dialog_new_prescription, null);

                // final EditText entryDate = (EditText) subView.findViewById(R.id.);
                final EditText entryReason = (EditText) subView.findViewById(R.id.etReason);
                final EditText entryDoctor = (EditText) subView.findViewById(R.id.etDoctor);
                final EditText entryDir = (EditText) subView.findViewById(R.id.etDirections);


                // Get Current Date
                // SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                // Date date = new Date();
                // entryDate.setText(dateFormat.format(date));

                final AlertDialog alertDialogBuilder = new AlertDialog.Builder(context)
                        .setTitle("Anadir la prescripción nueva")
                        .setView(subView)
                        .setMessage("Ingrese la informacion de la prescripción")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Verify reason
                                String s_razon = entryReason.getText().toString();
                                if (TextUtils.isEmpty(s_razon)) {
                                    //entryReason.setError("Por Favor, llena toda la informacion.");
                                    //int duration = Toast.LENGTH_LONG;
                                    //Context context = getApplicationContext();
                                    String text1 = "Por Favor, llena toda la información.";
                                    Toast toast1 = Toast.makeText(getApplicationContext(), text1, Toast.LENGTH_LONG);
                                    toast1.show();
                                }


                                // Verify doctor
                                String s_doc = entryDoctor.getText().toString();
                                if (TextUtils.isEmpty(s_doc)) {
                                    //entryDoctor.setError("Por Favor, llena toda la informacion.");
                                    // int duration = Toast.LENGTH_LONG;
                                    // Context context = getApplicationContext();
                                    String text1 = "Por Favor, llena toda la información.";
                                    Toast toast1 = Toast.makeText(getApplicationContext(), text1, Toast.LENGTH_LONG);
                                    toast1.show();
                                }

                                // Verify Directions
                                String s_dir = entryDir.getText().toString();
                                if (TextUtils.isEmpty(s_doc)) {
                                    //entryDoctor.setError("Por Favor, llena toda la informacion.");
                                    // int duration = Toast.LENGTH_LONG;
                                    // Context context = getApplicationContext();
                                    String text1 = "Por Favor, llena toda la información.";
                                    Toast toast1 = Toast.makeText(getApplicationContext(), text1, Toast.LENGTH_LONG);
                                    toast1.show();
                                }

                                //Set confirmation toast

                                if(entryReason.getText().toString().trim().length() > 0
                                        && entryDoctor.getText().toString().trim().length() > 0
                                        && entryDir.getText().toString().trim().length() > 0) {
                                    Toast toast2 = Toast.makeText(getApplicationContext(), "La prescripción se ha guardado.", Toast.LENGTH_LONG);
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

    public void onClick(View V){

        //Get patient ID
        /**ML: This would be from the pop up selection dialog, here is hardcoded**/
        String sID = "patid1";

        //Search by patient ID
        /**ML: Option to add a second case (argChoice) which searches by patient name**/
        System.out.println("Searching by patient ID");
        patientRXFetch(sID,1);
    }

    /** ML: This method is accessed from inside the onClick**/
    private void patientRXFetch(final String argVal, final int argChoice){

        class fetchPatientRX extends AsyncTask<Void, Void, String> {

            //In here, get the information from the user's selection
            protected String doInBackground(Void... params){

                RequestHandler reqHan = new RequestHandler();
                HashMap<String, String> map = new HashMap<>();
                String s;

                /**ML- argVal needs to be accesed from user selection on dialog with patients, done in onClick method**/

                switch (argChoice){

                    case 1:
                        map.put("patid", argVal);
                        //search by user selection from pop up menu, map value should be key value
                        s = reqHan.sendGetRequestParam(ConnVars.URL_FETCH_PAT_RX, map);
                        break;

                    default:
                        System.out.println("Default -- search did not work: argChoice == " + argChoice);
                        s = "badMethod";
                        break;
                }

                return s;
            }

            //Once JSON received correctly, parse and display it
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                rxShow(s);
            }
        }

        fetchPatientRX patRX = new fetchPatientRX();
        patRX.execute();
    }

    /**ML: This method gets the data returned from the DB in the JSON format and sets it to a variable**/
    public void rxShow(String json){

        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray resArr = jsonObject.getJSONArray(ConnVars.TAG_PRESCRIPTIONS);

            /**ML: gets  the first data block, will repeat if necessary**/
            JSONObject resObj = resArr.getJSONObject(0);

            /**ML: r_** are the values returned from the DB, resObj is the first data block
             * returned from the JSON array, getString gets the value associated with the key
             * that is denoted by the ConnVars tag
             */

            String r_rxID = resObj.getString(ConnVars.TAG_PRESCRIPTIONS_RXID);
            String r_drugID = resObj.getString(ConnVars.TAG_PRESCRIPTIONS_DRUGID);
            String r_transDate = resObj.getString(ConnVars.TAG_PRESCRIPTIONS_TRANSDATE);
            String r_quantity = resObj.getString(ConnVars.TAG_PRESCRIPTIONS_QUANTITY);
            String r_patID = resObj.getString(ConnVars.TAG_PRESCRIPTIONS_PATID);
            String r_directions = resObj.getString(ConnVars.TAG_PRESCRIPTIONS_DIRECTIONS);
            String r_duration = resObj.getString(ConnVars.TAG_PRESCRIPTIONS_DURATION);
            String r_doctor = resObj.getString(ConnVars.TAG_PRESCRIPTIONS_DOCTOR);
            String r_symptoms = resObj.getString(ConnVars.TAG_PRESCRIPTIONS_SYMPTOMS);
            String r_drugName = resObj.getString(ConnVars.TAG_PRESCRIPTIONS_DRUGNAME);


        } catch (JSONException j){
            System.out.println("JSON Exception occurred...HEAAAA");
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
        Intent go_back_to_PGI = new Intent(this, FetchPatientInfo.class);
        startActivity(go_back_to_PGI);
    }









}

    /*public class DataProvider {
        public HashMap<String, List<String> > getInfo()
        {
            HashMap<String, List<String>> rxHeaders = new HashMap<String, List<String>>();

            ArrayList<Class_FetchPrescriptions> rx1 = new ArrayList<Class_FetchPrescriptions>();

            String RXId, DrugId, TransDate, Quantity, PatID, Directions, Duration, Doctor, Symptoms, DrugName;

            //This is fake data used for demonstrations
            *//*rx1.add("Médico:   Maynor");
            rx1.add("Droga:   Tylenol");
            rx1.add("Cantidad:   24");
            rx1.add("Duración:   Dos semanas");
            rx1.add("Razón:   Resfriado");
            rx1.add("Direcciónes:   Haga una píldora cada mañana");
            rxHeaders.put("2017-03-02 :: Tylenol",rx1);*//*

            //Add real data inside here
            RXId=textViewRXId.toString();
            DrugId = textViewDrugId.toString();
            TransDate = textViewTransDate.toString();
            Quantity = textViewQuantity.toString();
            PatID = textViewPatID.toString();
            Directions = textViewDirections.toString();
            Duration = textViewDuration.toString();
            Doctor = textViewDoctor.toString();
            Symptoms = textViewSymptoms.toString();
            DrugName = textViewDrugName.toString();


            rx1.add(RXId, DrugId, TransDate, Quantity, PatID, Directions, Duration, Doctor, Symptoms, DrugName);


            *//* ML- tried this orginally, didn't work
            rx1.add(textViewRXId.toString());
            rx1.add(textViewDrugId.toString());
            rx1.add(textViewTransDate.toString());
            rx1.add(textViewQuantity.toString());
            rx1.add(textViewPatID.toString());
            rx1.add(textViewDirections.toString());
            rx1.add(textViewDuration.toString());
            rx1.add(textViewDoctor.toString());
            rx1.add(textViewSymptoms.toString());
            rx1.add(textViewDrugName.toString());*//*


            //List<String> rx2 = new ArrayList<String>();

            //This is fake data used for demonstrations
            *//*rx2.add("Médico:   Ethan");
            rx2.add("Droga:   Nyquil");
            rx2.add("Cantidad:   14");
            rx2.add("Duración:   Una semana");
            rx2.add("Razón:   Resfriado");
            rx2.add("Direcciónes:   Haga dos píldoras cada mañana");
            rxHeaders.put("2017-06-04 :: Nyquil",rx2);*//*

            //Add real data inside here

            //List<String> rx3 = new ArrayList<String>();


            //This is fake data used for demonstration
            *//*rx3.add("Médico:   Ethan");
            rx3.add("Droga:   Ibuprofin");
            rx3.add("Cantidad:   7");
            rx3.add("Duración:   Una semana");
            rx3.add("Razón:   dolor de cabeza");
            rx3.add("Direcciónes:   Haga una píldora cada noche");
            rxHeaders.put("2017-12-13 :: Ibuprofin",rx3);*//*

            //Add real data inside here

            //List<String> rx4 = new ArrayList<String>();

            //This is fake data used for demonstration
            *//*rx4.add("Médico:   Maddie");
            rx4.add("Droga:   Advil");
            rx4.add("Cantidad:   7");
            rx4.add("Duración:   Una semana");
            rx4.add("Razón:   dolor de cabeza");
            rx4.add("Direcciónes:   Haga una píldora cada noche");
            rxHeaders.put("2017-08-23 :: Advil",rx4);*//*

            //Add real data inside here

            return rxHeaders;
        }

    }*/







