package ethanfortin_nicaragua.elbluffhospital.Inventory;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

//import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.PatientinfoAdapter;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.PatientinfoFields;
import ethanfortin_nicaragua.elbluffhospital.PatientInfo.SearchAddPatients;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;

public class AddShipment extends AppCompatActivity { //Activity{


    private EditText et_drugName;
    private EditText et_drugId;
    private EditText et_drugQuant;
    private EditText et_drugUnit;
    private DatePicker et_shipdate;
    private DatePicker et_expdate;

    int error;
    AlertDialog db_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shipment);


    }

    public void createShipment(View v) {

        et_drugName = (EditText)findViewById(R.id.addName);
        et_drugId = (EditText)findViewById(R.id.addId);
        et_drugQuant = (EditText)findViewById(R.id.addQuantity);
        et_drugUnit=(EditText)findViewById(R.id.addUnits);
        et_shipdate=(DatePicker)findViewById(R.id.datePicker3);
        et_expdate=(DatePicker)findViewById(R.id.datePicker4);

        boolean cancel = false;
        View focusView = null;

        // Verify drug name
        String s_drugName = et_drugName.getText().toString();
        if(TextUtils.isEmpty(s_drugName)) {
            et_drugName.setError("Not Valid");
            focusView = et_drugName;
            cancel = true;
        }
        else{
            et_drugName.setError(null,null);
        }
        // Verify drug id
        String s_drugId = et_drugId.getText().toString();
        if(TextUtils.isEmpty(s_drugId)) {
            et_drugId.setError("Not Valid");
            focusView = et_drugId;
            cancel = true;
        }
        else{et_drugId.setError(null,null);}

        // Verify drug quantity
        String s_drugQuant = et_drugQuant.getText().toString();
        if(TextUtils.isEmpty(s_drugQuant)) {
            et_drugQuant.setError("Not Valid");
            focusView = et_drugQuant;
            cancel = true;
        }
        else{et_drugQuant.setError(null,null);}

        // Verify drug units
        String s_drugUnit = et_drugUnit.getText().toString();
        if(TextUtils.isEmpty(s_drugUnit)) {
            et_drugUnit.setError("Not Valid");
            focusView = et_drugUnit;
            cancel = true;
        }
        else{et_drugUnit.setError(null,null);}

        //Change format of date picker!
        //Get int of day, month, year

        int day_shipDate = et_shipdate.getDayOfMonth();
        int month_shipDate=et_shipdate.getMonth()+1;
        int year_shipDate =et_shipdate.getYear();

        int day_expDate=et_expdate.getDayOfMonth();
        int month_expDate=et_expdate.getMonth()+1;
        int year_expDate=et_expdate.getYear();

        //Turn day moth year into individual strings and concatonate into what suits you.
        String s_day_shipDate, s_month_shipDate, s_year_shipDate, s_shipDate;
        String s_day_expDate, s_month_expDate, s_year_expDate, s_expDate;

        //if value of day or month
        if(day_shipDate<10){
            s_day_shipDate="0"+String.valueOf(day_shipDate);
        }
        else{s_day_shipDate=String.valueOf(day_shipDate);}

        if(month_shipDate<10){
            s_month_shipDate="0"+String.valueOf(month_shipDate);
        }
        else{s_month_shipDate=String.valueOf(month_shipDate);}

        s_year_shipDate=String.valueOf(year_shipDate);


        if(day_shipDate<10) {
           s_day_expDate="0"+String.valueOf(day_expDate);
        }
        else{s_day_expDate=String.valueOf(day_expDate);}

        if(month_shipDate<10){
            s_month_expDate="0"+String.valueOf(month_expDate);
        }
        else{s_month_expDate=String.valueOf(month_expDate);}

        s_year_expDate=String.valueOf(year_expDate);

        //put these all into string called shipdate with format that PHP prefers;

        s_shipDate=s_year_shipDate+"-"+s_month_shipDate+"-"+s_day_shipDate;
        s_expDate=s_year_expDate+"-"+s_month_expDate+"-"+s_day_expDate;

        System.out.println("Here is the format and value of individual date values " + s_day_shipDate + " " + s_month_shipDate + " "+ s_year_shipDate);
        System.out.println("Here is the format and value of the concat date " + s_shipDate);
        System.out.println("EXP Here is the format and value of individual date values " + s_day_expDate + " " + s_month_expDate + " "+ s_year_expDate);
        System.out.println("EXP Here is the format and value of the concat date " + s_expDate);


        if(cancel) {
            focusView.requestFocus();
        }
        else {

            addShipment(s_drugId,s_drugName,s_drugQuant,s_drugUnit,s_shipDate,s_expDate);

            /*int duration = Toast.LENGTH_LONG;
            Context context = getApplicationContext();
            String text1 = "Medicina nueva archivada.";
            Toast toast1 = Toast.makeText(context, text1, duration);
            toast1.show();*/

            // finish();
        }
    }

    private void addShipment(final String drugid, final String drugname, final String drugincr, final String drugunit, final String shipdate, final String expdate) {
        class get_addShipment extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddShipment.this, "Buscando...", "Espera, por favor", false, false);
            }

            // Once JSON received correctly, parse and display it
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                System.out.println("Here is s "+s);
                int errortemp=jsonParse(s);
                System.out.println("HEre is errortemp "+errortemp);
                Log.d("Test1", "Error temp "+ errortemp);
                if(errortemp==0) {
                    db_message = new AlertDialog.Builder(AddShipment.this).create();
                    db_message.setTitle("Accion Termino");
                    db_message.setMessage("Shipment Creado!\n Totales Cambiados!");
                    db_message.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    db_message.show();
                }
                else if (errortemp==1){
                    db_message = new AlertDialog.Builder(AddShipment.this).create();
                    db_message.setTitle("Termino Sin Errores");
                    db_message.setMessage("Nueva Droga Creado!\n Shipment Creado\n Totales Cambiado");
                    db_message.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    db_message.show();
                }
                else{
                    db_message = new AlertDialog.Builder(AddShipment.this).create();
                    db_message.setTitle("ERRORES!");
                    db_message.setMessage("Habia errores creando el nuevo shipment.\n Aseguranse que no hay un shipment de esa droga en esa dia\n Solo puedes tener un shipment por droga por dia.");
                    db_message.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    db_message.show();
                }
            }

            // In here, split between argChoice Value (1 or 2)
            protected String doInBackground(Void... params) {

                RequestHandler reqHan = new RequestHandler();
                HashMap<String, String> map = new HashMap<>();
                map.put("drugid",drugid);
                map.put("drugname",drugname);
                map.put("drugincr", drugincr);
                map.put("drugunit",drugunit);
                map.put("shipdate",shipdate);
                map.put("expdate",expdate);
                String s;

                s = reqHan.sendPostRequest(ConnVars.URL_ADD_PRESCRIPTION, map);

                return s;
            }
        }
        get_addShipment shipment = new get_addShipment();
        shipment.execute();
    }
    private int jsonParse(String json_string) {

        Context context = this;

        int count = 0;
        String new_drug="1", ship_success="1", info_success="1";
        int int_new_drug = 0; int int_ship_success=0; int int_info_success=0;

        JSONObject jsonObject;
        JSONArray jsonArray;
        try {

            // Make JSONObject and designate the array jsonArray to grab the array
            // that's title is "druginfo" from the received object
            jsonObject = new JSONObject(json_string);
            System.out.println("made json object: " + json_string);
            jsonArray = jsonObject.getJSONArray(ConnVars.TAG_SHIP_ERRORMESSAGES);
            System.out.println("jsonArray: "+ jsonArray);

            //initiliaze count for while loop, strings for all data we will get from json
            //all data comes out as strings so for int values we need to cast them into int values later
            //while count is less than length of jsonarray
            // while (count < jsonArray.length()) {
            //get the object put drugid into drugid ect..


            JSONObject jo = jsonArray.getJSONObject(count);



            new_drug = jo.getString("new_drug");
            count++;

            jo = jsonArray.getJSONObject(count);
            ship_success = jo.getString("ship_success");
            count++;

            jo = jsonArray.getJSONObject(count);
            info_success = jo.getString("info_success");

            System.out.println("here are new_drug,ship_success,info_success: "+new_drug+" "+ship_success+" "+info_success);


            int_new_drug=Integer.parseInt(new_drug);
            int_ship_success=Integer.parseInt(ship_success);
            int_info_success=Integer.parseInt(info_success);


            //increment count
            // count++;
            //}

        } catch (JSONException e) {

            System.out.println("JSON Exception on try to get values for errors");

        }

        if(int_new_drug==0 && int_info_success==1 && int_ship_success ==1) {

            Log.d("Test1", "Case1");
            error=0;
            return  error;
        }
        else if (int_new_drug==1 && int_info_success==1 && int_ship_success ==1){
            Log.d("Test1", "Case2");
            error=1;
            return  error;
        }
        else{
            Log.d("Test1","Case3");
            error=2;
            Log.d("Test1", "Error="+error);
            return  error;
        }
    }
}
