package ethanfortin_nicaragua.elbluffhospital.Inventory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.ShipmentAdapter;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.ShipmentFields;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;

public class FetchShipments extends AppCompatActivity {

            DatePicker datePicker;
            ListView listView;
            TextView noHayResultados;
    ArrayAdapter<ShipmentFields> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_shipments);

        listView = (ListView) findViewById(R.id.shipment_list);
        datePicker = (DatePicker) findViewById(R.id.shipment_dp);
        noHayResultados=(TextView)findViewById(R.id.text_noresults);
        getShipments();

        datePicker.getCalendarView().setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
              System.out.println("Date Changed");
              getShipments();
            }
        });
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

                //String str2 = getIntent().getStringExtra("drugname");
               //System.out.println("Name: " + str2);
                int year = datePicker.getYear();
                int month = datePicker.getMonth()+1;
                int day = datePicker.getDayOfMonth();
                String s_day;
                String s_month;

                if (day<=9){
                    s_day= "0"+day;
                }
                else{ s_day= day+"";}
                if (month<=9){
                    s_month="0"+month;
                }
                else{s_month=month+"";}

                String str3 = year+"-"+s_month+"-"+s_day;
                System.out.println("Date: " + str3);

                //if(str3.length()>3){map.put("shipdate", str3);}
                //map.put("drugname", str2);
                map.put("shipdate", str3);

                s = reqHan.sendGetRequestParam(ConnVars.URL_FETCH_SHIPMENT, map);
                return s;
            }
        }

        fetchShipments fS = new fetchShipments();
        fS.execute();
    }

    private void parseShipments(String json) {
        System.out.println(json);

        Context context = this;
        final ArrayList<ShipmentFields> shipmentData = new ArrayList();

        int totalCast, count = 0;
        String drugId, drugName, shipQuant, shipDate, expDate;

        try {
            JSONObject jsonObject = new JSONObject(json);

            // Handle case when nothing is returned
            int success = jsonObject.getInt(ConnVars.TAG_SUCCESS);
            if (success == 0) {
                shipmentData.clear();
                adapter = new ShipmentAdapter(context, shipmentData);
                listView.setAdapter(adapter);
                noHayResultados.setVisibility(View.VISIBLE);

            } else {
                noHayResultados.setVisibility(View.GONE);
                JSONArray resArr = jsonObject.getJSONArray(ConnVars.TAG_SHIPMENT);

                while (count < resArr.length()) {
                    JSONObject resObj = resArr.getJSONObject(count);
                    drugName = resObj.getString(ConnVars.TAG_SHIPMENT_DRUGNAME);
                    drugId = resObj.getString(ConnVars.TAG_SHIPMENT_DRUGID);
                    shipQuant = resObj.getString(ConnVars.TAG_SHIPMENT_SHIPQUANT);
                    shipDate = resObj.getString(ConnVars.TAG_SHIPMENT_SHIPDATE);
                    expDate= resObj.getString(ConnVars.TAG_SHIPMENT_EXPDATE);


                    try {
                        totalCast = Integer.parseInt(shipQuant);
                        shipmentData.add(new ShipmentFields(shipDate, drugId, drugName, totalCast, expDate));
                        Log.d("Ethan shipmentfields", shipmentData.get(count).drugid.toString());
                    } catch (NumberFormatException e) {
                        System.out.println("Number format exception occurred...");
                    }

                    count++;
                }

                adapter = new ShipmentAdapter(context, shipmentData);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                        //System.out.println( temp_drugInfoData.get(position));
                        Intent intent = new Intent(FetchShipments.this, FetchSpecificDrug.class);
                        Bundle b = new Bundle();
                        b.putString("drugid", shipmentData.get(position).drugid); //Your id
                        intent.putExtras(b); //Put your id to your next Intent
                        startActivity(intent);
                        finish();
                    }
                });


            }
        }

         catch (JSONException j) {
            System.out.println("JSON Exception occurred...");
        }


    }

}