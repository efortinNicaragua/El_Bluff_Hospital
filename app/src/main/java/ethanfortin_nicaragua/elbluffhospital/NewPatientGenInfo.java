package ethanfortin_nicaragua.elbluffhospital;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

public class NewPatientGenInfo extends AppCompatActivity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient_gen_info);

        add_patientinfo_row add_patientinfo_row = new add_patientinfo_row(this);
        add_patientinfo_row.execute();

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
                    startActivity(new Intent(this, NewPatientGenInfo.class));
                    return true;
            case R.id.pat_history:
                    startActivity(new Intent(this, NewVisitHistory.class));
                    return true;
            case R.id.pat_prescription:
                    startActivity(new Intent(this, NewPatientPrescription.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        Intent go_back = new Intent(this, PatientInfo.class);
        startActivity(go_back);
    }
}


