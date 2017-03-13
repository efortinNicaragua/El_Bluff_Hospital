package ethanfortin_nicaragua.elbluffhospital.Inventory;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.ArrayAdapter_Fetch_shipment_rows;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.Class_Fetch_shipment_rows;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;

public class FetchShipments extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_shipments);

        getShipments();
    }

    private void getShipments() {

        class fetchShipments extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(FetchShipments.this,"Buscando...","Espera, por favor",false,false);
            }

            // Once JSON received correctly, parse and display it
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                parseShipments(s);
            }

            protected String doInBackground(Void... params) {

                RequestHandler reqHan = new RequestHandler();
                HashMap<String, String> map = new HashMap<>();
                String s;

                String str1 = getIntent().getStringExtra("drugid");
                System.out.println("ID: " + str1);
                String str2 = getIntent().getStringExtra("drugname");
                System.out.println("Name: " + str2);
                String str3 = getIntent().getStringExtra("shipdate");
                System.out.println("Date: " + str3);

                map.put("drugid", str1);
                map.put("drugname", str2);
                map.put("shipdate", str3);

                s = reqHan.sendGetRequestParam(ConnVars.URL_FETCH_SHIPMENTS, map);
                return s;
            }
        }

        fetchShipments fS = new fetchShipments();
        fS.execute();
    }

    private void parseShipments(String json) {
        System.out.println(json);

        Context context = this;
        ListView listView;
        ArrayList<Class_Fetch_shipment_rows> shipmentData = new ArrayList();

        int totalCast, count = 0;
        String drugId, drugName, shipQuant, shipDate;

        try {
            JSONObject jsonObject = new JSONObject(json);

            // Handle case when nothing is returned
            int success = jsonObject.getInt(ConnVars.TAG_SUCCESS);
            if (success == 0) {
                onBackPressed();
                Toast toast = Toast.makeText(getApplicationContext(),
                        "No hay resultados", Toast.LENGTH_LONG);
                ViewGroup vg = (ViewGroup) toast.getView();
                TextView toastTV = (TextView) vg.getChildAt(0);
                toastTV.setTextSize(25);
                toast.show();
            } else {

                JSONArray resArr = jsonObject.getJSONArray(ConnVars.TAG_SHIPMENTS);

                while (count < resArr.length()) {
                    JSONObject resObj = resArr.getJSONObject(count);
                    drugName = resObj.getString(ConnVars.TAG_SHIPMENTS_DRUGNAME);
                    drugId = resObj.getString(ConnVars.TAG_SHIPMENTS_DRUGID);
                    shipQuant = resObj.getString(ConnVars.TAG_SHIPMENTS_SHIPQUANT);
                    shipDate = resObj.getString(ConnVars.TAG_SHIPMENTS_SHIPDATE);

                    try {
                        totalCast = Integer.parseInt(shipQuant);
                        shipmentData.add(new Class_Fetch_shipment_rows(shipDate, drugId, drugName, totalCast));
                    } catch (NumberFormatException e) {
                        System.out.println("Number format exception occurred...");
                    }

                    count++;
                }

                ArrayAdapter<Class_Fetch_shipment_rows> adapter = new ArrayAdapter_Fetch_shipment_rows(context, shipmentData);
                listView = (ListView) findViewById(android.R.id.list);
                listView.setAdapter(adapter);

            }

        } catch (JSONException j) {
            System.out.println("JSON Exception occurred...");
        }


    }

}