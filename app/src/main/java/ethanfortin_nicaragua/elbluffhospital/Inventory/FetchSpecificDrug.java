package ethanfortin_nicaragua.elbluffhospital.Inventory;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.DrugNameAdapter;
import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.DruginfoAdapter;
import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.PatientinfoAdapter;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.DruginfoFields;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;
import ethanfortin_nicaragua.elbluffhospital.SpinnerWidget.SearchableSpinner;

public class FetchSpecificDrug extends AppCompatActivity implements SearchableSpinner.OnSelectionChangeListener {

    // Declare global layout variables
    ArrayAdapter<String> adapter;

    // druginfo class array adapter
    ArrayList<DruginfoFields> druginfo = new ArrayList();

    ListView lv;
    String selectedListItem;

    // Set layout, initialize layout object handles and listener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_specific_drug);

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

                String drugName;
                lv = (ListView)findViewById(R.id.drug_list);
                ArrayList<String> list = new ArrayList<>();
                int count = 0;

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray resArr = jsonObject.getJSONArray(ConnVars.TAG_DRUGINFO);

                    while (count < resArr.length()) {
                        System.out.println("Array: " + list);
                        JSONObject resObj = resArr.getJSONObject(count);
                        drugName = resObj.getString(ConnVars.TAG_DRUGINFO_NAME);
                        list.add(drugName);
                        count++;
                    }

                } catch (JSONException j) {
                    System.out.println("JSON Exception occurred...");
                }

                adapter = new DrugNameAdapter(FetchSpecificDrug.this, list);

                final EditText filter = (EditText)findViewById(R.id.filter_bar);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        selectedListItem = (String) lv.getItemAtPosition(position);
                        System.out.println("ITEM = " + selectedListItem);
                        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        mgr.hideSoftInputFromWindow(filter.getWindowToken(), 0);
                        drugFetch(selectedListItem);
                    }
                });

                lv.setAdapter(adapter);


                filter.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        FetchSpecificDrug.this.adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }

            // Pass user input params to fetch method, even if they are blank
            protected String doInBackground(Void... params) {

                String s;
                RequestHandler reqHan = new RequestHandler();
                s = reqHan.sendGetRequest(ConnVars.URL_FETCH_DRUGINFO_ALL);

                return s;
            }
        }

        fetchNameList nameList = new fetchNameList();
        nameList.execute();
    }

    // Takes entered name and Id as args,
    private void drugFetch(final String name) {

        class fetchSpecificDrug extends AsyncTask<Void,Void,String> {

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
                System.out.println("Executing Drugshow");
                drugShow(s);
            }

            // Pass user input params to fetch method, even if they are blank
            protected String doInBackground(Void... params) {

                RequestHandler reqHan = new RequestHandler();
                HashMap<String, String> map = new HashMap<>();
                String s;

                // Will only pass key-value if EditText was populated
                if (!TextUtils.equals("", name)) map.put("drugname", name);

                s = reqHan.sendGetRequestParam(ConnVars.URL_FETCH_SPECIFIC_DRUG, map);

                return s;

            }
        }

        fetchSpecificDrug specDrug = new fetchSpecificDrug();
        specDrug.execute();
    }

    // Parses JSON - listview involved
    private void drugShow(String json) {

        TextView txtName = (TextView)findViewById(R.id.name_res);
        TextView txtId = (TextView)findViewById(R.id.id_res);
        TextView txtQuant = (TextView)findViewById(R.id.quant_res);
        System.out.println(json);

        try {
            System.out.println("Mark 1");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray resArr = jsonObject.getJSONArray(ConnVars.TAG_DRUGINFO);
            JSONObject resObj = resArr.getJSONObject(0);

            String drugName = resObj.getString(ConnVars.TAG_DRUGINFO_NAME);
            String drugId = resObj.getString(ConnVars.TAG_DRUGINFO_ID);
            String drugTotal = resObj.getString(ConnVars.TAG_DRUGINFO_QUANT);

            txtName.setText(drugName);
            txtId.setText(drugId);
            txtQuant.setText(drugTotal);

        } catch (JSONException j) {
            System.out.println("JSON Exception occurred...");
        }
    }

    @Override
    public void onSelectionChanged(String selection) {
        Toast.makeText(this, selection + " selected", Toast.LENGTH_SHORT).show();
    }
}
