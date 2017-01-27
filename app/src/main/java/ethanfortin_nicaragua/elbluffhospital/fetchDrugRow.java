package ethanfortin_nicaragua.elbluffhospital;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class fetchDrugRow extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_shipments);



        //FetchDruginfoRow fetchDruginfoRow = new FetchDruginfoRow(this);
        //fetchDruginfoRow.execute();
    }

    private String getGetDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public class FetchDruginfoRow extends AsyncTask<Void, Class_FetchDruginfoRow, String> {

        Context context;
        String json_string;
        JSONObject jsonObject;
        JSONArray jsonArray;
        ArrayList<Class_FetchDruginfoRow> drugInfoRow_data = new ArrayList();

        public FetchDruginfoRow(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(Void... parms) {
            String link = "http://192.168.0.100/android_connect/fetch_druginfo_row.php";
            String username = "Chris";
            String password = "Dixon";

            Intent last = getIntent();
            String $drugid = last.getStringExtra("drugid");
            String $drugname = last.getStringExtra("drugname");
            String $shipdate = last.getStringExtra("shipdate");

            try {
                //Create and open the URL connection
                URL url = new URL(link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");

                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                //OutputStream Writer is how you write the data along with the connection request
                OutputStream outputStream = httpURLConnection.getOutputStream();

                //BufferedWriter lets you write to the PHP clasuse
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                //This is how you send JSON values along with the request
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                //bufferedWriter writes data
                bufferedWriter.write(post_data);

                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //we will store result in string builder
                StringBuilder sb = new StringBuilder();
                String result = "";

                //Input from PHP
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                //Writing data from JSON in to result(string) and sb(String Builder)
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                    sb.append(line);
                }

                //Closing all things we opened
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                json_string = sb.toString().trim();

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return json_string;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String json_string) {

            try{
                jsonObject = new JSONObject((json_string));
                jsonArray = jsonObject.getJSONArray("druginfo");

                int count = 0;
                String drugid, drugname, drugtotal;
                int drugtotal_int;

                while(count < jsonArray.length()) {

                    //get the object put drugid into drugid ect..
                    JSONObject JO = jsonArray.getJSONObject(count);
                    drugid = JO.getString("drugid");
                    drugname = JO.getString("drugname");
                    drugtotal = JO.getString("drugtotal");

                    //try to cast string into int
                    try {
                        drugtotal_int = Integer.parseInt(drugtotal);
                        //add this data as Class_FetchAllDrugInfo to ArrayList
                        drugInfoRow_data.add(new Class_FetchDruginfoRow(drugid, drugname, drugtotal_int));

                    } catch (NumberFormatException nfe) {
                        //make exception handlers?
                    }
                    //increment count
                    count++;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Initiliaze arrayadapter and provide it with context and arraylist
            ArrayAdapter<Class_FetchDruginfoRow> adapter = new ArrayAdapter_FetchDruginfoRow(context, drugInfoRow_data);

            //setliest view to lsitview in the xml file
            ListView listView=(ListView) findViewById(android.R.id.list);
            //turn on list view
            listView.setAdapter(adapter);
        }

    }
}
