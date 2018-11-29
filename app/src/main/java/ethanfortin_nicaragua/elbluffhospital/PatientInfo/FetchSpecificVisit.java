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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
//import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.PatientinfoAdapter;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.VisitFields;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;

public class FetchSpecificVisit extends AppCompatActivity {
    //Create global variables for list view and ArrayList<DruginfoFields>
    ListView listView;
    String sID;
    String sVisitDate;
    int invpid;
    TextView patid, visitdate, doctor, height, weight, allergies, illness, meds;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_specific_visit);

        patid= (TextView) findViewById(R.id.visit_patid);
        visitdate =(TextView)findViewById(R.id.visit_date);
        doctor =(TextView)findViewById(R.id.visit_doc);
        height= (TextView) findViewById(R.id.visit_height);
        weight= (TextView)findViewById(R.id.visit_weight);
        allergies = (TextView)findViewById(R.id.visit_allergies);
        illness = (TextView)findViewById(R.id.visit_illness);
        meds= (TextView) findViewById(R.id.visit_meds) ;

        Intent intent = getIntent();
        sID = intent.getStringExtra("patid");
        sVisitDate = intent.getStringExtra("visitdate");
        Log.d("patid", sID);
        Log.d("visitdate", sVisitDate);
        System.out.println("here is patId: " +sID);
        System.out.println("here is visitdate: " +sVisitDate);

        getPatientInfo();
    }


    private void getPatientInfo() {

        class getVisitInfo extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(FetchSpecificVisit.this, "Buscando...", "Espera, por favor", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                parseVisitInfo(s);
            }

            //In here, split between argChoid Value (1 or 2)
            protected String doInBackground(Void... params) {

                RequestHandler reqHan = new RequestHandler();
                HashMap<String, String> map = new HashMap<>();
                String s;

                map.put("patid", sID);
                map.put("visitdate", sVisitDate);
                s = reqHan.sendGetRequestParam(ConnVars.URL_FETCH_PAT_VISIT, map);

                return s;
            }
        }

        getVisitInfo info = new getVisitInfo();
        info.execute();
    }

    private void parseVisitInfo(String json) {

        Context context = this;
        ListView listView;
        ArrayList<VisitFields> patientInfoData = new ArrayList();

        // int count = 0;
        String patId, visitDate, Doctor, Height, Weight, Allergies, Illness, Meds;

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray resArr = jsonObject.getJSONArray(ConnVars.TAG_VISITHISTORY);
            System.out.println("json="+json.toString());
            System.out.println("jsonObj="+jsonObject.toString());

            //while (count < resArr.length()) {
            JSONObject resObj = resArr.getJSONObject(0);
            patId = resObj.getString(ConnVars.TAG_VISITHISTORY_PATID);
            visitDate = resObj.getString(ConnVars.TAG_VISITHISTORY_VISITDATE);
            Doctor = resObj.getString(ConnVars.TAG_VISITHISTORY_DOCTOR);
            Height=resObj.getString(ConnVars.TAG_VISITHISTORY_HEIGHT);
            Weight = resObj.getString(ConnVars.TAG_VISITHISTORY_WEIGHT);
            Allergies = resObj.getString(ConnVars.TAG_VISITHISTORY_ALLERGIES);
            Illness= resObj.getString(ConnVars.TAG_VISITHISTORY_ILLNESS);
            Meds = resObj.getString(ConnVars.TAG_VISITHISTORY_MEDS);


            patid.setText(patId);
            visitdate.setText(visitDate);
            doctor.setText("Doctor: "+Doctor);
            height.setText("Height: "+Height);
            weight.setText("Weight: "+Weight);
            allergies.setText("Allergies: "+Allergies);
            illness.setText("Illness: "+Illness);
            meds.setText("Meds: "+Meds);


        } catch (JSONException j) {
            System.out.println("JSON Exception occured...");
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(FetchSpecificVisit.this, FetchVisits.class);
        Bundle b = new Bundle();
        b.putString("patid", sID); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
        finish();
    }
}
