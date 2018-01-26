package ethanfortin_nicaragua.elbluffhospital.Inventory;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.DruginfoAdapter;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.DruginfoFields;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;

public class Inventory extends AppCompatActivity {

    ArrayAdapter<DruginfoFields> adapter;
    ListView listView;
    ArrayList<DruginfoFields> drugInfoData = new ArrayList();
    EditText et_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        listView= (ListView) findViewById(R.id.inventory_list);
        et_search=(EditText) findViewById(R.id.filter_bar1);
        getInventory();
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.toString().equals("")){
                getInventory();
                }
                else{
                    //perform search
                    System.out.println("s="+s.toString());
                    searchItem(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void searchItem(String textToSearch){

        ArrayList<DruginfoFields> temp_drugInfoData = drugInfoData;
        ArrayList<DruginfoFields> temp= new ArrayList<>();
        int temp_size=drugInfoData.size();

       // System.out.println("Temp DName: "+temp_drugInfoData.get(0).drugname);
        //if(temp_drugInfoData.get(0).drugname.contains("a")){ System.out.println("Cotains a: "+temp_drugInfoData.get(0).drugname);}
        for (int i=0; i<temp_size;i++){
            //System.out.println("i= "+i);
            //System.out.println("Temp DName: "+temp_drugInfoData.get(i).getDrugname());
            if (!temp_drugInfoData.get(i).drugname.equalsIgnoreCase(textToSearch)) {
               temp.add(temp_drugInfoData.get(i));
                //System.out.println("Temp DName: "+temp_drugInfoData.get(i).getDrugname());
            }


        }
         drugInfoData.removeAll(temp);
        /*for (int i=0; i<drugInfoData.size();i++) {
            System.out.println("DrugName "+drugInfoData.get(i).drugname);
        }*/

         adapter.notifyDataSetChanged();
         listView.setAdapter(adapter);
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
                    drugInfoData.add(new DruginfoFields(drugId, drugName, totalCast));
                } catch (NumberFormatException e) {
                    System.out.println("Number format exception occurred...");
                }

                count++;
            }

        } catch (JSONException j) {
            System.out.println("JSON Exception occurred...");
        }

         adapter = new DruginfoAdapter(context, drugInfoData);
        listView.setAdapter(adapter);
    }
}