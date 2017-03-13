package ethanfortin_nicaragua.elbluffhospital.Inventory;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.ArrayAdapter_FetchAllDrugInfo;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.Class_FetchAllDrugInfo;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;

public class Inventory extends ListActivity {

    // Set layout, initialize layout object handles and listener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        getInventory();
    }

    private void getInventory() {

        class getInv extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Inventory.this,"Buscando...","Espera, por favor",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                parseInventory(s);
            }

            // In here, split between argChoice Value (1 or 2)
            protected String doInBackground(Void... params) {

                RequestHandler reqHan = new RequestHandler();
                HashMap<String, String> map = new HashMap<>();
                String s;

                s = reqHan.sendGetRequest(ConnVars.URL_FETCH_DRUGINFO_ALL);

                return s;
            }
        }

        getInv inv = new getInv();
        inv.execute();

    }

    // Parses JSON - isn't robust because listView isn't involved
    // Should only return one drug record, i.e. one name, id, and quantity
    private void parseInventory(String json) {

        Context context = this;
        ListView listView;
        ArrayList<Class_FetchAllDrugInfo> drugInfoData = new ArrayList();

        int totalCast, count = 0;
        String drugName, drugId, drugTotal;

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray resArr = jsonObject.getJSONArray(ConnVars.TAG_DRUGINFO);

            while (count < resArr.length()) {
                JSONObject resObj = resArr.getJSONObject(count);
                drugName = resObj.getString(ConnVars.TAG_DRUGINFO_NAME);
                drugId = resObj.getString(ConnVars.TAG_DRUGINFO_ID);
                drugTotal = resObj.getString(ConnVars.TAG_DRUGINFO_QUANT);

                try {
                    totalCast = Integer.parseInt(drugTotal);
                    drugInfoData.add(new Class_FetchAllDrugInfo(drugId, drugName, totalCast));
                } catch (NumberFormatException e) {
                    System.out.println("Number format exception occurred...");
                }

                count++;
            }

        } catch (JSONException j) {
            System.out.println("JSON Exception occurred...");
        }

        ArrayAdapter<Class_FetchAllDrugInfo> adapter = new ArrayAdapter_FetchAllDrugInfo(context, drugInfoData);
        listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(adapter);
    }
}