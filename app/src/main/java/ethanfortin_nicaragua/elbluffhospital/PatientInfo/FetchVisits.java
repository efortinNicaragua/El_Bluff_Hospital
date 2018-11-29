package ethanfortin_nicaragua.elbluffhospital.PatientInfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.FetchVisits_ExpListAdapter;
import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.VisitAdapter;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.VisitFields;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;
import ethanfortin_nicaragua.elbluffhospital.UploadFile;

public class FetchVisits extends AppCompatActivity {
    private Button addDocument;
    String sID;
    int invpid;

    final Context context = this;
    ArrayList<VisitFields> patVisitdata = new ArrayList();
    //int count = 0;
    //int hash_count = 0;
    Spinner year;
    //String dob_day_temp1, dob_month_temp1;
    // int dob_day_temp, dob_month_temp, dob_year_temp;
    //AlertDialog db_message;

    ArrayAdapter<VisitFields> adapter;
    ListView listView;
    ArrayList<VisitFields> temp_patVisitdata = new ArrayList();
    EditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_visits);
        year = (Spinner) findViewById(R.id.filter_year);

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= 2000; i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);

        Spinner spinYear = (Spinner) findViewById(R.id.filter_year);
        spinYear.setAdapter(adapter);


        // I AM COMMENTING THIS OUT ~ BRIANNA
//       Intent intent = getIntent();
//        sID = intent.getStringExtra("patid");

        listView= (ListView) findViewById(R.id.visit_list);

        Intent intent = getIntent();
        sID = intent.getStringExtra("patid");
        Log.d("patid", sID);
        System.out.println("patid is: " + sID);

        visitFetch();
    }


    private void visitFetch() {
        class fetch_visitinfo extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                System.out.println("got here");

                super.onPreExecute();
                loading = ProgressDialog.show(FetchVisits.this, "Buscando...", "Espera, por favor", false, false);
            }

            // Once JSON received correctly, parse and display it
            @Override
            protected void onPostExecute(String s) {
                System.out.println("here is s: "+s);
                super.onPostExecute(s);
                loading.dismiss();
                jsonParse(s);
            }

            // In here, split between argChoice Value (1 or 2)
            protected String doInBackground(Void... params) {

                RequestHandler reqHan = new RequestHandler();
                HashMap<String, String> map = new HashMap<>();
                String s;

                map.put("patid", sID);
                s = reqHan.sendGetRequestParam(ConnVars.URL_FETCH_PAT_VISIT, map);

                return s;
            }
        }
        fetch_visitinfo vinfo = new fetch_visitinfo();
        vinfo.execute();
    }

    private void jsonParse(String json_string) {

        Context context=this;

        int totalCast, count=0;
        String patid, visitdate, doctor, height, weight, allergies, illness, meds;
        int visitid;

        JSONObject jsonObject;
        JSONArray jsonArray;
        try {

            // Make JSONObject and designate the array jsonArray to grab the array
            // that's title is "druginfo" from the received object
            jsonObject = new JSONObject(json_string);
            System.out.println("made json object: "+json_string);
            jsonArray = jsonObject.getJSONArray(ConnVars.TAG_VISITHISTORY);
            System.out.println("put stuff into jsonobject");

            //initiliaze count for while loop, strings for all data we will get from json
            //all data comes out as strings so for int values we need to cast them into int values later
            //while count is less than length of jsonarray
            while (count < jsonArray.length()) {
                //get the object put drugid into drugid ect..
                JSONObject jo = jsonArray.getJSONObject(count);
                visitid= jo.getInt("visitid");
                patid= jo.getString("patid");
                visitdate=jo.getString("visitdate");
                doctor= jo.getString("doctor");
                height = jo.getString("height");
                weight= jo.getString("weight");
                allergies=jo.getString("allergies");
                illness = jo.getString("illness");
                meds = jo.getString("meds");

                //try to cast string into int


                //add this data as VisitFields to ArrayList
                patVisitdata.add(new VisitFields(visitid, patid, visitdate, doctor, height, weight, allergies, illness, meds));
                            // took  weight, allergies, illness, meds out of this

                //increment count
                count++;
            }

        } catch (JSONException e) {

        }
        //System.out.println(patVisitdata.size()+" that was size of pat info data");
        for(int i=0;i<patVisitdata.size();i++){
            temp_patVisitdata.add(patVisitdata.get(i));
            // System.out.println("name="+temp_patVisitdata.get(i).patname.toString());
        }

        adapter = new VisitAdapter(context, temp_patVisitdata);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //System.out.println( temp_drugInfoData.get(position));
                Intent intent = new Intent(FetchVisits.this, FetchSpecificVisit.class);
                Bundle b = new Bundle();
                b.putString("patid", temp_patVisitdata.get(position).patid); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(FetchVisits.this, FetchPatientInfo.class);
        Bundle b = new Bundle();
        b.putString("patid", sID); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
        finish();
    }

    public void AddNewVisit(View V) {
        Intent intent = new Intent(FetchVisits.this, NewFetchVisit.class);
        Bundle b = new Bundle();
        b.putString("patid", sID); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}