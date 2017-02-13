package ethanfortin_nicaragua.elbluffhospital;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FetchSpecificDrug extends AppCompatActivity implements View.OnClickListener {

    // Declare global layout variables

    // results
    private TextView textViewDrugName;
    private TextView textViewDrugId;
    private TextView textViewDrugTotal;

    // search parameters
    private EditText editTextDrugName;
    private EditText editTextDrugId;

    // button listener
    private Button buttonSearch;


    // Set layout, initialize layout object handles and listener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_specific_drug);

        buttonSearch = (Button) findViewById(R.id.bFind);

        editTextDrugName = (EditText) findViewById(R.id.etDrugName);
        editTextDrugId = (EditText) findViewById(R.id.etDrugID);

        textViewDrugName = (TextView) findViewById(R.id.resDrug);
        textViewDrugTotal = (TextView) findViewById(R.id.resQuant);
        textViewDrugId = (TextView) findViewById(R.id.resID);

        buttonSearch.setOnClickListener(this);
    }

    // argChoice = 1 --> search by name
    // argChoice = 2 --> search by ID
    private void drugFetch(final String argVal, final int argChoice) {

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

                switch (argChoice) {

                    case 1:
                        // search by
                        map.put("drugname", argVal);
                        s = reqHan.sendGetRequestParam(ConnVars.URL_FETCH_SPECIFIC_DRUG, map);
                        break;

                    case 2:
                        // search by id
                        map.put("drugid", argVal);
                        s = reqHan.sendGetRequestParam(ConnVars.URL_FETCH_SPECIFIC_DRUG, map);
                        break;

                    default:
                        System.out.println("Default -- neither name/ID worked: argChoice == " + argChoice);
                        s = "badMethod";
                        break;
                }

                return s;
            }
        }

        fetchSpecificDrug specDrug = new fetchSpecificDrug();
        specDrug.execute();
    }

    // Parses JSON - isn't robust because listView isn't involved
    // Should only return one drug record, i.e. one name, id, and quantity
    private void drugShow(String json) {

        try {
            System.out.println("Mark 1");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray resArr = jsonObject.getJSONArray(ConnVars.TAG_DRUGINFO);
            JSONObject resObj = resArr.getJSONObject(0);

            String drugName = resObj.getString(ConnVars.TAG_DRUGINFO_NAME);
            String drugId = resObj.getString(ConnVars.TAG_DRUGINFO_ID);
            String drugTotal = resObj.getString(ConnVars.TAG_DRUGINFO_QUANT);

            textViewDrugName.setText(drugName);
            textViewDrugId.setText(drugId);
            textViewDrugTotal.setText(drugTotal);

        } catch (JSONException j) {
            System.out.println("JSON Exception occurred...");
        }
    }

    @Override
    public void onClick(View v) {

        // Get
        String sName = editTextDrugName.getText().toString();
        String sId = editTextDrugId.getText().toString();

        // If neither field is populated, give toast
        if (TextUtils.equals("", sName)) {
            if (TextUtils.equals("", sId)) {
                int duration = Toast.LENGTH_LONG;
                Context context = getApplicationContext();
                String text1 = "Da el nombre o el ID de la medicina, por favor";
                Toast toast1 = Toast.makeText(context, text1, duration);
                toast1.show();
            }
            else {
                // If name is blank but ID is populated, search by ID
                System.out.println("Searching by ID");
                drugFetch(sId, 2);
            }
        }
        else {
            if (TextUtils.equals("", sId)) {
                // If name is populated and ID is blank, search by name
                System.out.println("Searching by name");
                drugFetch(sName, 1);
            }
            else {
                // If both fields are populated, toast to only fill one.
                int duration = Toast.LENGTH_LONG;
                Context context = getApplicationContext();
                String text1 = "el nombre o el ID, no los dos, por favor";
                Toast toast1 = Toast.makeText(context, text1, duration);
                toast1.show();
            }
        }
    }
}
