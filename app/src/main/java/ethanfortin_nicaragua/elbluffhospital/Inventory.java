package ethanfortin_nicaragua.elbluffhospital;

import android.app.ListActivity;
import android.content.Context;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Inventory extends ListActivity {

//Create global variables for list view and ArrayList<Class_FetchAllDrugInfo>
    ListView listView;
    ArrayList<Class_FetchAllDrugInfo> druginfo_data = new ArrayList();

//Standard onCreate method
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        /*
        *
        * Following is only for demonstration, should be commented for PHP functionality
        *
        * */
        /*
        listView = (ListView) findViewById(android.R.id.list);
        ArrayList<Class_FetchAllDrugInfo> druginfo_data = new ArrayList();

        druginfo_data.add(new Class_FetchAllDrugInfo("<Example Drug ID>", "<Example Name>", 9999));
        druginfo_data.add(new Class_FetchAllDrugInfo("113649", "Tylenol", 9999));
        druginfo_data.add(new Class_FetchAllDrugInfo("780283", "Advil", 9999));
        druginfo_data.add(new Class_FetchAllDrugInfo("098342", "Dayquil", 9999));
        druginfo_data.add(new Class_FetchAllDrugInfo("389913", "Nyquil", 9999));
        druginfo_data.add(new Class_FetchAllDrugInfo("919191", "Ibuprofin", 9999));
        druginfo_data.add(new Class_FetchAllDrugInfo("789789", "Vicodin", 9999));

        ArrayAdapter<Class_FetchAllDrugInfo> adapter = new ArrayAdapter_FetchAllDrugInfo(this, druginfo_data);
        */

        //set list view to listview in the xml file
        //ListView listView=(ListView) findViewById(android.R.id.list);
        //turn on list view
        //listView.setAdapter(adapter);



        /*
        *
        * Below should be uncommented for PHP functionality; above is for example only
        *
        * */
        fetch_druginfo_all fetch_druginfo_all = new fetch_druginfo_all(this);
        fetch_druginfo_all.execute();

    }

    public class fetch_druginfo_all extends AsyncTask<Void, Class_FetchAllDrugInfo, String> {

        Context context;
        String json_string;
        JSONObject jsonObject;
        JSONArray jsonArray;

        public fetch_druginfo_all(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(Void... parms) {
            String link = "http://192.168.0.101/android_connect/fetch_druginfo_all.php"; //192.168.0.100 is one that usually works

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

                //Input from PHP
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                //Writing data from JSON in to result(string) and sb(String Builder)
                String result = "";
                String line;
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
                System.out.println("MalformedURLException occurred while making connection ");
            } catch (IOException e) {
                System.out.println("IOException occurred while making connection ");
            }
            return json_string;
        }

        @Override
        protected void onPreExecute() {
            // Do nothing
        }

        @Override
        protected void onPostExecute(String json_string) {
        //we try to convert string to jsonObject
            try {

                // Make JSONObject and designate the array jsonArray to grab the array
                // that's title is "druginfo" from the received object
                jsonObject = new JSONObject(json_string);
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
                    JSONObject jo = jsonArray.getJSONObject(count);
                    drugid = jo.getString("drugid");
                    drugname = jo.getString("drugname");
                    drugtotal = jo.getString("drugtotal");

                    //try to cast string into int
                    try {
                        drugtotal_int = Integer.parseInt(drugtotal);
                    //add this data as Class_FetchAllDrugInfo to ArrayList
                        druginfo_data.add(new Class_FetchAllDrugInfo(drugid, drugname, drugtotal_int));

                    } catch (NumberFormatException nfe) {
                        System.out.println("Number Format Exception occurred...");
                    }
                    //increment count
                    count++;
                }

            } catch (JSONException e) {
                System.out.println("JSON Exception occurred...");
            }


            //Initiliaze arrayadapter and provide it with context and arraylist
            ArrayAdapter<Class_FetchAllDrugInfo> adapter = new ArrayAdapter_FetchAllDrugInfo(context, druginfo_data);

            //setliest view to lsitview in the xml file
            listView = (ListView) findViewById(android.R.id.list);
            //turn on list view
            listView.setAdapter(adapter);

        }

    }
}

