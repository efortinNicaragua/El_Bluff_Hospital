package ethanfortin_nicaragua.elbluffhospital;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PatientInfo extends AppCompatActivity {

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
    Context context= this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void buscar(View v) {
        EditText name_EditText = (EditText) findViewById(R.id.edit_name);
        EditText id_EditText = (EditText) findViewById(R.id.edit_ID);
        String sName = name_EditText.getText().toString();
        String sID = id_EditText.getText().toString();
        String[] Nombres =
                {
                        "Pablo Sanchez 9/8/1991 M 0119235",
                        "Mario Lugio   1/2/1874 M 1239450",
                        "Dr. Pali      4/5/1990 M 12345069"
                };


        if ((sName.matches("")) & (sID.matches(""))) {
            Toast.makeText(this, "Necesitas Entrar un ID o un Nombre", Toast.LENGTH_SHORT).show();
        } else {
            /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Elige el paciente");
            DialogInterface.OnClickListener patient_dialog = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            };
           builder.setItems(Nombres.toString(), );
            builder.show();*/

            AlertDialog.Builder builderSingle = new AlertDialog.Builder(PatientInfo.this);
            builderSingle.setTitle("Elige el paciente");
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    PatientInfo.this,
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
                            Intent intent_patientData = new Intent(PatientInfo.this, NewPatientGenInfo.class);
                            startActivity(intent_patientData);
                            /**
                             * This next line is the variable that will be used to reference the correct row
                             * in the database for the patient that was clicked
                             */
//                            String strName = arrayAdapter.getItem(which);

                        }
                    });
            builderSingle.show();
        }


    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("PatientInfo Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    public void NuevoPaciente(View V) {
        /**Need to make dialog_patient_preccription pull data from DB not my made up stuff
        *create layout inflater and subView assign sub view to dialog xml
         * */
        LayoutInflater inflater = LayoutInflater.from(PatientInfo.this);
        View subView = inflater.inflate(R.layout.dialog_new_pacient, null);

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
                         */

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

                            Intent i = new Intent(getApplicationContext(), PatientInfo.class);
                            startActivity(i);
                        }



                        /*This is the code needed for db communication.

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

                        add_patient_info_row add_patient_info_row= new add_patient_info_row(context);
                        add_patient_info_row.execute();*/

                        //Push to DB including ones not above but in Dialog
                        dialog.dismiss();

                    }
                });
        builderSingle1.show();
    }

    @Override
    public void onBackPressed(){
        Intent go_back = new Intent(this, DoctorMain.class);
        startActivity(go_back);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public class add_patient_info_row extends AsyncTask<Void, Class_add_patient_info_row, String> {

        Context context;
        AlertDialog alertDialog;
        String json_string;
        JSONObject jsonObject;
        JSONArray jsonArray;
        LayoutInflater inflater;
        ArrayList<Class_FetchAllDrugInfo> druginfo_data = new ArrayList();

        public add_patient_info_row(Context ctx) {
            context = ctx;

        }

        @Override
        protected String doInBackground(Void... parms) {
            String link = "http://192.168.0.101/android_connect/fetch_druginfo_all.php"; //192.168.0.100
            String data;
            String fail = "fail";
            try {
                //Create and open the URL connection
                URL url = new URL(link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");

                //setDoOutput allows you to access PHP server?
                //setDoInput lets you to recieve data from PHP server?
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                //OutputStream Writer is how you write the data along with the connection request
                OutputStream outputStream = httpURLConnection.getOutputStream();

                //BufferedWriter lets you write to the PHP clasuse
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                //This is how you send JSON values along with the request
                String post_data = URLEncoder.encode("patname", "UTF-8") + "=" + URLEncoder.encode(patname, "UTF-8") + "&"
                        + URLEncoder.encode("patid", "UTF-8") + "=" + URLEncoder.encode(patid, "UTF-8");// + "&"
                        /*+ URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8") + "&"
                        + URLEncoder.encode("telephone", "UTF-8") + "=" + URLEncoder.encode(telephone, "UTF-8") + "&"
                        + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8") + "&"
                        + URLEncoder.encode("marstat", "UTF-8") + "=" + URLEncoder.encode(marstat, "UTF-8") + "&"
                        + URLEncoder.encode("dob", "UTF-8") + "=" + URLEncoder.encode(s_dob, "UTF-8") + "&"
                        + URLEncoder.encode("children", "UTF-8") + "=" + URLEncoder.encode(s_children, "UTF-8") + "&"
                        + URLEncoder.encode("height", "UTF-8") + "=" + URLEncoder.encode(s_height, "UTF-8") + "&"
                        + URLEncoder.encode("weight", "UTF-8") + "=" + URLEncoder.encode(s_weight, "UTF-8") + "&"
                        + URLEncoder.encode("allergies", "UTF-8") + "=" + URLEncoder.encode(allergies, "UTF-8") + "&"
                        + URLEncoder.encode("medoncd", "UTF-8") + "=" + URLEncoder.encode(medcond, "UTF-8");*/

                //bufferedWriter writes data
                bufferedWriter.write(post_data);

                //Deletes data, closes everything
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //we will store result in string builder
                StringBuilder sb = new StringBuilder();
                String result = "";

                //Input from PHP
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                //Writing data from JSON in to result(string) and sb(String Builder)
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                    sb.append(line);
                }

                //Closing all things we opened
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                //Turning StringBuilder to JSONObject
                //JSONObject tempJsonObj = new JSONObject(sb.toString());

                //Manipulating JSON DATA into something useful
                //JSONObject mainObj=new JSONObject(tempJsonOb;

                json_string = sb.toString().trim();

            } catch (MalformedURLException e) {
                //need to add exception handler

            } catch (IOException e) {

                //need to add exception handler

            }
            return json_string;
        }

        @Override
        protected void onPreExecute() {
        }

        //doInBackground return value goes here to onPostExecute
        @Override
        protected void onPostExecute(String json_string) {
            String message;

            //we try to convert string to jsonObject
            try {
                //listView = (ListView) findViewById(R.id.activity_add_medicine);

                jsonObject = new JSONObject(json_string);
                //from object we make json array druginfo because that is what it is called in php
                jsonArray = jsonObject.getJSONArray("message");


                //initiliaze count for while loop, strings for all data we will get from json
                //all data comes out as strings so for int values we need to cast them into int values later
                //hence drugtotal has int and string variables
                int count = 0;
                int drugtotal_int;

                //while count is less than length of jsonarray
                while (count < jsonArray.length()) {
                    //get the object put drugid into drugid ect..
                    JSONObject JO = jsonArray.getJSONObject(count);
                    message = JO.getString("message");
                    count++;
                }

            } catch (JSONException e) {
                //make excetion handler

            }
        }
    }
}