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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.FetchVisits_ExpListAdapter;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.VisitFields;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;
import ethanfortin_nicaragua.elbluffhospital.UploadFile;

public class FetchVisits extends AppCompatActivity {



    private Button addDocument;

    final Context context = this;
    ArrayList<VisitFields> patVisitdata = new ArrayList();
    //int count = 0;
    //int hash_count = 0;
    String sID;
    Spinner year;
    //String dob_day_temp1, dob_month_temp1;
   // int dob_day_temp, dob_month_temp, dob_year_temp;
    //AlertDialog db_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_visits);

        year= (Spinner) findViewById(R.id.filter_year);
        ArrayList<String> years=new ArrayList<String>();
        int thisYear= Calendar.getInstance().get(Calendar.YEAR);
        for (int i=thisYear; i>=2000; i--){
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);

        Spinner spinYear = (Spinner)findViewById(R.id.filter_year);
        spinYear.setAdapter(adapter);
        // String[] months=new String[]={"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul","Aug", "Sep", "Nov", "Dec"}
        //addDocument = (Button) findViewById(R.id.add_doc);
        /*addDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDoc();
            }
        });*/

        Intent intent = getIntent();
        sID = intent.getStringExtra("patid");
        //patientVisitFetch(sID);
/*
        final FloatingActionButton addVisit = (FloatingActionButton) findViewById(R.id.fab_add);

        addVisit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(FetchVisits.this);
                final View subView = inflater.inflate(R.layout.dialog_add_visit, null);

                final TextView Date = (TextView) subView.findViewById(R.id.vh_calPicker);
                final EditText Reason = (EditText) subView.findViewById(R.id.vh_reason);
                final EditText Doctor = (EditText) subView.findViewById(R.id.vh_doctor);

                Date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater inflater = LayoutInflater.from(FetchVisits.this);
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

                                Date.setText(dob_day_temp1+"-"+dob_month_temp1+"-"+dob_year_temp);
                            }
                        });

                        builderSingle1.show();
                    }
                });

                final AlertDialog alertDialogBuilder = new AlertDialog.Builder(context)
                        .setTitle("Anadir la visita nueva")
                        .setView(subView)
                        .setMessage("Ingrese la informacion de la visita")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                               if(Reason.getText().toString().trim().equals(""))
                               {Reason.setError("Necesitas un razon");}
                                else if(Date.getText().toString().trim().equals(""))
                                {Date.setError("Necesitas un razon");}
                                else if(Doctor.getText().toString().trim().equals(""))
                                {Doctor.setError("Necesitas un razon");}

                              else{
                                String reason=Reason.getText().toString();
                                String doctor=Doctor.getText().toString();
                                String date= dob_year_temp+"-"+dob_month_temp1+"-"+dob_day_temp1;

                                newVH(date,sID,reason,doctor);
                                Log.d("ethan date",date);}


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

    private void newVH(final String date, final String ID, final String reason, final String doctor) {
        class get_newVH extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(FetchVisits.this, "Buscando...", "Espera, por favor", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Log.d("Test1","Here is s"+s);
                int eMessage;
                eMessage= jsonParseAdd(s);

                if (eMessage==1){
                    db_message = new AlertDialog.Builder(FetchVisits.this).create();
                    db_message.setTitle("Accion Termino");
                    db_message.setMessage("Nuevo Visit Creado");
                    db_message.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    db_message.show();
                }
                else if (eMessage==0){
                    db_message = new AlertDialog.Builder(FetchVisits.this).create();
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
            }
            // In here, split between argChoice Value (1 or 2)
            protected String doInBackground(Void... params) {

                RequestHandler reqHan1 = new RequestHandler();
                HashMap<String, String> map1 = new HashMap<>();
                map1.put("patid",ID);
                Log.d("Ethan transaction_date",date);
                map1.put("visitdate",date);
                map1.put("reason", reason);
                map1.put("doctor",doctor);

                String s;
                s = reqHan1.sendPostRequest(ConnVars.URL_ADD_VH_ROW ,map1);
                Log.d("Test1", "S is :"+s);
                return s;
            }
        }
        get_newVH nh = new get_newVH();
        nh.execute();
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
            jsonArray = jsonObject.getJSONArray(ConnVars.TAG_VISITHISTORY_ERRORMESSAGES);

            JSONObject jo = jsonArray.getJSONObject(count);
            Log.d("Test1", "Jo.count0"+jsonArray.getJSONObject(0));

            message = jo.getString("success");
            Log.d("Test1", "Json string: " + message);

            try {
                int_message = Integer.parseInt(message);
                Log.d("Test1", "JsonInt:" + int_message);
            } catch (NumberFormatException n) {}
        }
        catch(JSONException p){
            Log.d("Test1", "JSON ERROR MESSAGE");
        }
        return int_message;
    }



    private void addDoc() {
        Intent intent = getIntent();
        String patid = intent.getStringExtra("patid");

        Intent i = new Intent(this, UploadFile.class);
        i.putExtra("patid", patid);
        startActivity(i);
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
                return true;
            case R.id.pat_history:
                Intent newintent1 = new Intent(getBaseContext(), FetchVisits.class);
                newintent1.putExtra("patid", sID);
                startActivity(newintent1);
                return true;
            case R.id.pat_prescription:
                Intent newintent2 = new Intent(getBaseContext(), FetchPrescriptions.class);
                newintent2.putExtra("patid", sID);
                startActivity(newintent2);
            default:
                return super.onOptionsItemSelected(item);
        }
    */}

    @Override
    public void onBackPressed(){
        Intent go_back_to_PGI_2 = new Intent(this, SearchAddPatients.class);
        startActivity(go_back_to_PGI_2);
    }

}