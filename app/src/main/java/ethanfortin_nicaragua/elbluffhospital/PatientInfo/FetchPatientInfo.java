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
import ethanfortin_nicaragua.elbluffhospital.DataClasses.PatientinfoFields;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;

public class FetchPatientInfo extends AppCompatActivity {
    //Create global variables for list view and ArrayList<DruginfoFields>
    ListView listView;
    String sID;
    int invpid;
    TextView patid, birthday, patname, genero, address, telephone, civil_status;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_patient_info);

        patid= (TextView) findViewById(R.id.geninfo_patid);
        birthday=(TextView)findViewById(R.id.geninfo_birthday);
        genero=(TextView)findViewById(R.id.geninfo_gender);
        patname= (TextView) findViewById(R.id.geninfo_name);
        address= (TextView)findViewById(R.id.geninfo_address);
        telephone = (TextView)findViewById(R.id.geninfo_telephone);
        civil_status = (TextView)findViewById(R.id.geninfo_civil_status);
       // peso= (TextView) findViewById(R.id.geninfo_lastweight) ;
        //talla=(TextView) findViewById(R.id.geninfo_lastheight);
       // pa=(TextView) findViewById(R.id.geninfo_lastbp);
        //t=(TextView) findViewById(R.id.geninfo_lasttemp);
