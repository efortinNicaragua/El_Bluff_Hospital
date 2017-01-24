package ethanfortin_nicaragua.elbluffhospital;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import static android.R.id.list;

public class Inventory extends ListActivity {

//Create global variables for list view and ArrayList<Class_FetchAllDrugInfo>
    ListView listView;
    ArrayList<Class_FetchAllDrugInfo> druginfo_data = new ArrayList();

//Standard onCreate method
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        fetch_druginfo_all fetch_druginfo_all = new fetch_druginfo_all(this);
        fetch_druginfo_all.execute();

    }


    public class fetch_druginfo_all extends AsyncTask<Void, Class_FetchAllDrugInfo, String> {

        Context context;
        AlertDialog alertDialog;
        String json_string;
        JSONObject jsonObject;
        JSONArray jsonArray;
        LayoutInflater inflater;
        ArrayList<Class_FetchAllDrugInfo> druginfo_data = new ArrayList();

        public fetch_druginfo_all(Context ctx) {
            context=ctx;

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
                httpURLConnection.setRequestMethod("POST");
                //setDoOutput allows you to access PHP server?
                //setDoInput lets you to recieve data from PHP server?
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                //OutputStream Writer is how you write the data along with the connection request
                OutputStream outputStream = httpURLConnection.getOutputStream();
                //BufferedWriter lets you write to the PHP clasuse
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                //This is how you send JSON values along with the request
                String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode("example", "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode("ejemplo", "UTF-8");

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
                jsonArray = jsonObject.getJSONArray("druginfo");


                //initiliaze count for while loop, strings for all data we will get from json
                //all data comes out as strings so for int values we need to cast them into int values later
                //hence drugtotal has int and string variables
                int count = 0;
                String drugid, drugname, drugtotal;
                int drugtotal_int;

                //while count is less than length of jsonarray
                while (count < jsonArray.length()) {
                    //get the object put drugid into drugid ect..
                    JSONObject JO = jsonArray.getJSONObject(count);
                    drugid = JO.getString("drugid");
                    drugname = JO.getString("drugname");
                    drugtotal = JO.getString("drugtotal");

                    //try to cast string into int
                    try {
                        drugtotal_int = Integer.parseInt(drugtotal);
                    //add this data as Class_FetchAllDrugInfo to ArrayList
                        druginfo_data.add(new Class_FetchAllDrugInfo(drugid, drugname, drugtotal_int));

                    } catch (NumberFormatException nfe) {
                        //make exception handlers?
                    }
                    //increment count
                    count++;
                }

            } catch (JSONException e) {
                //make excetion handler

            }



            //Initiliaze arrayadapter and provide it with context and arraylist
            ArrayAdapter<Class_FetchAllDrugInfo> adapter = new ArrayAdapter_FetchAllDrugInfo(context, druginfo_data);

            //setliest view to lsitview in the xml file
            ListView listView=(ListView) findViewById(android.R.id.list);
            //turn on list view
            listView.setAdapter(adapter);

        }

    }
}

