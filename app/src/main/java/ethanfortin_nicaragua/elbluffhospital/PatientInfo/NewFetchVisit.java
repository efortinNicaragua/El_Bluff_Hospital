package ethanfortin_nicaragua.elbluffhospital.PatientInfo;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.PatientinfoAdapter;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.PatientinfoFields;
import ethanfortin_nicaragua.elbluffhospital.MainMenu;
import ethanfortin_nicaragua.elbluffhospital.PatientInfo.SearchAddPatients;
import ethanfortin_nicaragua.elbluffhospital.PatientInfo.FetchPatientInfo;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;


public class NewFetchVisit extends AppCompatActivity {
    private DatePicker et_visitDate;
    private EditText et_docName;
    private EditText et_height;
    private EditText et_weight;
    private EditText et_allergies;
    private EditText et_ill;
    private EditText et_meds;
//    private EditText et_patId;

    String sID;

    int error;
    AlertDialog db_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_fetch_visit);

        Intent intent = getIntent();
        sID = intent.getStringExtra("patid");
        Log.d("patid", sID);
        System.out.println("patid is: " + sID);
    }

    public void newVisit(View V) {

        // every edit text is matched to a vairable ad every variable (in string s drug name)
        et_visitDate = (DatePicker) findViewById(R.id.visitDate);
        et_docName = (EditText) findViewById(R.id.docName);
        et_height = (EditText) findViewById(R.id.height);
        et_weight = (EditText) findViewById(R.id.weight);
        et_allergies = (EditText) findViewById(R.id.allergies);
        et_ill = (EditText) findViewById(R.id.illness);
        et_meds = (EditText) findViewById(R.id.medicines);
//        et_patId = (EditText) findViewById(R.id.patIdagain);

        boolean cancel = false;
        View focusView = null;

        // every field needs to be found by id
        // Verify pat name
        // Verify pat birthday
        //Change format of date picker!
        //Get int of day, month, year
        int day_visitDate = et_visitDate.getDayOfMonth();     //turn the date into a string the database can handle
        int month_visitDate = et_visitDate.getMonth() + 1;
        int year_visitDate = et_visitDate.getYear();

        //Turn day moth year into individual strings and concatonate into what suits you.
        String s_day_visitDate, s_month_visitDate, s_year_visitDate, s_visitDate;

        //if value of day or month
        if (day_visitDate < 10) {
            s_day_visitDate = "0" + String.valueOf(day_visitDate);
        } else {
            s_day_visitDate = String.valueOf(day_visitDate);
        }
        if (month_visitDate < 10) {
            s_month_visitDate = "0" + String.valueOf(month_visitDate);
        } else {
            s_month_visitDate = String.valueOf(month_visitDate);
        }
        s_year_visitDate = String.valueOf(year_visitDate);
        //put these all into string called bday with format that PHP prefers;
        s_visitDate = s_year_visitDate + "-" + s_month_visitDate + "-" + s_day_visitDate;
        // System.out.println("Here is the format and value of individual date values " + s_day_patBday + " " + s_month_patBday + " "+ s_year_patBday);
        // System.out.println("Here is the format and value of the concat date " + s_patBday);
        // Verify doctor name
        String s_docName = et_docName.getText().toString();           // doing this for every text view
        if (TextUtils.isEmpty(s_docName)) {
            et_docName.setError("Not Valid");
            focusView = et_docName;
            cancel = true;
        } else {
            et_docName.setError(null, null);
        }
        // Verify pat height
        String s_height = et_height.getText().toString();           // doing this for every text view
        if (TextUtils.isEmpty(s_height)) {
            et_height.setError("Not Valid");
            focusView = et_height;
            cancel = true;
        } else {
            et_height.setError(null, null);
        }
        // Verify pat weight
        String s_weight = et_weight.getText().toString();           // doing this for every text view
        if (TextUtils.isEmpty(s_weight)) {
            et_weight.setError("Not Valid");
            focusView = et_weight;
            cancel = true;
        } else {
            et_weight.setError(null, null);
        }
        // Verify allergies
        String s_allergies = et_allergies.getText().toString();           // doing this for every text view
        if (TextUtils.isEmpty(s_allergies)) {
            et_allergies.setError("Not Valid");
            focusView = et_allergies;
            cancel = true;
        } else {
            et_allergies.setError(null, null);
        }
        // Verify pat illness
        String s_ill = et_ill.getText().toString();           // doing this for every text view
        if (TextUtils.isEmpty(s_ill)) {
            et_ill.setError("Not Valid");
            focusView = et_ill;
            cancel = true;
        } else {
            et_ill.setError(null, null);
        }
        // Verify pat meds
        String s_meds = et_meds.getText().toString();           // doing this for every text view
        if (TextUtils.isEmpty(s_meds)) {
            et_meds.setError("Not Valid");
            focusView = et_meds;
            cancel = true;
        } else {
            et_meds.setError(null, null);
        }

/*        // Verify pat ID
        String s_patId = et_patId.getText().toString();           // doing this for every text view
        if (TextUtils.isEmpty(s_patId)) {
            et_patId.setError("Not Valid");
            focusView = et_patId;
            cancel = true;
        } else {
            et_patId.setError(null, null);
        }
*/
        System.out.println("Here patID " + sID);
        System.out.println("Here visitDate " + s_visitDate);
        System.out.println("Here docName " + s_docName);
        System.out.println("Here height " + s_height);
        System.out.println("Here weight " + s_weight);
        System.out.println("Here allergies " + s_allergies);
        System.out.println("Here ill " + s_ill);
        System.out.println("Here meds " + s_meds);

        if (cancel) {
            focusView.requestFocus();           //pops a message on everythign that is blank
        } else {          //if cancel is false then add the shipment

            addNewVisit(s_visitDate, s_docName, s_height, s_weight, s_allergies, s_ill, s_meds);   // all the string values from before
        }
    }

    private void addNewVisit(final String visitDate, final String docName, final String height, final String weight, final String allergies, final String ill, final String meds) {
        class get_addNewVisit extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {     //shows the loadng screen
                super.onPreExecute();
                loading = ProgressDialog.show(NewFetchVisit.this, "Buscando...", "Espera, por favor", false, false);
            }

            // Once JSON received correctly, parse and display it
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);     //all that json stuff
                loading.dismiss();
                System.out.println("Here is s " + s);
                int errortemp = jsonParse(s);
                System.out.println("Here is errortemp " + errortemp);
                Log.d("Test1", "Error temp " + errortemp);
                if (errortemp == 0) {      //return errortemp for each php file, associated with anything that goes wrong
                    db_message = new AlertDialog.Builder(NewFetchVisit.this).create();
                    db_message.setTitle("Accion Termino");
                    db_message.setMessage("Nuevo Visit Creado!"); //it all worked youre all good
                    db_message.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent newvis = new Intent(NewFetchVisit.this,  FetchVisits.class);
                                    Bundle b = new Bundle();
                                    b.putString("patid", sID); //Your id
                                    newvis.putExtras(b); //Put your id to your next Intent
                                    startActivity(newvis);
                                    finish();
                                }
                            });
                    db_message.show();
                } else if (errortemp == 1) {                 //DO WE NEED THIS ERRORTEMP==1 THING??
                    db_message = new AlertDialog.Builder(NewFetchVisit.this).create();
                    db_message.setTitle("Missing Information"); //it finished with some errors
                    db_message.setMessage("No Nueva Visit Creado");
                    db_message.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    db_message.show();
                } else {
                    db_message = new AlertDialog.Builder(NewFetchVisit.this).create();
                    db_message.setTitle("ERRORES! Uknown");    //there are waaaaay too many errors (oops!)
                    db_message.setMessage("Habia errores creando el nuevo paciente.\nAsegurar que el paciente no exista.");
                    db_message.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    db_message.show();
                }
            }

            // In here, split between argChoice Value (1 or 2)
            protected String doInBackground(Void... params) {

                RequestHandler reqHan = new RequestHandler();
                HashMap<String, String> map = new HashMap<>();  //variables must have two pieces
                map.put("patid", sID);
                map.put("visitdate", visitDate);
                map.put("doctor", docName);
                map.put("height", height);
                map.put("weight", weight);
                map.put("allergies", allergies);
                map.put("illness", ill);
                map.put("meds", meds);
                String s;

                s = reqHan.sendPostRequest(ConnVars.URL_ADD_VH_ROW, map);
                // System.out.println("do in bg s " + s);
                return s;
            }
        }

        get_addNewVisit myVisit = new get_addNewVisit();
        myVisit.execute();
    }

    private int jsonParse(String json_string) { //where you get the values back

        Context context = this;

        //int count = 0;
        String s_VH_ErrorMessages;
        int int_VH_ErrorMessages = 500;

        JSONObject jsonObject;
        //JSONArray jsonArray;

        try {

            // Make JSONObject and designate the array jsonArray to grab the array
            jsonObject = new JSONObject(json_string);
            System.out.println("made json object: " + json_string);

            s_VH_ErrorMessages = jsonObject.getString("VH_ErrorMessages");
            //s_VH_ErrorMessages = jo.getString("VH_ErrorMessages");
            //count++;

            System.out.println("here is VH_ErrorMessages " + s_VH_ErrorMessages);

            int_VH_ErrorMessages = Integer.parseInt(s_VH_ErrorMessages);

        } catch (JSONException e) {

            System.out.println("JSON Exception on try to get values for errors");

        }

        if (int_VH_ErrorMessages == 200) {      //successful
            Log.d("Test1", "Case1");
            error = 0;
            return error;
        } else if (int_VH_ErrorMessages == 400) { //bad request
            Log.d("Test1", "Case2");
            error = 1;
            return error;
        } else {                                  //failed
            Log.d("Test1", "Case3");
            error = 2;
            Log.d("Test1", "Error=" + error);
            return error;
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("onBackPressed", "backpPressed");
        Intent go_back = new Intent(this, SearchAddPatients.class);
        startActivity(go_back);
    }

    public void AddNewPrescription2(View V) {
        Intent newPat = new Intent(NewFetchVisit.this, AddNewPrescription2.class);
        startActivity(newPat);
    }

    public void FakePDF(View V) {
        Intent newPat = new Intent(NewFetchVisit.this, FakePDF.class);
        startActivity(newPat);

    }
}



