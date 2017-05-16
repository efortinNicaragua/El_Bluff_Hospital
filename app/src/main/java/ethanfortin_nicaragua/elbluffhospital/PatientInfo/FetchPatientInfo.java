package ethanfortin_nicaragua.elbluffhospital.PatientInfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;

import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.PatGenInfoAdapter;
import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.PatientinfoAdapter;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.PatientinfoFields;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;

public class FetchPatientInfo extends AppCompatActivity {
    //Create global variables for list view and ArrayList<DruginfoFields>
    ListView listView;
    String sID;
    int invpid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_patient_info);

        Intent intent = getIntent();
        sID = intent.getStringExtra("patid");
        Log.d("patid", sID);
        getPatientInfo();
    }


    private void getPatientInfo() {

        class getPatInfo extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(FetchPatientInfo.this, "Buscando...", "Espera, por favor", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                parsePatientInfo(s);
            }

            //In here, split between argChoid Value (1 or 2)
            protected String doInBackground(Void... params) {

                RequestHandler reqHan = new RequestHandler();
                HashMap<String, String> map = new HashMap<>();
                String s;

                map.put("patid", sID);
                s = reqHan.sendGetRequestParam(ConnVars.URL_FETCH_PATIENTINFO_ROW, map);

                return s;
            }
        }

        getPatInfo info = new getPatInfo();
        info.execute();
    }

    private void parsePatientInfo(String json) {

        Context context = this;
        ListView listView;
        ArrayList<PatientinfoFields> patientInfoData = new ArrayList();

       int count = 0;
        String patName, patId, address, telephone, dob, gender, marStat, allergies, medCond, children, height, weight;
        int childrenCast, heightCast, weightCast;

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray resArr = jsonObject.getJSONArray(ConnVars.TAG_PATIENTINFO);

            while (count < resArr.length()) {
                JSONObject resObj = resArr.getJSONObject(count);
                patName = resObj.getString(ConnVars.TAG_PATIENTINFO_NAME);
                patId = resObj.getString(ConnVars.TAG_PATIENTINFO_ID);
                address = resObj.getString(ConnVars.TAG_PATIENTINFO_ADDRESS);
                telephone = resObj.getString(ConnVars.TAG_PATIENTINFO_TELEPHONE);
                dob = resObj.getString(ConnVars.TAG_PATIENTINFO_DOB);
                gender = resObj.getString(ConnVars.TAG_PATIENTINFO_GENDER);
                marStat = resObj.getString(ConnVars.TAG_PATIENTINFO_MARSTAT);
                allergies = resObj.getString(ConnVars.TAG_PATIENTINFO_ALLERGIES);
                medCond = resObj.getString(ConnVars.TAG_PATIENTINFO_MEDCOND);
                children = resObj.getString(ConnVars.TAG_PATIENTINFO_CHILDREN);
                height = resObj.getString(ConnVars.TAG_PATIENTINFO_HEIGHT);
                weight = resObj.getString(ConnVars.TAG_PATIENTINFO_WEIGHT);
                invpid = resObj.getInt(ConnVars.TAG_PATIENTINFO_INVPID);

                try {
                    childrenCast = Integer.parseInt(children);
                    heightCast = Integer.parseInt(height);
                    weightCast = Integer.parseInt(weight);
                    patientInfoData.add(new PatientinfoFields(patId, patName, address, telephone, gender, marStat, heightCast, weightCast, childrenCast, allergies, medCond, dob));
                } catch (NumberFormatException nfe) {
                    System.out.println("Number format exception occured...");
                }
                count++;

            }
        } catch (JSONException j) {
            System.out.println("JSON Exception occured...");
        }

        ArrayAdapter<PatientinfoFields> adapter = new PatGenInfoAdapter(context, patientInfoData);
        listView = (ListView) findViewById(R.id.list2);
        listView.setAdapter(adapter);
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
                newintent.putExtra("invpid", invpid);
                startActivity(newintent);
                //startActivity(new Intent(this, FetchPatientInfo.class));
                return true;
            case R.id.pat_history:
                Intent newintent1 = new Intent(getBaseContext(), FetchVisits.class);
                newintent1.putExtra("patid", sID);
                newintent1.putExtra("invpid", invpid);
                startActivity(newintent1);
                //startActivity(new Intent(this, FetchVisits.class));
                return true;
            case R.id.pat_prescription:
                Intent newintent2 = new Intent(getBaseContext(), FetchPrescriptions.class);
                newintent2.putExtra("patid", sID);
                newintent2.putExtra("invpid", invpid);
                startActivity(newintent2);
                //startActivity(new Intent(this, FetchPrescriptions.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        Intent go_back = new Intent(this, SearchAddPatients.class);
        startActivity(go_back);
    }
}
