package ethanfortin_nicaragua.elbluffhospital.PatientInfo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.PatientinfoAdapter;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.PatientinfoFields;
import ethanfortin_nicaragua.elbluffhospital.R;

public class FetchPatientInfo extends AppCompatActivity {
    //Create global variables for list view and ArrayList<DruginfoFields>
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_patient_info);

        /*
        *
        * Following is only for demonstration, should be commented for PHP functionality
        *
        * */
        listView = (ListView) findViewById(android.R.id.list);
        ArrayList<PatientinfoFields> patientgeninfo_data = new ArrayList();

       /* patientgeninfo_data.add(new PatientinfoFields("Nombre", "Pablo Sanchez"));
        patientgeninfo_data.add(new PatientinfoFields("ID", "123456"));
        patientgeninfo_data.add(new PatientinfoFields("Direccion", "532 El Bluff"));
        patientgeninfo_data.add(new PatientinfoFields("Tele", "18972892200"));
        patientgeninfo_data.add(new PatientinfoFields("La Fecha de Nacimiento", "23-01-78"));
        patientgeninfo_data.add(new PatientinfoFields("Genero", "Hombre"));
        patientgeninfo_data.add(new PatientinfoFields("Casado", "Si"));
        patientgeninfo_data.add(new PatientinfoFields("Ninos", "Tres: Maria, Juan, Jose"));
        patientgeninfo_data.add(new PatientinfoFields("Altura", "86cm"));
        patientgeninfo_data.add(new PatientinfoFields("Pesadura", "65kg"));
        patientgeninfo_data.add(new PatientinfoFields("Alergias", "Penecilinia, Azufre"));
        patientgeninfo_data.add(new PatientinfoFields("Condiciones Medicos", "Alta presion sanguinea"));*/

        ArrayAdapter<PatientinfoFields> adapter = new PatientinfoAdapter(this, patientgeninfo_data);

        //set list view to listview in the xml file
        ListView listView=(ListView) findViewById(android.R.id.list);

        //turn on list view
        listView.setAdapter(adapter);





        /*The following lines should be uncommented for PHP/communication functionality.

        add_patientinfo_row add_patientinfo_row = new add_patientinfo_row(this);
        add_patientinfo_row.execute();

        */





    }

    public class add_patientinfo_row extends AsyncTask<Void, AddPatientInfoRow, String> {

        public String s_patname = getIntent().getStringExtra("patname");
        public String s_patid = getIntent().getStringExtra("patid");;
        public String s_address = getIntent().getStringExtra("address");;
        public String s_telephone = getIntent().getStringExtra("telephone");;
        public String s_dob = getIntent().getStringExtra("dob");;
        public String s_gender = getIntent().getStringExtra("gender");;
        public String s_marstat = getIntent().getStringExtra("marstat");;
        public String s_children = getIntent().getStringExtra("children");;
        public String s_height = getIntent().getStringExtra("height");;
        public String s_weight = getIntent().getStringExtra("weight");;
        public String s_allergies = getIntent().getStringExtra("allergies");;
        public String s_medcond = getIntent().getStringExtra("medcond");;

        int i_children = Integer.parseInt(s_children);
        int i_height = Integer.parseInt(s_height);
        int i_weight = Integer.parseInt(s_weight);

        Context context;
        String json_st;
        JSONObject json_ob;
        JSONArray json_ar;
        ArrayList<AddPatientInfoRow> patientinfo_data;

        public add_patientinfo_row(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                URL url = new URL("http://192.168.0.100/android_connect/add_patientinfo_row.php");
                DataOutputStream printout;
                DataInputStream input;

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);

                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);

                conn.setRequestProperty("Content-Type","application/json");
                conn.connect();

                json_ob = new JSONObject();
                json_ar = new JSONArray();

                json_ob.put("patid",s_patid);
                json_ob.put("patname",s_patname);
                json_ob.put("address",s_address);
                json_ob.put("telephone",s_telephone);
                json_ob.put("dob",s_dob);
                json_ob.put("gender",s_gender);
                json_ob.put("marstat",s_marstat);
                json_ob.put("children",i_children);
                json_ob.put("height",i_height);
                json_ob.put("weight",i_weight);
                json_ob.put("allergies",s_allergies);
                json_ob.put("medcond",s_medcond);

                printout = new DataOutputStream(conn.getOutputStream());
                printout.writeUTF(URLEncoder.encode(json_ob.toString(),"UTF-8"));
                printout.flush();
                printout.close();

                finish();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json_st;
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
        Intent go_back = new Intent(this, SearchAddPatients.class);
        startActivity(go_back);
    }


    public void EditPatient(View view){

        LayoutInflater inflater = LayoutInflater.from(FetchPatientInfo.this);
        View subView = inflater.inflate(R.layout.dialog_patient_gen_info, null);

        //Build dialog set it to subview
        AlertDialog.Builder builderSingle1 = new AlertDialog.Builder(this);
        builderSingle1.setView(subView);
        builderSingle1.setTitle("Editar Paciente");


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

        edit_name2.setText("Pablo Sanchez", TextView.BufferType.EDITABLE);
        edit_ID2.setText("123456", TextView.BufferType.EDITABLE);
        edit_adress2.setText("532 El Bluff", TextView.BufferType.EDITABLE);
        edit_telephone2.setText("18972892200", TextView.BufferType.EDITABLE);
        edit_gender2.setText("Hombre", TextView.BufferType.EDITABLE);
        edit_married2.setText("Si", TextView.BufferType.EDITABLE);
        edit_birthday2.setText("23-01-78", TextView.BufferType.EDITABLE);
        edit_children2.setText("Tres: Maria, Juan, Jose", TextView.BufferType.EDITABLE);
        edit_height2.setText("86cm", TextView.BufferType.EDITABLE);
        edit_weight2.setText("65kg", TextView.BufferType.EDITABLE);
        edit_allergies2.setText("Penecilinia, Azufre", TextView.BufferType.EDITABLE);
        edit_medicalConditions2.setText("Alta presion sanguinea", TextView.BufferType.EDITABLE);


        builderSingle1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle1.setPositiveButton(
                "Guardar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast toast = Toast.makeText(getApplicationContext(), "Cambios guardado.", Toast.LENGTH_LONG);
                        toast.show();

                        //Push to DB including ones not above but in Dialog
                        dialog.dismiss();

                    }
                });
        builderSingle1.show();
    }

}




