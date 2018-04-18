package ethanfortin_nicaragua.elbluffhospital.Inventory;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.DruginfoAdapter;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.DruginfoFields;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;

public class Inventory extends AppCompatActivity {

    ArrayAdapter<DruginfoFields> adapter;
    ListView listView;
    ArrayList<DruginfoFields> drugInfoData = new ArrayList();
    ArrayList<DruginfoFields> temp_drugInfoData = new ArrayList();
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

        for (int i=0; i<drugInfoData.size();i++){
            System.out.println("drugname="+drugInfoData.get(i).drugname.toString());
        }


        ArrayList<DruginfoFields> temp= new ArrayList<>();
        int temp_size=drugInfoData.size();
        temp_drugInfoData.clear();

        for (int i=0; i<temp_size;i++){
            //System.out.println("i= "+i);
            //System.out.println("Temp DName: "+temp_drugInfoData.get(i).getDrugname());

            //set add druginfodata to temp_druginfo array
            temp_drugInfoData.add(drugInfoData.get(i));

            //add drugname to temp list if it does not match searched for character
            if (!temp_drugInfoData.get(i).drugname.toLowerCase().contains(textToSearch.toLowerCase())) {
               temp.add(temp_drugInfoData.get(i));
            }
        }

        System.out.println("pause");
        for (int i=0; i<drugInfoData.size();i++){
            System.out.println("drugname="+drugInfoData.get(i).drugname.toString());
        }

        for (int j = temp.size()-1; j >= 0; j--) {
            temp_drugInfoData.remove(temp.get(j));
        }
        //temp_drugInfoData.removeAll(temp);

        //System.out.println("temp size after removal="+temp_drugInfoData.size());
        /*for (int i=0; i<drugInfoData.size();i++) {
            System.out.println("DrugName "+drugInfoData.get(i).drugname);
        }*/
        System.out.println("pause1");
        for (int i=0; i<drugInfoData.size();i++){
            System.out.println("drugname="+drugInfoData.get(i).drugname.toString());
        }
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

        final Context context = this;

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

        for(int i=0;i<drugInfoData.size();i++){
            temp_drugInfoData.add(drugInfoData.get(i));
        }

        adapter = new DruginfoAdapter(context, temp_drugInfoData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //System.out.println( temp_drugInfoData.get(position));
                Intent intent = new Intent(Inventory.this, FetchSpecificDrug.class);
                Bundle b = new Bundle();
                b.putString("drugid", temp_drugInfoData.get(position).drugid); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
                finish();
            }
        });
    }
}