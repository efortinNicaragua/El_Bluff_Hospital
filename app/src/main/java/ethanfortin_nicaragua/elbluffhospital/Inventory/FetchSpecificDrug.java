package ethanfortin_nicaragua.elbluffhospital.Inventory;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.ArrayAdapter_FetchAllDrugInfo;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.Class_FetchAllDrugInfo;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.Class_FetchDruginfoRow;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;

public class FetchSpecificDrug extends AppCompatActivity implements View.OnClickListener {

    // Declare global layout variables

    // search parameters
    private EditText editTextDrugName;
    private EditText editTextDrugId;

    // button listener
    private Button buttonSearch;

    // druginfo class array adapter
    ArrayList<Class_FetchAllDrugInfo> druginfo = new ArrayList();


    // Set layout, initialize layout object handles and listener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_specific_drug);

        buttonSearch = (Button) findViewById(R.id.bFind);

        editTextDrugName = (EditText) findViewById(R.id.etDrugName);
        editTextDrugId = (EditText) findViewById(R.id.etDrugID);

        buttonSearch.setOnClickListener(this);
    }

    // argChoice = 1 --> search by name
    // argChoice = 2 --> search by ID
    private void drugFetch(final String name, final String id) {

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

            // In here, split between argChoice Value (1 or 2)
            protected String doInBackground(Void... params) {

                RequestHandler reqHan = new RequestHandler();
                HashMap<String, String> map = new HashMap<>();
                String s;

                if (!TextUtils.equals("", name)) map.put("drugname", name);
                if (!TextUtils.equals("", id)) map.put("drugid", id);

                s = reqHan.sendGetRequestParam(ConnVars.URL_FETCH_SPECIFIC_DRUG, map);

                return s;

            }
        }

        fetchSpecificDrug specDrug = new fetchSpecificDrug();
        specDrug.execute();
    }

    // Parses JSON - listview involved
    private void drugShow(String json) {

        try {
            System.out.println("Mark 1");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray resArr = jsonObject.getJSONArray(ConnVars.TAG_DRUGINFO);
            JSONObject resObj = resArr.getJSONObject(0);

            String drugName = resObj.getString(ConnVars.TAG_DRUGINFO_NAME);
            String drugId = resObj.getString(ConnVars.TAG_DRUGINFO_ID);
            String drugTotal = resObj.getString(ConnVars.TAG_DRUGINFO_QUANT);

            druginfo.add(new Class_FetchAllDrugInfo(
                    drugName,
                    drugId,
                    Integer.parseInt(drugTotal)
            ));

        } catch (JSONException j) {
            System.out.println("JSON Exception occurred...");
        }

        Context context = this;
        ListView listView;
        ArrayAdapter<Class_FetchAllDrugInfo> adapter = new ArrayAdapter_FetchAllDrugInfo(context, druginfo);

        // Sync ListView object with widget in layout file
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        // Get
        String sName = editTextDrugName.getText().toString();
        String sId = editTextDrugId.getText().toString();

        // If neither field is populated, give toast
        if (TextUtils.equals("", sName) && TextUtils.equals("", sId)) {

            int duration = Toast.LENGTH_LONG;
            Context context = getApplicationContext();
            String text1 = "Da el nombre o el ID de la medicina, por favor";
            Toast toast1 = Toast.makeText(context, text1, duration);
            toast1.show();
        }
        // Else, one or both fields are populated
        else drugFetch(sName, sId);
    }
}
