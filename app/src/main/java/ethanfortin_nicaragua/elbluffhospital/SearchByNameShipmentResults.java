package ethanfortin_nicaragua.elbluffhospital;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchByNameShipmentResults extends AppCompatActivity {
    public String drugid, drugname, shipdate;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_name_shipment_results);

        Bundle bundle = getIntent().getExtras();
        drugname = bundle.getString("DRUG_NAME");
        drugid = bundle.getString("DRUG_ID");
        shipdate = bundle.getString("DATE");
        int tog = bundle.getInt("TOGGLE");

        fetch_shipments_rows fetch_shipments_rows = new fetch_shipments_rows(this);
        fetch_shipments_rows.execute();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SearchByNameShipmentResults Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public class fetch_shipments_rows extends AsyncTask<Void, Class_Fetch_shipment_rows, String> {

        Context context;
        AlertDialog alertDialog;
        String json_string;
        JSONObject jsonObject;
        JSONArray jsonArray;
        LayoutInflater inflater;
        ArrayList<Class_Fetch_shipment_rows> druginfo_data = new ArrayList();

        public fetch_shipments_rows(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(Void... parms) {
            String link = "http://192.168.0.101/android_connect/fetch_druginfo_all.php"; //192.168.0.100 is one that usually works
            String data;
            String fail = "fail";
            try {
                //Create and open the URL connection
                URL url = new URL(link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //
                //CHECK GET OR POST
                //      ]
                //      ]
                //      ]
                //      ]
                httpURLConnection.setRequestMethod("GET");
                //setDoOutput allows you to access PHP server?
                //setDoInput lets you to recieve data from PHP server?
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                //OutputStream Writer is how you write the data along with the connection request
                OutputStream outputStream = httpURLConnection.getOutputStream();
                //BufferedWriter lets you write to the PHP clasuse
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                //This is how you send JSON values along with the request
                String post_data = URLEncoder.encode("drugid", "UTF-8") + "=" + URLEncoder.encode(drugid, "UTF-8") + "&"
                        + URLEncoder.encode("drugname", "UTF-8") + "=" + URLEncoder.encode(drugname, "UTF-8") + "&"
                        + URLEncoder.encode("shipdate", "UTF-8") + "=" + URLEncoder.encode(shipdate, "UTF-8");

                //bufferedWriter writes data
                bufferedWriter.write(post_data);
                //Deletes data, closes everything
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //we will store result in string builder
                StringBuilder sb = new StringBuilder();
                String result = "";
                //Input from PHP
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                //Writting data from JSON in to result(string) and sb(String Builder)
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                    sb.append(line);
                }
                //Closing all things we opened
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                //Turning StringBuilder to JSONObject
                //JSONObject tempJsonObj = new JSONObject(sb.toString());

                //Manipulating JSON DATA into something useful
                //JSONObject mainObj=new JSONObject(tempJsonOb;

                json_string = sb.toString().trim();

            } catch (MalformedURLException e) {
                //need to add exception handler

            } catch (IOException e) {

                //need to add exception handler

            }
            return json_string;
        }

        @Override
        protected void onPreExecute() {
        }

        //doInBackground return value goes here to onPostExecute
        @Override
        protected void onPostExecute(String json_string) {
            //we try to convert string to jsonObject
            try {
                //listView = (ListView) findViewById(R.id.activity_add_medicine);

                jsonObject = new JSONObject(json_string);
                //from object we make json array druginfo because that is what it is called in php
                jsonArray = jsonObject.getJSONArray("shipment");


                //initiliaze count for while loop, strings for all data we will get from json
                //all data comes out as strings so for int values we need to cast them into int values later
                //hence drugtotal has int and string variables
                int count = 0;
                String temp_drugid, temp_drugname, temp_shipquant, temp_shipdate;
                int int_shipquant;
                //Still gotta figure out this date thing
                Date date_shipdate;

                //while count is less than length of jsonarray
                while (count < jsonArray.length()) {
                    //get the object put drugid into drugid ect..
                    JSONObject JO = jsonArray.getJSONObject(count);
                    drugid = JO.getString("drugid");
                    drugname = JO.getString("drugname");
                    temp_shipquant = JO.getString("shipquant");
                    temp_shipdate = JO.getString("shipdate");


                    //try to cast string into int
                    try {
                        int_shipquant = Integer.parseInt(temp_shipquant);
                        //add this data as Class_FetchAllDrugInfo to ArrayList
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date d = sdf.parse("2005-12-03");

                        druginfo_data.add(new Class_Fetch_shipment_rows(d, drugid, drugname, int_shipquant));
                    } catch (NumberFormatException nfe) {
                        //make exception handlers?
                    } catch (ParseException ex) {
                    }
                    //increment count
                    count++;
                }

            } catch (JSONException e) {
                //make excetion handler

            }


            //Initiliaze arrayadapter and provide it with context and arraylist
            ArrayAdapter<Class_Fetch_shipment_rows> adapter = new ArrayAdapter_Fetch_shipment_rows(context, druginfo_data);

            //setliest view to lsitview in the xml file
            ListView listView = (ListView) findViewById(android.R.id.list);
            //turn on list view
            listView.setAdapter(adapter);

        }
    }
}
