package ethanfortin_nicaragua.elbluffhospital.PatientInfo;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InterfaceAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.ArrayAdapter_FetchPatientGenInfo;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.Class_FetchPatientGenInfo;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;

public class SearchAddPatients extends Activity {
    ArrayList<Class_FetchPatientGenInfo> patinfo = new ArrayList();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    String patname;
    String patid;
    String address;
    String telephone;
    String gender;
    String marstat;
    String s_dob;
    Date dob;
    String s_children;
    int children;
    String s_height;
    int height;
    String s_weight;
    int weight;
    String allergies;
    String medcond;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_add_patients);

        //patientFetch();

        /** ATTENTION: This was auto-generated to implement the App Indexing API.
         See https://g.co/AppIndexing/AndroidStudio for more information.*/
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void buscar(View v) {
        EditText name_EditText = (EditText) findViewById(R.id.edit_name);
        EditText id_EditText = (EditText) findViewById(R.id.edit_ID);
        String sName = name_EditText.getText().toString();
        String sID = id_EditText.getText().toString();



        if ((sName.matches("")) & (sID.matches(""))) {
            Toast.makeText(this, "Necesitas Entrar un ID o un Nombre", Toast.LENGTH_SHORT).show();

        }

        patientFetch(sID);
        /*else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Elige el paciente");
            DialogInterface.OnClickListener patient_dialog = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            };
           builder.setItems(Nombres.toString(), );
            builder.show();*/

          /*  AlertDialog.Builder builderSingle = new AlertDialog.Builder(SearchAddPatients.this);
            builderSingle.setTitle("Elige el paciente");
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    SearchAddPatients.this,
                    android.R.layout.select_dialog_singlechoice);
            arrayAdapter.add("Pablo Himenz 8/7/1990 M 194356");
            arrayAdapter.add("Pablo Himnez 6/19/2004 M 23345");
            arrayAdapter.add("Pablo Shancez 6/1/2010  M 245674");
            arrayAdapter.add("Pablo Tarin 12/3/2015   M 264510");


            builderSingle.setNegativeButton(
                    "cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });


            builderSingle.setAdapter(
                    arrayAdapter,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent_patientData = new Intent(SearchAddPatients.this, FetchPatientInfo.class);
                            startActivity(intent_patientData);
                            /**
                             * This next line is the variable that will be used to reference the correct row
                             * in the database for the patient that was clicked
                             */
//                            String strName = arrayAdapter.getItem(which);

                   /*     }
                    });
            builderSingle.show();
        }*/


    }

    private void patientFetch(final String patid) {
        class fetch_patientinfo extends AsyncTask<Void, Class_FetchPatientGenInfo, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SearchAddPatients.this, "Buscando...", "Espera, por favor", false, false);
            }

            // Once JSON received correctly, parse and display it
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                jsonParse(s);
            }

            // In here, split between argChoice Value (1 or 2)
            protected String doInBackground(Void... params) {

                RequestHandler reqHan = new RequestHandler();
                HashMap<String, String> map = new HashMap<>();
                map.put("patid",patid);
                String s;

                s = reqHan.sendGetRequestParam (ConnVars.URL_FETCH_PATIENT_GENERAL_INFO, map);

                return s;
            }
        }
        fetch_patientinfo pinfo = new fetch_patientinfo();
        pinfo.execute();
    }

    private void jsonParse(String json_string) {

        Context context=this;
        ListView listView;
        ArrayList<Class_FetchPatientGenInfo> patGenInfo = new ArrayList();

        int totalCast, count=0;
        String patid, patname, address, telephone, gender,marstat, allergies, medcond, children, height, weight;
        int children_int, height_int, weight_int;

        JSONObject jsonObject;
        JSONArray jsonArray;
        try {

            // Make JSONObject and designate the array jsonArray to grab the array
            // that's title is "druginfo" from the received object
            jsonObject = new JSONObject(json_string);
            System.out.println("made json object: "+json_string);
            jsonArray = jsonObject.getJSONArray(ConnVars.TAG_PATIENTINFO);
            System.out.println("put stuff into jsonobject");

            //initiliaze count for while loop, strings for all data we will get from json
            //all data comes out as strings so for int values we need to cast them into int values later
            //while count is less than length of jsonarray
            while (count < jsonArray.length()) {
                //get the object put drugid into drugid ect..
                JSONObject jo = jsonArray.getJSONObject(count);
                patid= jo.getString("patid");
                patname= jo.getString("patname");
                address = jo.getString("address");
                telephone= jo.getString("telephone");
                gender = jo.getString("gender");
                marstat = jo.getString("marstat");
                allergies=jo.getString("allergies");
                medcond = jo.getString("medcond");
                children = jo.getString("children");
                height = jo.getString("height");
                weight=jo.getString("weight");


                //try to cast string into int
                try {
                    children_int = Integer.parseInt(children);
                    height_int = Integer.parseInt(height);
                    weight_int = Integer.parseInt(weight);
                    //add this data as Class_FetchAllDrugInfo to ArrayList
                    patinfo.add(new Class_FetchPatientGenInfo(patid, patname , address, telephone, gender, marstat, children_int,height_int,weight_int,allergies,medcond));

                } catch (NumberFormatException nfe) {
                    System.out.println("Number Format Exception occurred...");
                }
                //increment count
                count++;
            }

        } catch (JSONException e) {
            System.out.println("JSON Exception occurred...");
        }
        ArrayAdapter<Class_FetchPatientGenInfo> adapter = new ArrayAdapter_FetchPatientGenInfo(context, patinfo);

        //setliest view to lsitview in the xml file
        listView = (ListView) findViewById(R.id.listview_patientgeninfo);
        // listView = this.getListView();
        //turn on list view
        listView.setAdapter(adapter);
    }

    public void NuevoPaciente(View V) {
        /**Need to make dialog_patient_preccription pull data from DB not my made up stuff
         *create layout inflater and subView assign sub view to dialog xml
         * */
        LayoutInflater inflater = LayoutInflater.from(SearchAddPatients.this);
        View subView = inflater.inflate(R.layout.dialog_new_patient, null);

        //Build dialog set it to subview
        AlertDialog.Builder builderSingle1 = new AlertDialog.Builder(this);
        builderSingle1.setView(subView);

        //Set dialog        title
        builderSingle1.setTitle("Entrar Nueva Paciente");
        final EditText edit_name2 = (EditText) subView.findViewById(R.id.newdialog_edit_name);
        final EditText edit_ID2 = (EditText) subView.findViewById(R.id.newdialog_edit_ID);
        final EditText edit_adress2 = (EditText) subView.findViewById(R.id.newdialog_edit_adress);
        final EditText edit_telephone2 = (EditText) subView.findViewById(R.id.newdialog_edit_Telephone);
        final EditText edit_gender2 = (EditText) subView.findViewById(R.id.newdialog_edit_gender);
        final EditText edit_married2 = (EditText) subView.findViewById(R.id.newdialog_edit_Married);
        final EditText edit_birthday2 = (EditText) subView.findViewById(R.id.newdialog_edit_Birthdate);
        final EditText edit_children2 = (EditText) subView.findViewById(R.id.newdialog_edit_Children);
        final EditText edit_height2 = (EditText) subView.findViewById(R.id.newdialog_edit_Height);
        final EditText edit_weight2 = (EditText) subView.findViewById(R.id.newdialog_edit_Weight);
        final EditText edit_allergies2 = (EditText) subView.findViewById(R.id.newdialog_edit_Allergies);
        final EditText edit_medicalConditions2 = (EditText) subView.findViewById(R.id.newdialog_edit_MedicalConditions);
        builderSingle1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builderSingle1.setPositiveButton(
                "Crear",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        /* This is to be used for demonstration where communication
                        to database is not an option. Take out for actual DB communication.


                        boolean cancel = false;
                        View focusView = null;

                        final String s_edit_name2 = edit_name2.getText().toString();
                        final String s_edit_ID2 = edit_ID2.getText().toString();
                        final String s_edit_adress2 = edit_adress2.getText().toString();
                        final String s_edit_telephone2 = edit_telephone2.getText().toString();
                        final String s_edit_gender2 = edit_gender2.getText().toString();
                        final String s_edit_married2 = edit_married2.getText().toString();
                        final String s_edit_birthday2 = edit_birthday2.getText().toString();
                        final String s_edit_children2 = edit_children2.getText().toString();
                        final String s_edit_height2 = edit_height2.getText().toString();
                        final String s_edit_weight2 = edit_weight2.getText().toString();
                        final String s_edit_allergies2 = edit_allergies2.getText().toString();
                        final String s_edit_medicalConditions2 = edit_medicalConditions2.getText().toString();

                       if(s_edit_name2.matches("")){
                           Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                           return;
                       }
                        if(s_edit_ID2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(s_edit_adress2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(s_edit_telephone2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(s_edit_gender2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(s_edit_married2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(s_edit_birthday2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(s_edit_children2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(s_edit_height2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(s_edit_weight2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(s_edit_allergies2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(s_edit_medicalConditions2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else{
                            Toast toast = Toast.makeText(getApplicationContext(), "Paciente guarde.", Toast.LENGTH_LONG);
                            toast.show();

                            Intent i = new Intent(getApplicationContext(), SearchAddPatients.class);
                            startActivity(i);
                        }
                        */


                        /***This is the code needed for db communication.***/

                        patname = edit_name2.toString();
                        patid = edit_ID2.toString();
                        address = edit_adress2.toString();
                        telephone = edit_telephone2.toString();
                        gender = edit_gender2.toString();
                        marstat = edit_married2.toString();
                        s_dob = edit_birthday2.toString();
                        s_children = edit_children2.toString();
                        s_height = edit_height2.toString();
                        s_weight = edit_weight2.toString();
                        allergies = edit_allergies2.toString();
                        medcond = edit_medicalConditions2.toString();

                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            dob = dateFormat.parse(s_dob);
                            children = Integer.decode(s_children);
                            height = Integer.decode(s_height);
                            weight = Integer.decode(s_weight);
                        } catch (Exception e) {
                        }
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setText("go catz");
                        toast.show();


                        Toast toasty = new Toast(getApplicationContext());
                        toasty.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toasty.setDuration(Toast.LENGTH_LONG);
                        toasty.setText("if you see this, info was sent to DB!!!!");
                        toasty.show();

                        //Push to DB including ones not above but in Dialog
                        dialog.dismiss();

                    }
                });
        builderSingle1.show();
    }

    @Override
    public void onBackPressed() {
        Intent go_back = new Intent(this, DoctorMain.class);
        startActivity(go_back);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        client.disconnect();
    }

}