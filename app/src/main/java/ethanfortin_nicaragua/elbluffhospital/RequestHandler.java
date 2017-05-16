package ethanfortin_nicaragua.elbluffhospital;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by wildcat on 2/6/2017.
 * Based on tutorial written by Belel Khan - SimplifiedCoding.net
 */

public class RequestHandler {
    //a

    /*
    * Used to post data to MySQL
    * */
    // First argument is the URL of the script to which we will send the request
    // Other is a HashMap with name value pairs containing the data to be send with the request
    public String sendPostRequest(String requestURL, HashMap<String, String> postDataParams) {

        URL url;
        Log.d("Ethan","we sendPostRequest");
        // String builder stores response from mysql
        StringBuilder sb = new StringBuilder();

        try {
            // Initialize URL
            //url=new URL(requestURL);
            url = new URL(requestURL + "?" + getPostDataString(postDataParams));
            Log.d("Test1","Post Request");
            Log.d("Test1","URL initiated: " + url);

            // Create connection
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // Config connection properties
            con.setReadTimeout(3000);
            con.setConnectTimeout(3000);
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);

            /*OutputStream os = con.getOutputStream();

            // Here is where parameters are defined to the request
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            System.out.println("Here is the Writter " + getPostDataString(postDataParams) );
            writer.flush();
            writer.close();
            os.close();*/

            con.connect();

            int responseCode = con.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                sb = new StringBuilder();
                String response;

                while((response = br.readLine()) != null) sb.append(response);

            } else {
                System.out.println("HTTP Response was not OK:");
                System.out.println(responseCode);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /*
    * Used when fetching from MySQL without params (i.e. FetchDrugInfoAll)
    * */
    public String sendGetRequest(String requestURL) {

        URL url;

        // Stringbuilder stores response from MySQL
        StringBuilder sb = new StringBuilder();

        try {
            url = new URL(requestURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(3000);
            con.setConnectTimeout(3000);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String s;
            while((s = bufferedReader.readLine()) != null) {
                sb.append(s + "\n");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /*
    * Used when fetching from MySQL with parameter (i.e. any time specification is needed by user)
    * Only difference between this and sendGetRequest is the id is included in URL
    * */
    public String sendGetRequestParam(String requestURL, HashMap<String, String> params) {

        URL url;

        // Stringbuilder stores response from MySQL
        StringBuilder sb = new StringBuilder();

        try {

            url = new URL(requestURL + "?" + getPostDataString(params));
            System.out.println("URL initiated: " + url);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(3000);
            con.setConnectTimeout(3000);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String s;
            while((s = bufferedReader.readLine()) != null) {
                sb.append(s + "\n");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /*
    * Used to write parameters to a request in acceptable format
    * */
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {

            // Append "&" before every parameter except first one
            if (first) first = false;
            else result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
