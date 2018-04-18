package ethanfortin_nicaragua.elbluffhospital.Inventory;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.ShipmentAdapter1;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.DruginfoFields;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.ShipmentFields;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;

public class FetchSpecificDrug extends AppCompatActivity {

    // Declare global layout variables
    ArrayAdapter<ShipmentFields> adapter;

    // druginfo class array adapter
    ArrayList<DruginfoFields> druginfo = new ArrayList();

    ListView listView;
    TextView txt_drugid;
    TextView txt_drugname;
    TextView txt_drugtotal;
    String drug_id;
    // Set layout, initialize layout object handles and listener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_specific_drug);

        listView=(ListView) findViewById(R.id.list_specific_drug);
        txt_drugid=(TextView) findViewById(R.id.text_drugid);
        txt_drugname=(TextView) findViewById(R.id.text_drugname);
        txt_drugtotal=(TextView) findViewById(R.id.text_drugtotal);

        Bundle b = getIntent().getExtras();
        if(b != null)
            drug_id= b.getString("drugid");

        nameListFetch();
    }

    // Fetches JSON String of all drugs in inventory
    private void nameListFetch() {

        class fetchNameList extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(FetchSpecificDrug.this,"Buscando...","Espera, por favor",false,false);
            }

            // Once JSON received correctly, parse and display it
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                parseShipments(s);
            }

            // Pass user input params to fetch method, even if they are blank
            protected String doInBackground(Void... params) {

                HashMap<String, String> map = new HashMap<>();
                String s;
                //map.put("drugid",id);
                map.put("drugid",drug_id);
                RequestHandler reqHan = new RequestHandler();
                s = reqHan.sendGetRequestParam(ConnVars.URL_FETCH_SPECIFIC_DRUG, map);

                return s;
            }
        }

        fetchNameList nameList = new fetchNameList();
        nameList.execute();
    }

    private void parseShipments(String json) {
        System.out.println(json);

        Context context = this;
        ArrayList<ShipmentFields> shipmentData = new ArrayList();

        int totalCast, count = 0;
        String drugId, drugName, drugQuant, shipQuant, shipDate, expDate;

        try {
            JSONObject jsonObject = new JSONObject(json);
            System.out.println("json="+json.toString());
            System.out.println("jsonObj="+jsonObject.toString());


                JSONArray resArr = jsonObject.getJSONArray(ConnVars.TAG_SHIPMENT);
                //System.out.println("resArr="+resArr.toString());
                while (count < resArr.length()) {
                    JSONObject resObj = resArr.getJSONObject(count);
                    //System.out.println("resobj="+resObj.toString());
                    drugName = resObj.getString(ConnVars.TAG_SHIPMENT_DRUGNAME);
                    drugId = resObj.getString(ConnVars.TAG_SHIPMENT_DRUGID);
                    shipQuant = resObj.getString(ConnVars.TAG_SHIPMENT_SHIPQUANT);
                    shipDate = resObj.getString(ConnVars.TAG_SHIPMENT_SHIPDATE);
                    expDate= resObj.getString(ConnVars.TAG_SHIPMENT_EXPDATE);


                    try {
                        totalCast = Integer.parseInt(shipQuant);
                        shipmentData.add(new ShipmentFields(shipDate, drugId, drugName, totalCast, expDate));
                        //Log.d("Ethan shipmentfields", shipmentData.get(count).drugid.toString());
                    } catch (NumberFormatException e) {
                        System.out.println("Number format exception occurred...");
                    }

                    count++;
                }

                adapter = new ShipmentAdapter1(context, shipmentData);
                listView.setAdapter(adapter);

                json=(json.substring(json.lastIndexOf("druginfo")-14));
                //System.out.println("json="+json.toString());
                jsonObject = new JSONObject(json);
                //System.out.println("jsonObj="+jsonObject.toString());
                JSONArray druginfoArr=jsonObject.getJSONArray(ConnVars.TAG_DRUGINFO);
                //System.out.println(druginfoArr.toString());
                JSONObject druginfoObj=druginfoArr.getJSONObject(0);
                //System.out.println(druginfoObj.toString());
                drugName=druginfoObj.getString(ConnVars.TAG_DRUGINFO_NAME);
                drugId=druginfoObj.getString(ConnVars.TAG_DRUGINFO_ID);
                drugQuant=druginfoObj.getString(ConnVars.TAG_DRUGINFO_QUANT);

                txt_drugname.setText(drugName);
                txt_drugid.setText("ID:"+drugId);
                txt_drugtotal.setText("Total:"+drugQuant);



        }

        catch (JSONException j) {
            System.out.println("JSON Exception occurred...");
        }


    }
    @Override
    public void onBackPressed() {
        finish();
    }

}

