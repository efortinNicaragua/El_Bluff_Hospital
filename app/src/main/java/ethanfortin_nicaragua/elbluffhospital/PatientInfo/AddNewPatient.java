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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

//import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.PatientinfoAdapter;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.PatientinfoFields;
import ethanfortin_nicaragua.elbluffhospital.PatientInfo.SearchAddPatients;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;

public class AddNewPatient extends AppCompatActivity {
    private EditText et_patName;
    private EditText et_patId;
    private DatePicker et_patBday;
    private EditText et_patAddress;
    private EditText et_patPhone;
    private EditText et_patGender;
    private EditText et_patMarriage;

    int error;
    AlertDialog db_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);

    }

    public void createPatient(View V) {

        // every edit text is matched to a vairable ad every variable (in string s drug name)
        et_patName = (EditText) findViewById(R.id.patName);
        et_patId = (EditText) findViewById(R.id.patID);
        et_patBday = (DatePicker) findViewById(R.id.bdayPicker);
        et_patAddress = (EditText) findViewById(R.id.patAddress);
        et_patPhone = (EditText) findViewById(R.id.patPhone);
        et_patGender = (EditText) findViewById(R.id.patGender);
        et_patMarriage = (EditText) findViewById(R.id.patMarriage);


        boolean cancel = false;
        View focusView = null;

        // every field needs to be found by id
        // Verify pat name
        String s_patName = et_patName.getText().toString();       // we get this from the fields before
        if (TextUtils.isEmpty(s_patName)) {                         // see if it is empty
            et_patName.setError("Not Valid");                      // if empty set error (do not access data base)
            focusView = et_patName;                                //lets you know that it is empty and need to be filled
            cancel = true;
        } else {
            et_patName.setError(null, null);
        }
        // Verify pat id
        String s_patId = et_patId.getText().toString();           // doing this for every text view
        if (TextUtils.isEmpty(s_patId)) {
            et_patId.setError("Not Valid");
            focusView = et_patId;
            cancel = true;
        } else {
            et_patId.setError(null, null);
        }
        // Verify pat birthday
        //Change format of date picker!
        //Get int of day, month, year
        int day_patBday = et_patBday.getDayOfMonth();     //turn the date into a string the database can handle
        int month_patBday = et_patBday.getMonth() + 1;
        int year_patBday = et_patBday.getYear();

        //Turn day moth year into individual strings and concatonate into what suits you.
        String s_day_patBday, s_month_patBday, s_year_patBday, s_patBday;

        //if value of day or month
        if (day_patBday < 10) {
            s_day_patBday = "0" + String.valueOf(day_patBday);
        } else {
            s_day_patBday = String.valueOf(day_patBday);
        }
        if (month_patBday < 10) {
            s_month_patBday = "0" + String.valueOf(month_patBday);
        } else {
            s_month_patBday = String.valueOf(month_patBday);
        }
        s_year_patBday = String.valueOf(year_patBday);
        //put these all into string called bday with format that PHP prefers;
        s_patBday = s_year_patBday + "-" + s_month_patBday + "-" + s_day_patBday;
        // System.out.println("Here is the format and value of individual date values " + s_day_patBday + " " + s_month_patBday + " "+ s_year_patBday);
        // System.out.println("Here is the format and value of the concat date " + s_patBday);

        // Verify pat address
        String s_patAddress = et_patAddress.getText().toString();           // doing this for every text view
        if (TextUtils.isEmpty(s_patAddress)) {
            et_patAddress.setError("Not Valid");
            focusView = et_patAddress;
            cancel = true;
        } else {
            et_patAddress.setError(null, null);
        }

        // Verify pat phone
        String s_patPhone = et_patPhone.getText().toString();           // doing this for every text view
        if (TextUtils.isEmpty(s_patPhone)) {
            et_patPhone.setError("Not Valid");
            focusView = et_patPhone;
            cancel = true;
        } else {
            et_patPhone.setError(null, null);
        }

        // Verify pat Gender
        String s_patGender = et_patGender.getText().toString();           // doing this for every text view
        if (TextUtils.isEmpty(s_patGender)) {
            et_patGender.setError("Not Valid");
            focusView = et_patGender;
            cancel = true;
        } else {
            et_patGender.setError(null, null);
        }

        // Verify pat marriage status
        String s_patMarriage = et_patMarriage.getText().toString();           // doing this for every text view
        if (TextUtils.isEmpty(s_patMarriage)) {
            et_patMarriage.setError("Not Valid");
            focusView = et_patMarriage;
            cancel = true;
        } else {
            et_patMarriage.setError(null, null);
        }

        System.out.println("Here patID " + s_patId);
        System.out.println("Here patName " + s_patName);
        System.out.println("Here address " + s_patAddress);
        System.out.println("Here telephone " + s_patPhone);
        System.out.println("Here bday " + s_patBday);
        System.out.println("Here gender " + s_patGender);
        System.out.println("Here marriage " + s_patMarriage);



        if (cancel) {
            focusView.requestFocus();           //pops a message on everythign that is blank
        } else {          //if cancel is false then add the shipment

            addNewPatient(s_patName, s_patId, s_patAddress, s_patPhone, s_patBday, s_patGender, s_patMarriage);   // all the string values from before
        }
    }

    private void addNewPatient(final String patName, final String patId, final String patAddress, final String patPhone, final String patBday, final String patGender, final String patMarriage) {
        class get_addNewPatient extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {     //shows the loadng screen
                super.onPreExecute();
                loading = ProgressDialog.show(AddNewPatient.this, "Buscando...", "Espera, por favor", false, false);
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
                    db_message = new AlertDialog.Builder(AddNewPatient.this).create();
                    db_message.setTitle("Accion Termino");
                    db_message.setMessage("Nuevo Paciente Creado!"); //it all worked youre all good
                    db_message.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    db_message.show();
                } else if (errortemp == 1) {                 //DO WE NEED THIS ERRORTEMP==1 THING??
                    db_message = new AlertDialog.Builder(AddNewPatient.this).create();
                    db_message.setTitle("Missing Information"); //it finished with some errors
                    db_message.setMessage("No Nueva Patiente Creado");
                    db_message.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    db_message.show();
                } else {
                    db_message = new AlertDialog.Builder(AddNewPatient.this).create();
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
                map.put("patid", patId);
                map.put("patname", patName);
                map.put("address", patAddress);
                map.put("telephone", patPhone);
                map.put("birthday", patBday);
                map.put("gender", patGender);
                map.put("civil_status", patMarriage);
                String s;

                s = reqHan.sendPostRequest(ConnVars.URL_ADD_PATIENTINFO_ROW, map);
                // System.out.println("do in bg s " + s);
                return s;
            }
        }

        get_addNewPatient patient = new get_addNewPatient();
        patient.execute();
    }

    private int jsonParse(String json_string) { //where you get the values back

        Context context = this;

        //int count = 0;
        String s_New_ErrorMessages;
        int int_New_ErrorMessages = 500;

        JSONObject jsonObject;
        //JSONArray jsonArray;

        try {

            // Make JSONObject and designate the array jsonArray to grab the array
            // that's title is "druginfo" from the received object
            jsonObject = new JSONObject(json_string);
            System.out.println("made json object: " + json_string);
            //jsonArray = jsonObject.getJSONArray(ConnVars.TAG_NEWPAT_ERRORMESSAGES);
            //System.out.println("jsonArray: " + jsonArray);

            // initiliaze count for while loop, strings for all data we will get from json
            // all data comes out as strings so for int values we need to cast them into int values later
            // while count is less than length of jsonarray
            // while (count < jsonArray.length()) {
            // get the object put drugid into drugid ect..


            //JSONObject jo = jsonArray.getJSONObject(count);
            //JSONObject jo = jsonObject;

            s_New_ErrorMessages = jsonObject.getString("New_ErrorMessages");
            //s_New_ErrorMessages = jo.getString("New_ErrorMessages");
            //count++;

            System.out.println("here is New_ErrorMessages " + s_New_ErrorMessages);

            int_New_ErrorMessages = Integer.parseInt(s_New_ErrorMessages);

        } catch (JSONException e) {

            System.out.println("JSON Exception on try to get values for errors");

        }

        if (int_New_ErrorMessages == 200) {      //successful
            Log.d("Test1", "Case1");
            error = 0;
            return error;
        }
        else if (int_New_ErrorMessages == 400) { //bad request
            Log.d("Test1", "Case2");
            error = 1;
            return error;
        }
        else {                                  //failed
            Log.d("Test1", "Case3");
            error = 2;
            Log.d("Test1", "Error=" + error);
            return error;
        }
/*
        int count = 0;
        String new_patient = "1", patient_success = "1", info_success = "1";
        int int_new_patient = 0;
        int int_patient_success = 0;
        int int_info_success = 0; //?????

        JSONObject jsonObject;
        JSONArray jsonArray;
        try {

            // Make JSONObject and designate the array jsonArray to grab the array
            // that's title is "druginfo" from the received object
            jsonObject = new JSONObject(json_string);
            System.out.println("made json object: " + json_string);
            jsonArray = jsonObject.getJSONArray(ConnVars.TAG_NEWPAT_ERRORMESSAGES);
            System.out.println("jsonArray: " + jsonArray);

            //initiliaze count for while loop, strings for all data we will get from json
            //all data comes out as strings so for int values we need to cast them into int values later
            //while count is less than length of jsonarray
            // while (count < jsonArray.length()) {
            //get the object put drugid into drugid ect..


            JSONObject jo = jsonArray.getJSONObject(count);


            new_patient = jo.getString("new_patient");
            count++;

            jo = jsonArray.getJSONObject(count);
            patient_success = jo.getString("patient_success");
            count++;

            jo = jsonArray.getJSONObject(count);
            info_success = jo.getString("info_success");

            System.out.println("here are new_patient,patient_success,info_success: " + new_patient + " " + patient_success + " " + info_success);

            int_new_patient = Integer.parseInt(new_patient);
            int_patient_success = Integer.parseInt(patient_success);
            int_info_success = Integer.parseInt(info_success);


            //increment count
            // count++;
            //}

        } catch (JSONException e) {

            System.out.println("JSON Exception on try to get values for errors");

        }

        if (int_new_patient == 0 && int_info_success == 1 && int_patient_success == 1) {

            Log.d("Test1", "Case1");
            error = 0;
            return error;
        } else if (int_new_patient == 1 && int_info_success == 1 && int_patient_success == 1) {
            Log.d("Test1", "Case2");
            error = 1;
            return error;
        } else {
            Log.d("Test1", "Case3");
            error = 2;
            Log.d("Test1", "Error=" + error);
            return error;
        }
    */
    }

}
