package ethanfortin_nicaragua.elbluffhospital.PatientInfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.FetchPrescriptions_ExpListAdapter;
import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.ShipmentAdapter1;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.PrescriptionFields;


import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;

public class FetchPrescriptions extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;

    Context context = this;
    ArrayList<PrescriptionFields> patRXdata = new ArrayList();
    ArrayList<String> drugname= new ArrayList<>();
    ListView LV_patRX;
    int count = 0;
    String sID;
    String dob_day_temp1, dob_month_temp1;
    int dob_day_temp, dob_month_temp, dob_year_temp;
    AlertDialog db_message;

    ShipmentAdapter1 adapter;
    ListView lv;
    String selectedListItem;
    EditText filter;

    String drugid;
    HashMap<String, String> pickerinfo = new HashMap<>();

    int hash_count=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_prescriptions);

        Intent intent = getIntent();
        sID = intent.getStringExtra("patid");
        patientRXFetch(sID);




        /**ML: Testing to see if the data that comes back is correct!**/

        //patientRXFetch(sID);


        /**ML: Use FAB to create a new prescription **/
        final FloatingActionButton addRx = (FloatingActionButton) findViewById(R.id.fab_add);

        addRx.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(FetchPrescriptions.this);
                final View subView = inflater.inflate(R.layout.dialog_new_prescription, null);

                //nameListFetch();
                // final EditText entryDate = (EditText) subView.findViewById(R.id.);
                final EditText doctor=(EditText) subView.findViewById(R.id.etDoctor);
                final TextView calendar = (TextView) subView.findViewById(R.id.popout_calendar);
                final TextView drugname = (TextView) subView.findViewById(R.id.popout_list);
                final EditText quantity = (EditText) subView.findViewById(R.id.etQuantity);
                final EditText duration = (EditText) subView.findViewById(R.id.etDuration);
                final EditText reason = (EditText) subView.findViewById(R.id.etReason);
                final EditText directions = (EditText) subView.findViewById(R.id.etDirections);



                drugname.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        LayoutInflater inflater = LayoutInflater.from(FetchPrescriptions.this);
                        View subView = inflater.inflate(R.layout.dialog_drug_picker, null);

                       lv = (ListView) subView.findViewById(R.id.drug_list_full);
                        filter = (EditText)subView.findViewById(R.id.filter_bar2);

                        //Build dialog set it to subview
                        AlertDialog.Builder builderSingle2 = new AlertDialog.Builder(context);
                        builderSingle2.setView(subView);

                        nameListFetch();

                        builderSingle2.setNegativeButton(
                                "Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                        builderSingle2.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        drugname.setText(selectedListItem);
                                    }
                        });

                        builderSingle2.show();
                    }

                });

                calendar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater inflater = LayoutInflater.from(FetchPrescriptions.this);
                        View subView = inflater.inflate(R.layout.dialog_dob_picker, null);

                        //Build dialog set it to subview
                        AlertDialog.Builder builderSingle1 = new AlertDialog.Builder(context);
                        builderSingle1.setView(subView);
                        Calendar c = Calendar.getInstance();
                        //Date date= c.getTime();
                        // Log.d("Ethan Date", c.get(Calendar.DAY_OF_MONTH)+ " "+ c.get(Calendar.MONTH)+1+  " "+ c.get(Calendar.YEAR));

                        final NumberPicker day_picker = (NumberPicker) subView.findViewById(R.id.day_picker) ;
                        //Here is were we will set values for number picker
                        day_picker.setMinValue(1);
                        day_picker.setMaxValue(31);
                        day_picker.setValue(c.get(Calendar.DAY_OF_MONTH));
                        day_picker.setWrapSelectorWheel(true);

                        final NumberPicker month_Picker = (NumberPicker) subView.findViewById(R.id.month_picker) ;
                        month_Picker.setMinValue(1);
                        month_Picker.setMaxValue(12);
                        month_Picker.setValue(c.get(Calendar.MONTH)+1);
                        month_Picker.setWrapSelectorWheel(true);

                        final NumberPicker year_picker = (NumberPicker) subView.findViewById(R.id.year_picker);
                        year_picker.setMinValue(1900);
                        year_picker.setMaxValue(2500);
                        year_picker.setValue(c.get(Calendar.YEAR));
                        year_picker.setWrapSelectorWheel(true);

                        builderSingle1.setNegativeButton(
                                "Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builderSingle1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dob_day_temp=day_picker.getValue();
                                if (dob_day_temp<=9){
                                    dob_day_temp1= "0"+dob_day_temp;
                                }
                                else{ dob_day_temp1=dob_day_temp+"";}
                                dob_month_temp=month_Picker.getValue();
                                if (dob_month_temp<=9){
                                    dob_month_temp1= "0"+dob_month_temp;
                                }
                                else{ dob_month_temp1=dob_month_temp+"";}
                                dob_year_temp=year_picker.getValue();

                                dialog.cancel();

                                calendar.setText(dob_day_temp1+"-"+dob_month_temp1+"-"+dob_year_temp);
                                //dob_temp=dob_month_temp1+"-"+dob_month_temp1+"-"+dob_year_temp;
                            }
                        });
                        builderSingle1.show();

                    }
                });

                // Get Current Date
                // SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                // Date date = new Date();
                // entryDate.setText(dateFormat.format(date));

                final AlertDialog alertDialogBuilder = new AlertDialog.Builder(context)
                        .setTitle("Anadir la prescripción nueva")
                        .setView(subView)
                        .setMessage("Ingrese la informacion de la prescripción")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                String s_doctor= doctor.getText().toString();
                                String s_calendar=dob_year_temp+"-"+dob_month_temp1+"-"+dob_day_temp1;
                                //changed this, it's not sent only drugID is
                                String s_drug = drugname.getText().toString();
                                String s_quantity=quantity.getText().toString();
                                String s_duration = duration.getText().toString();
                                String s_reason=reason.getText().toString();
                                String s_directions=directions.getText().toString();

                                if(doctor.getText().toString().trim().equals("")){
                                    doctor.setError("Necesitas entrar un doctor");
                                    Log.d("Maddi", "got heree too");
                                }
                                else if (s_calendar.length()<4){
                                    calendar.setError("Necesitas entrar una fecha");
                                }
                                else if(drugname.getText().toString().trim().equals(""))
                                {
                                    drugname.setError("Necesitas entrar una droga");
                                }
                                else if (quantity.toString().trim().equals("")){
                                    quantity.setError("Necesitas entrar una cantidad");
                                }
                                else if (duration.toString().trim().equals("")){
                                    duration.setError("Necesitas entrar una Duracion");
                                }
                                else if (reason.toString().trim().equals("")){
                                    reason.setError("Necesitas entrar una razon");
                                }
                                else if (directions.toString().trim().equals("")){
                                    directions.setError("Necesitas entrar direciones");
                                }
                                else{
                                    Log.d("Maddie", "got here 3");
                                    String s_drugID = drugid.toString();
                                    System.out.println("s_drugID is being added as:" + s_drugID);
                                    newPrescription(s_doctor,s_calendar,s_drug,s_drugID,s_quantity,s_duration,s_reason,s_directions);
                                    Log.d("ethan s_calendar",s_calendar);}


                            }

                        })

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        })


                        .create();
                alertDialogBuilder.show();
            }


        });
    }


    // Fetches JSON String of all drugs in inventory
    private void nameListFetch() {

        class fetchNameList extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(FetchPrescriptions.this,"Buscando...","Espera, por favor",false,false);
            }

            // Once JSON received correctly, parse and display it
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                String drugName;

                //lv = (ListView) subView.findViewById(R.id.drug_list_full);
                ArrayList<String> list = new ArrayList<>();
                int count = 0;

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray resArr = jsonObject.getJSONArray(ConnVars.TAG_DRUGINFO);

                    while (count < resArr.length()) {
                        System.out.println("Array: " + list);
                        JSONObject resObj = resArr.getJSONObject(count);
                        drugName = resObj.getString(ConnVars.TAG_DRUGINFO_NAME);
                        drugid = resObj.getString(ConnVars.TAG_DRUGINFO_ID);
                        list.add(drugName);

                        pickerinfo.put(drugName, drugid);
                        //System.out.println("hashmapout:" + pickerinfo.toString());
                        count++;
                    }

                } catch (JSONException j) {
                    System.out.println("JSON Exception occurred...");
                }


                //adapter = new DrugNameAdapter(FetchPrescriptions.this, list);

                //final EditText filter = (EditText)findViewById(R.id.filter_bar2);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        selectedListItem = (String) lv.getItemAtPosition(position);
                        System.out.println("ITEM = " + selectedListItem);
                        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        mgr.hideSoftInputFromWindow(filter.getWindowToken(), 0);
                        //drugFetch(selectedListItem);
                    }
                });

                lv.setAdapter(adapter);


                filter.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        FetchPrescriptions.this.adapter.getFilter().filter(s);
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





    private void newPrescription(final String doctor, final String transaction_date, final String drug, final String drugid, final String quantity, final String duration, final String reason,
                            final String directions ) {
        class get_newPrescription extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(FetchPrescriptions.this, "Buscando...", "Espera, por favor", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Log.d("Test1","Here is s"+s);
                int eMessage;
                eMessage= jsonParseAdd(s);

                if (eMessage==11){
                    db_message = new AlertDialog.Builder(FetchPrescriptions.this).create();
                    db_message.setTitle("Accion Termino");
                    db_message.setMessage("Nuevo Prescripción Creado");
                    db_message.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    db_message.show();
                }
                else if (eMessage==10){
                    db_message = new AlertDialog.Builder(FetchPrescriptions.this).create();
                    db_message.setTitle("ERRORES!");
                    db_message.setMessage("Habia errores.\nAseguranse que la informacion esta correcto");
                    db_message.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    db_message.show();
                }
                else{
                    db_message = new AlertDialog.Builder(FetchPrescriptions.this).create();
                    db_message.setTitle("ERRORES!");
                    db_message.setMessage("Habia errores.\n Revisa la informacion. Aseguranse que todo esta llenado\nAseguranse que tienes suficiente medecina en la sistema");
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

                RequestHandler reqHan1 = new RequestHandler();

                HashMap<String, String> map1 = new HashMap<>();

               // System.out.println("pickerinfo is:" + pickerinfo.toString());
                String tempDrugID = pickerinfo.get(selectedListItem).toString();
                System.out.println("tempDrugID is:" + tempDrugID);
                map1.put("did",tempDrugID);
                //Log.d("Ethan transaction_date",transaction_date);
                map1.put("date",transaction_date);
                map1.put("q", quantity);
                map1.put("pid",sID);
                map1.put("dir",directions);
                map1.put("dur",duration);
                map1.put("dr", doctor);
                map1.put("smp",reason);

                String s;
                s = reqHan1.sendPostRequest(ConnVars.URL_UPDATE_DRUG_RX_ROW ,map1);
                Log.d("Test1", "S is :"+s);
                return s;
            }
        }
        get_newPrescription np = new get_newPrescription();
        np.execute();

        System.out.println("Selected List Item:" + selectedListItem);
    }
    private int jsonParseAdd(String json_string) {

        Log.d("Test1", "GOT to JsonParseADd");
        Context context = this;

        int count = 0, int_message = 00;
        String message = "00";

        JSONObject jsonObject;
        JSONArray jsonArray;
        try {

            // Make JSONObject and designate the array jsonArray to grab the array
            // that's title is "New_ErrorMessages" from the received object
            Log.d("Test1", "GOT to JsonParseADd");
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray(ConnVars.TAG_NEWPRE_ERRORMESSAGES);

            //initiliaze count for while loop, strings for all data we will get from json
            //all data comes out as strings so for int values we need to cast them into int values later
            //while count is less than length of jsonarray
            // while (count < jsonArray.length()) {
            //get the object put drugid into drugid ect..




             while (count < jsonArray.length()) {
            JSONObject jo = jsonArray.getJSONObject(count);
            Log.d("Test1", "Jo.count0"+jsonArray.getJSONObject(0));
            //Log.d("Test1", "Jo.count1"+jsonArray.getJSONObject(1));
            //Log.d("Test1", "Jo.count2"+jsonArray.getJSONObject(2));
            message = jo.getString("success");
            Log.d("Test1", "Json string: " + message);
            try {
                int_message = Integer.parseInt(message);
                Log.d("Test1", "JsonInt:" + int_message);
            } catch (NumberFormatException n) {}
              count++;
            }
        }
        catch(JSONException p){
            Log.d("Test1", "JSON ERROR MESSAGE");
        }
        return int_message;
    }


  //  /**ML: Needs to be changed back to onClick for the patient selection, take out testing button**/
   // public void onClick2(View V){

        //Get patient ID
        /**ML: This would be from the pop up selection dialog, here is hardcoded**/
   //     sID = "patid0";

        //Search by patient ID
        /**ML: Option to add a second case (argChoice) which searches by patient name**/

    //}

    /** ML: This method is accessed from inside the onClick**/
    private void patientRXFetch(final String argVal){

        class fetchPatientRX extends AsyncTask<Void, Void, String> {

            //In here, get the information from the user's selection
            protected String doInBackground(Void... params){

                RequestHandler reqHan = new RequestHandler();
                HashMap<String, String> map = new HashMap<>();
                String s;

                /**ML- argVal needs to be accesed from user selection on dialog with patients, done in onClick method**/


                        map.put("patid", argVal);
                        //search by user selection from pop up menu, map value should be key value
                        s = reqHan.sendGetRequestParam(ConnVars.URL_FETCH_PAT_RX, map);




                return s;
            }

            //Once JSON received correctly, parse and display it
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                rxShow(s);
            }
        }

        fetchPatientRX patRX = new fetchPatientRX();
        patRX.execute();
        System.out.println("Completed patientRXFetch method");
    }

    /**ML: This method gets the data returned from the DB in the JSON format and sets it to a variable**/
    public void rxShow(String json){

        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray resArr = jsonObject.getJSONArray(ConnVars.TAG_PRESCRIPTIONS);
            System.out.println("got past resArr");

            /**ML: r_** are the values returned from the DB, resObj is the first data block
             * returned from the JSON array, getString gets the value associated with the key
             * that is denoted by the ConnVars tag
             */

            while(count < resArr.length()) {
                /**ML: gets  the first data block, will repeat if necessary**/
                JSONObject resObj = resArr.getJSONObject(count);
                String r_rxID = resObj.getString(ConnVars.TAG_PRESCRIPTIONS_RXID);
                String r_drugID = resObj.getString(ConnVars.TAG_PRESCRIPTIONS_DRUGID);
                String r_transDate = resObj.getString(ConnVars.TAG_PRESCRIPTIONS_TRANSDATE);
                String r_quantity = resObj.getString(ConnVars.TAG_PRESCRIPTIONS_QUANTITY);
                String r_patID = resObj.getString(ConnVars.TAG_PRESCRIPTIONS_PATID);
                String r_directions = resObj.getString(ConnVars.TAG_PRESCRIPTIONS_DIRECTIONS);
                String r_duration = resObj.getString(ConnVars.TAG_PRESCRIPTIONS_DURATION);
                String r_doctor = resObj.getString(ConnVars.TAG_PRESCRIPTIONS_DOCTOR);
                String r_symptoms = resObj.getString(ConnVars.TAG_PRESCRIPTIONS_SYMPTOMS);


                /**ML: This needs to be fixed, JSON exception occurs here because it needs to come from a table join**/
                //String r_drugName = resObj.getString(ConnVars.TAG_PRESCRIPTIONS_DRUGNAME);
                // System.out.println("10");

                System.out.println("9");
                String r_drugName = resObj.getString(ConnVars.TAG_PRESCRIPTIONS_DRUGNAME);
                System.out.println("10 "+r_drugName);


                        try{
                            Integer r_quantity_int = Integer.parseInt(r_quantity);
                            patRXdata.add(new PrescriptionFields(r_rxID, r_drugID, r_transDate, r_quantity_int, r_patID, r_directions, r_duration, r_doctor, r_symptoms));
                            drugname.add(r_drugName);
                            Log.d("Go kids",patRXdata.get(count).D_RXId().toString());
                            Log.d("go kids round 2", drugname.get(count));
                        } catch(NumberFormatException nfe){
                            System.out.println("Number format exception occured!");
                        }

                count++;
                hash_count++;
            }


        } catch (JSONException j){
            System.out.println("JSON Exception occurred...HEAAAA");
        }

        //ArrayAdapter<PrescriptionFields> adapter2 = new PrescriptionAdapter(context, patRXdata);
        //LV_patRX = (ListView) findViewById(R.id.LV_fetchPrescriptions);
        //LV_patRX.setAdapter(adapter2);

        listView = (ExpandableListView)findViewById(R.id.exp_list);
        initData();
        listAdapter = new FetchPrescriptions_ExpListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);
    }



    private void initData() {
        /**ML: Need to make a count variable to select all prescriptions not just the first one (get rid of the o)**/
        /*PrescriptionFields temp1 = patRXdata.get(0);
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        //This is where you set the prescription ID as the Group Item
        listDataHeader.add(temp1.C_rxid);

        //This is where you set the child items of each group item
        List<String> rx1 = new ArrayList<>();
        rx1.add("ID de Paciente:    " + temp1.C_patid);
        rx1.add("ID de Medicina:    " + temp1.C_drugid);
        rx1.add("La Indicaciones:    " + temp1.C_directions);
        rx1.add("El Doctor:    " + temp1.C_doctor);
        rx1.add("La Droga:    " + temp1.C_drugname);
        rx1.add("La Duración:    " + temp1.C_duration);
        rx1.add("La Cantidad:    " + temp1.C_quantity);
        rx1.add("La Sintoma:    " + temp1.C_symptoms);
        rx1.add("La fecha de Transacción:    " +  temp1.C_transdate);

        //Put the data in the HashMap
        listHash.put(listDataHeader.get(0), rx1);

    }*/

        /**ML: Need to make a count variable to select all visits not just the first one (get rid of the o)**/
        int H_count = 0;
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        while (H_count < hash_count) {
            PrescriptionFields temp1 = patRXdata.get(H_count);
            String drugname_temp= drugname.get(H_count);

            //This is where you set the prescription IS as the Group Item
            listDataHeader.add(temp1.C_rxid);

            //This is where you set the child items of each group item
            List<String> rx1 = new ArrayList<>();
            rx1.add("ID de Paciente:    " + temp1.C_patid);
            rx1.add("ID de Medicina:    " + temp1.C_drugid);
            rx1.add("La Indicaciones:    " + temp1.C_directions);
            rx1.add("El Doctor:    " + temp1.C_doctor);
            rx1.add("La Droga:    " + drugname_temp);
            rx1.add("La Duración:    " + temp1.C_duration);
            rx1.add("La Cantidad:    " + temp1.C_quantity);
            rx1.add("La Sintoma:    " + temp1.C_symptoms);
            rx1.add("La fecha de Transacción:    " + temp1.C_transdate);
            ;

            //Put the data in the HashMap
            listHash.put(listDataHeader.get(H_count), rx1);

            H_count++;
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pat_gen_info:
                Intent newintent = new Intent(getBaseContext(), FetchPatientInfo.class);
                newintent.putExtra("patid", sID);
                startActivity(newintent);
                //startActivity(new Intent(this, FetchPatientInfo.class));
                return true;
            case R.id.pat_history:
                Intent newintent1 = new Intent(getBaseContext(), FetchVisits.class);
                newintent1.putExtra("patid", sID);
                startActivity(newintent1);
                //startActivity(new Intent(this, FetchVisits.class));
                return true;
            case R.id.pat_prescription:
                Intent newintent2 = new Intent(getBaseContext(), FetchPrescriptions.class);
                newintent2.putExtra("patid", sID);
                startActivity(newintent2);
                //startActivity(new Intent(this, FetchPrescriptions.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //!!

    @Override
    public void onBackPressed(){
        Intent go_back_to_PGI = new Intent(this, SearchAddPatients.class);
        startActivity(go_back_to_PGI);
    }


}









