package ethanfortin_nicaragua.elbluffhospital;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
//import static ethanfortin_nicaragua.elbluffhospital.R.id.drugtotal;
//import static ethanfortin_nicaragua.elbluffhospital.R.id.listView_fetch_druginfo_all;

/**
 * Created by Wildcat on 11/29/2016.
 */


public class fetch_druginfo_all extends AsyncTask<Void, Class_FetchAllDrugInfo, String>{

   // public interface_DruginfoAll delegate=null;


    Context context;
    AlertDialog alertDialog;
    String json_string;
    JSONObject jsonObject;
    private Inventory activity;
    JSONArray jsonArray;
    LayoutInflater inflater;
    ArrayList<Class_FetchAllDrugInfo> druginfo_data= new ArrayList();

    public fetch_druginfo_all(Context ctx){
        context = ctx;

    }

    @Override
    protected String doInBackground(Void ...parms) {
        String link = "http://192.168.0.100/android_connect/fetch_druginfo_all.php"; //153.104
       String user_name="Ethan";
        String password="GoCatz";
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
            OutputStream outputStream=httpURLConnection.getOutputStream();
            //BufferedWriter lets you write to the PHP clasuse
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            //This is how you send JSON values along with the request
            String post_data= URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                    +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");

            //bufferedWriter writes data
            bufferedWriter.write(post_data);
            //Deletes data, closes everything
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

           StringBuilder sb=new StringBuilder();
           String result="";
           //Input from PHP
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
           //Writting data from JSON in to result(string) and sb(String Builder)
            String line="";
            while((line=bufferedReader.readLine())!=null){
                result+=line;
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

            return sb.toString().trim();
        } catch (MalformedURLException e) {
            alertDialog= new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Fails");
            alertDialog.setMessage("Could not connect ya dingus, bad url ");
            alertDialog.show();


             return fail;
        } catch (IOException e) {

            alertDialog= new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Fails");
            alertDialog.setMessage("Could not connect ya dingus bad IOE");
            alertDialog.show();
            return fail;
        } //catch (JSONException e) {
           //e.printStackTrace();
           //return fail;
       //}

    }
    @Override
    protected void onPreExecute() {
       /* alertDialog= new AlertDialog.Builder(context).create();
        alertDialog.setTitle("LoginStatus");
        alertDialog.setMessage("WE got to hereeeee");
        alertDialog.show();*/
    }

    @Override
    protected void onPostExecute(String result){
        //inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

       /* alertDialog= new AlertDialog.Builder(context).create();
        alertDialog.setTitle("LoginStatus");
        alertDialog.setMessage("WE got to hereeeee");
        alertDialog.show();*/

        json_string=result;
       /* alertDialog= new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Is it alive ");
        alertDialog.setMessage(result);
        alertDialog.show();*/

        try{
            //listView = (ListView) findViewById(R.id.activity_add_medicine);

            jsonObject = new JSONObject(json_string);
            jsonArray= jsonObject.getJSONArray("druginfo");
            int count=0;

            //druginfo_data= new Class_FetchAllDrugInfo[jsonArray.length()];
            String drugid, drugname, drugtotal;
            int drugtotal_int;

            while(count<jsonArray.length()){
                JSONObject JO= jsonArray.getJSONObject(count);
                drugid = JO.getString("drugid");
                drugname = JO.getString("drugname");
                drugtotal = JO.getString("drugtotal");
                alertDialog= new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Did it work"+count);
                alertDialog.setMessage("DrugInfo: "+drugid + "DrugName: "+drugname + " DrugCount: "+ drugtotal);
                alertDialog.show();


                try{
                     drugtotal_int=Integer.parseInt(drugtotal);

                     druginfo_data.add(new Class_FetchAllDrugInfo(drugid,drugname,drugtotal_int));


                     Class_FetchAllDrugInfo single_drugdata=druginfo_data.get(count);
                     String temp_drugid= single_drugdata.getDrugID();
                    String temp_drugname= single_drugdata.getDrugName();
                    int temp_drugcount= single_drugdata.getDrugTotal();


                    //druginfo_data[count]=new Class_FetchAllDrugInfo(drugid,drugname,drugtotal_int);
                }
                catch(NumberFormatException nfe){
                    //make exception handlers?
                }




                count++;
            }

        }
        catch (JSONException e){
            alertDialog= new AlertDialog.Builder(context).create();
            alertDialog.setTitle("IT's time to be sad");
            alertDialog.setMessage("YOU'VE LET ME DOWN");
            alertDialog.show();

        }

        activity.setArrayList(druginfo_data);

        alertDialog= new AlertDialog.Builder(context).create();
        alertDialog.setTitle("We finished");
        alertDialog.setMessage("Don't LET ME DOWN");
        alertDialog.show();
    }




    /*@Override
    protected void onProgressUpdate(Void...values){
        alertDialog= new AlertDialog.Builder(context).create();
        alertDialog.setTitle("How WE doing");
        alertDialog.setMessage("Not great");
         alertDialog.show();
    }*/
}