//        fc=(TextView) findViewById(R.id.geninfo_lasthb);

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

      // int count = 0;
        String patName, patId, Birthday, Address, Telephone, Gender, Civil_status;

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray resArr = jsonObject.getJSONArray(ConnVars.TAG_PATIENTINFO);
            System.out.println("json="+json.toString());
            System.out.println("jsonObj="+jsonObject.toString());

            //while (count < resArr.length()) {
                JSONObject resObj = resArr.getJSONObject(0);
                patName = resObj.getString(ConnVars.TAG_PATIENTINFO_NAME);
                patId = resObj.getString(ConnVars.TAG_PATIENTINFO_ID);
                Address = resObj.getString(ConnVars.TAG_PATIENTINFO_ADDRESS);
                Gender=resObj.getString(ConnVars.TAG_PATIENTINFO_GENDER);
                Telephone = resObj.getString(ConnVars.TAG_PATIENTINFO_TELEPHONE);
                Civil_status = resObj.getString(ConnVars.TAG_PATIENTINFO_CIVILSTATUS);
                Birthday= resObj.getString(ConnVars.TAG_PATIENTINFO_BIRTHDAY);


                patid.setText(patId);
                patname.setText(patName);
                //birthday.setText(birthday);
                address.setText("Direccion: "+Address);
                telephone.setText("Tele: "+Telephone);
                civil_status.setText("Estado Civil: "+Civil_status);
                genero.setText("Genero: "+Gender);
                birthday.setText("Fecha de Nacciamento: "+Birthday);

                //count++;

            //}
 /*           //Get the weight
            json=(json.substring(json.lastIndexOf("peso")-23));
            System.out.println("Myjson="+json.toString());
            jsonObject = new JSONObject(json);
            System.out.println("jsonObj="+jsonObject.toString());
            JSONArray vitalinfoArr=jsonObject.getJSONArray(ConnVars.TAG_VISITHISTORY_PESO);
            //System.out.println(vitalinfoArr.toString());
            JSONObject vitalinfoObj=vitalinfoArr.getJSONObject(0);
            //System.out.println(vitalinfoObj.toString());


            String Peso=vitalinfoObj.getString(ConnVars.TAG_VISITHISTORY_PESO);
            System.out.println("Peso= "+Peso);
            //txt_drugname.setText(drugName);
            peso.setText("Peso: "+Peso+"kg");


            //get Height
            json=(json.substring(json.lastIndexOf("talla")-24));
            System.out.println("json="+json.toString());
            jsonObject = new JSONObject(json);
            System.out.println("jsonObj="+jsonObject.toString());
            vitalinfoArr=jsonObject.getJSONArray(ConnVars.TAG_VISITHISTORY_TALLA);
            //System.out.println(vitalinfoArr.toString());
             vitalinfoObj=vitalinfoArr.getJSONObject(0);
            //System.out.println(vitalinfoObj.toString());


            String Talla=vitalinfoObj.getString(ConnVars.TAG_VISITHISTORY_TALLA);
            System.out.println("Talla= "+Talla);
            //txt_drugname.setText(drugName);
            talla.setText("Talla: "+Talla+"cm");

            //Get Blood Preassure
            json=(json.substring(json.lastIndexOf("pa")-21));
            System.out.println("json="+json.toString());
            jsonObject = new JSONObject(json);
            System.out.println("jsonObj="+jsonObject.toString());
            vitalinfoArr=jsonObject.getJSONArray(ConnVars.TAG_VISITHISTORY_PA);
            //System.out.println(vitalinfoArr.toString());
            vitalinfoObj=vitalinfoArr.getJSONObject(0);
            //System.out.println(vitalinfoObj.toString());


            String Pa=vitalinfoObj.getString(ConnVars.TAG_VISITHISTORY_PA);
            System.out.println("Pa= "+Pa);
            //txt_drugname.setText(drugName);
            pa.setText("Pa: "+Pa+"mmHg");

            //ArrayAdapter<PatientinfoFields> adapter = new PatGenInfoAdapter(context, patientInfoData);
            //listView = (ListView) findViewById(R.id.list2);
            //listView.setAdapter(adapter);

            //Get temperature
            json=(json.substring(json.lastIndexOf("t")-20));
            System.out.println("json="+json.toString());
            jsonObject = new JSONObject(json);
            System.out.println("jsonObj="+jsonObject.toString());
            vitalinfoArr=jsonObject.getJSONArray(ConnVars.TAG_VISITHISTORY_T);
            //System.out.println(vitalinfoArr.toString());
            vitalinfoObj=vitalinfoArr.getJSONObject(0);
            //System.out.println(vitalinfoObj.toString());


            String T=vitalinfoObj.getString(ConnVars.TAG_VISITHISTORY_T);
            System.out.println("T= "+T);
            //txt_drugname.setText(drugName);
            t.setText("T: "+T+"c");

            //Get heartbeat / heart rate
            json=(json.substring(json.lastIndexOf("fc")-21));
            System.out.println("json="+json.toString());
            jsonObject = new JSONObject(json);
            System.out.println("jsonObj="+jsonObject.toString());
            vitalinfoArr=jsonObject.getJSONArray(ConnVars.TAG_VISITHISTORY_FC);
            //System.out.println(vitalinfoArr.toString());
            vitalinfoObj=vitalinfoArr.getJSONObject(0);
            //System.out.println(vitalinfoObj.toString());


            String Fc=vitalinfoObj.getString(ConnVars.TAG_VISITHISTORY_FC);
            System.out.println("FC= "+Fc);
            //txt_drugname.setText(drugName);
            fc.setText("fc: "+Fc+"minutos");
*/
        } catch (JSONException j) {
            System.out.println("JSON Exception occured...");
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
                Intent newintent = new Intent(getBaseContext(), FetchPatientInfo.class);
                newintent.putExtra("patid", sID);
                newintent.putExtra("invpid", invpid);
                startActivity(newintent);
                //startActivity(new Intent(this, FetchPatientInfo.class));
                return true;
            case R.id.pat_history:



                Intent newintent1 = new Intent(getBaseContext(), FetchVisits.class);
                Bundle b = new Bundle();
                b.putString("patid", sID);
                newintent1.putExtras(b);
                startActivity(newintent1);
                finish();
                System.out.println("The pat ID is: " + sID);
                /*
                newintent1.putExtra("patid", sID);
                newintent1.putExtra("invpid", invpid);
                System.out.println("The pat ID is: " + sID);
                startActivity(newintent1);
                //startActivity(new Intent(this, FetchVisits.class));

                        //System.out.println( temp_drugInfoData.get(position));
                        Intent intent = new Intent(SearchAddPatients.this, FetchPatientInfo.class);
                        Bundle b = new Bundle();
                        b.putString("patid", temp_patInfoData.get(position).patid); //Your id
                        intent.putExtras(b); //Put your id to your next Intent
                        startActivity(intent);
                        finish();
           */
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
