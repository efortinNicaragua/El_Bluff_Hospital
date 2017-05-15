package ethanfortin_nicaragua.elbluffhospital.PatientInfo;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.PatientinfoAdapter;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.PatientinfoFields;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;

public class SearchAddPatients extends Activity {
    ArrayList<PatientinfoFields> patinfo = new ArrayList();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    String patname;
    String patid;
    String address;
    String telephone;
    String gender;
    String marstat;
    String s_dob;
    Date dob;
    String s_children;
    int children;
    String s_height;
    int height;
    String s_weight;
    int weight;
    String allergies;
    String medcond;
    ListView listView;
    String temp_dob_string;
    Dialog findPatient_dialog;
    PatientinfoFields selectedListItem;
    AlertDialog db_message;
    String gender_temp, married_temp, dob_temp;
    int children_temp, dob_day_temp, dob_month_temp, dob_year_temp;
    double height_temp,weight_temp;
    int height_temp_int,weight_temp_int;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_add_patients);

        /** ATTENTION: This was auto-generated to implement the App Indexing API.
         See https://g.co/AppIndexing/AndroidStudio for more information.*/
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    }

    //take this out after testing!
    public void rxClick(View v){
        Intent shortcut_RX = new Intent(this, FetchVisits.class);
        startActivity(shortcut_RX);
    }

    public void buscar(View v) {
        EditText name_EditText = (EditText) findViewById(R.id.edit_name);
        EditText id_EditText = (EditText) findViewById(R.id.edit_ID);
        String sName = name_EditText.getText().toString();
        String sID = id_EditText.getText().toString();



        if ((sName.matches("")) & (sID.matches(""))) {
            Toast.makeText(this, "Necesitas Entrar un ID o un Nombre", Toast.LENGTH_SHORT).show();

        }

        patientFetch(sID,sName);
        /*LayoutInflater inflater = LayoutInflater.from(SearchAddPatients.this);
        View findPatient = inflater.inflate(R.layout.dialog_find_patient,null);

        AlertDialog.Builder builder= new AlertDialog.Builder(SearchAddPatients.this);
        builder.setView(findPatient);

        builder.setTitle("Elige un Paciente");*/


          /*  AlertDialog.Builder builderSingle = new AlertDialog.Builder(SearchAddPatients.this);
            builderSingle.setTitle("Elige el paciente");
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    SearchAddPatients.this,
                    android.R.layout.select_dialog_singlechoice);
            arrayAdapter.add("Pablo Himenz 8/7/1990 M 194356");
            arrayAdapter.add("Pablo Himnez 6/19/2004 M 23345");
            arrayAdapter.add("Pablo Shancez 6/1/2010  M 245674");
            arrayAdapter.add("Pablo Tarin 12/3/2015   M 264510");


            builderSingle.setNegativeButton(
                    "cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });


            builderSingle.setAdapter(
                    arrayAdapter,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent_patientData = new Intent(SearchAddPatients.this, FetchPatientInfo.class);
                            startActivity(intent_patientData);
                            /**
                             * This next line is the variable that will be used to reference the correct row
                             * in the database for the patient that was clicked
                             */
//                            String strName = arrayAdapter.getItem(which);

                   /*     }
                    });
            builderSingle.show();
        }*/


    }

    private void patientFetch(final String patid, final String patname) {
        class fetch_patientinfo extends AsyncTask<Void, PatientinfoFields, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SearchAddPatients.this, "Buscando...", "Espera, por favor", false, false);
            }

            // Once JSON received correctly, parse and display it
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                jsonParse(s);
            }

            // In here, split between argChoice Value (1 or 2)
            protected String doInBackground(Void... params) {
                String patid_temp, patname_temp;
                patid_temp='%'+patid+'%';
                patname_temp='%'+patname+'%';

                RequestHandler reqHan = new RequestHandler();
                HashMap<String, String> map = new HashMap<>();
                map.put("patid",patid_temp);
                map.put("patname",patname_temp);
                String s;

                s = reqHan.sendGetRequestParam (ConnVars.URL_FETCH_PATIENT_GENERAL_INFO, map);

                return s;
            }
        }
        fetch_patientinfo pinfo = new fetch_patientinfo();
        pinfo.execute();
    }

    private void jsonParse(String json_string) {

        Context context=this;

        //ArrayList<Class_FetchPatientGenInfo> patGenInfo = new ArrayList();
        //ListView listView;
       // ArrayList<PatientinfoFields> patGenInfo = new ArrayList();

        int totalCast, count=0;
        String patid, patname, address, telephone, gender,marstat, allergies, medcond, children, height, weight;
        int children_int, height_int, weight_int;

        JSONObject jsonObject;
        JSONArray jsonArray;
        try {

            // Make JSONObject and designate the array jsonArray to grab the array
            // that's title is "druginfo" from the received object
            jsonObject = new JSONObject(json_string);
            System.out.println("made json object: "+json_string);
            jsonArray = jsonObject.getJSONArray(ConnVars.TAG_PATIENTINFO);
            System.out.println("put stuff into jsonobject");

            //initiliaze count for while loop, strings for all data we will get from json
            //all data comes out as strings so for int values we need to cast them into int values later
            //while count is less than length of jsonarray
            while (count < jsonArray.length()) {
                    //get the object put drugid into drugid ect..
                    JSONObject jo = jsonArray.getJSONObject(count);
                    patid= jo.getString("patid");
                    patname= jo.getString("patname");
                    address = jo.getString("address");
                    telephone= jo.getString("telephone");
                    gender = jo.getString("gender");
                    marstat = jo.getString("marstat");
                    allergies=jo.getString("allergies");
                    medcond = jo.getString("medcond");
                    children = jo.getString("children");
                    height = jo.getString("height");
                    weight=jo.getString("weight");
                    temp_dob_string=jo.getString("dob");
                    System.out.println("The DOB IS " + temp_dob_string);

                    //try to cast string into int
                    try {
                        children_int = Integer.parseInt(children);
                        height_int = Integer.parseInt(height);
                        weight_int = Integer.parseInt(weight);
                        //add this data as DruginfoFields to ArrayList
                        patinfo.add(new PatientinfoFields(patid, patname , address, telephone, gender, marstat, children_int,height_int,weight_int,allergies,medcond,temp_dob_string));

                    } catch (NumberFormatException nfe) {
                        System.out.println("Number Format Exception occurred...");
                    }
                //increment count
                count++;
            }

        } catch (JSONException e) {

        }

        ArrayAdapter<PatientinfoFields> adapter = new PatientinfoAdapter(context, patinfo);


       /* //testing purposes
        patinfo.add(new PatientinfoFields("patid1", "ethan", "5", "774", "m", "m", 3, 4, 5, "1", "medcond", "123"));
        patinfo.add(new PatientinfoFields("patid2", "GO GO Dancer", "5", "774", "m", "m", 3, 4, 5, "1", "medcond", "123"));
        patinfo.add(new PatientinfoFields("patid3", "STop", "5", "774", "m", "m", 3, 4, 5, "1", "medcond", "123"));
        patinfo.add(new PatientinfoFields("patid4", "Huck", "5", "774", "m", "m", 3, 4, 5, "1", "medcond", "123"));
        patinfo.add(new PatientinfoFields("patid5", "CATCH!!!!", "5", "774", "m", "m", 3, 4, 5, "1", "medcond", "123"));
*/
        findPatient_dialog= new Dialog(this);
        findPatient_dialog.setTitle("Elige un paciente");
        LayoutInflater li=(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v= li.inflate(R.layout.dialog_find_patient,null,false);
        findPatient_dialog.setContentView(v);

        //final ListView listView;
        //final ArrayAdapter<PatientinfoFields> adapter = new ArrayAdapter_FetchPatientInfo(context, patinfo);
        listView = (ListView) findPatient_dialog.findViewById(R.id.listview_patientgeninfo);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setSelector(R.drawable.greygradient);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectedListItem = (PatientinfoFields) listView.getItemAtPosition(position);

            }
        });

        listView.setAdapter(adapter);

        findPatient_dialog.setCancelable(true);
        findPatient_dialog.show();


    }

   /* private class EfficientAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public EfficientAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return patinfo.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            if (convertView == null || convertView.getTag() == null) {
                convertView = mInflater.inflate(R.layout.row_druginventory_all, null);
                holder = new ViewHolder();
                holder.backgroundID = (TextView) convertView
                        .findViewById(R.id.patid);
                holder.backgroundPatName= (TextView) convertView
                        .findViewById(R.id.patname);
                holder.backgroundDOB= (TextView) convertView
                        .findViewById(R.id.dob);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if(position == selectedListItem) {
                holder.backgroundID.setBackgroundColor(Color.GRAY);
                holder.backgroundPatName.setBackgroundColor(Color.GRAY);
                holder.backgroundDOB.setBackgroundColor(Color.GRAY);
            } else {
                holder.backgroundID.setBackgroundColor(Color.WHITE);
                holder.backgroundPatName.setBackgroundColor(Color.WHITE);
                holder.backgroundDOB.setBackgroundColor(Color.WHITE);
            }

            //holder.officesTitle.setText(data.get(position));

            return convertView;
        }

    }
    static class ViewHolder {
        TextView backgroundID;
        TextView backgroundPatName;
        TextView backgroundDOB;

    }*/

    public void NuevoPaciente(View V) {
        /**Need to make dialog_patient_preccription pull data from DB not my made up stuff
         *create layout inflater and subView assign sub view to dialog xml
         * */
        LayoutInflater inflater = LayoutInflater.from(SearchAddPatients.this);
        View subView = inflater.inflate(R.layout.dialog_new_patient, null);

        //Build dialog set it to subview
        AlertDialog.Builder builderSingle1 = new AlertDialog.Builder(this);
        builderSingle1.setView(subView);

        //Set dialog        title
        builderSingle1.setTitle("Entrar Nueva Paciente");
        final EditText edit_name2 = (EditText) subView.findViewById(R.id.newdialog_edit_name);
        final EditText edit_ID2 = (EditText) subView.findViewById(R.id.newdialog_edit_ID);
        final EditText edit_adress2 = (EditText) subView.findViewById(R.id.newdialog_edit_adress);
        final EditText edit_telephone2 = (EditText) subView.findViewById(R.id.newdialog_edit_Telephone);
        final Spinner edit_gender2 = (Spinner) subView.findViewById(R.id.newdialog_edit_gender);
        final Spinner edit_married2 = (Spinner) subView.findViewById(R.id.newdialog_edit_Married);
        final TextView edit_birthday2 =(TextView) subView.findViewById(R.id.newdialog_edit_Birthdate);
        final TextView edit_children2 = (TextView) subView.findViewById(R.id.newdialog_edit_Children);
        final TextView edit_height2 = (TextView) subView.findViewById(R.id.newdialog_edit_Height);
        final TextView edit_weight2 = (TextView) subView.findViewById(R.id.newdialog_edit_Weight);
        final EditText edit_allergies2 = (EditText) subView.findViewById(R.id.newdialog_edit_Allergies);
        final EditText edit_medicalConditions2 = (EditText) subView.findViewById(R.id.newdialog_edit_MedicalConditions);


        //Spinner adapters to set style an values of spinner
        SpinnerAdapter adap = new ArrayAdapter<String>(this, R.layout.spinner_item, new String[]{"Click para elegir","M", "F"});
        edit_gender2.setAdapter(adap);

        SpinnerAdapter adap1 = new ArrayAdapter<String>(this, R.layout.spinner_item, new String[]{"Click para elegir","Casado", "Soltero"});
        edit_married2.setAdapter(adap1);


        //Here are the methods that will handle when an item gets selected
        edit_gender2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                 String selectedItem = parent.getItemAtPosition(position).toString();
                gender_temp=selectedItem;

            }
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
        edit_married2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                married_temp=selectedItem;

            }
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        //Here is on click for text views to load dialgos
        edit_children2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(SearchAddPatients.this);
                View subView = inflater.inflate(R.layout.dialog_child_picker, null);

                //Build dialog set it to subview
                AlertDialog.Builder builderSingle1 = new AlertDialog.Builder(context);
                builderSingle1.setView(subView);

                final NumberPicker child_number = (NumberPicker) subView.findViewById(R.id.child_np) ;
                //Here is were we will set values for number picker
                child_number.setMinValue(0);
                child_number.setMaxValue(30);
                child_number.setValue(0);
                child_number.setWrapSelectorWheel(false);

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
                        children_temp=child_number.getValue();
                        edit_children2.setText(children_temp+"");
                    }
                });
                builderSingle1.show();

            }
        });
        edit_height2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(SearchAddPatients.this);
                View subView = inflater.inflate(R.layout.dialog_height_picker, null);

                //Build dialog set it to subview
                final AlertDialog.Builder builderSingle1 = new AlertDialog.Builder(context);
                builderSingle1.setView(subView);

                final NumberPicker height_picker = (NumberPicker) subView.findViewById(R.id.height_np) ;
                //Here is were we will set values for number picker
                height_picker.setMinValue(0);
                height_picker.setMaxValue(250);
                height_picker.setValue(165);
                height_picker.setWrapSelectorWheel(false);

                final NumberPicker height_decimalPicker = (NumberPicker) subView.findViewById(R.id.height_decimal_np) ;
                height_decimalPicker.setMinValue(0);
                height_decimalPicker.setMaxValue(99);
                height_decimalPicker.setValue(0);
                height_decimalPicker.setWrapSelectorWheel(true);

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
                        height_temp=height_picker.getValue()+(height_decimalPicker.getValue()/100.0);
                        height_temp_int=height_picker.getValue();
                        dialog.cancel();
                        edit_height2.setText(height_temp+" cm");
                    }
                });
                builderSingle1.show();

            }
        });

        edit_weight2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(SearchAddPatients.this);
                View subView = inflater.inflate(R.layout.dialog_height_picker, null);

                TextView title = (TextView) subView.findViewById(R.id.text_weightPicker);
                title.setText("Elige la pesa en Kilogramas");
                //Build dialog set it to subview
                AlertDialog.Builder builderSingle1 = new AlertDialog.Builder(context);
                builderSingle1.setView(subView);

                final NumberPicker height_picker = (NumberPicker) subView.findViewById(R.id.height_np) ;
                //Here is were we will set values for number picker
                height_picker.setMinValue(0);
                height_picker.setMaxValue(200);
                height_picker.setValue(70);
                height_picker.setWrapSelectorWheel(false);

                final NumberPicker height_decimalPicker = (NumberPicker) subView.findViewById(R.id.height_decimal_np) ;
                height_decimalPicker.setMinValue(0);
                height_decimalPicker.setMaxValue(99);
                height_decimalPicker.setValue(0);
                height_decimalPicker.setWrapSelectorWheel(true);

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
                        weight_temp=height_picker.getValue()+(height_decimalPicker.getValue()/100.0);
                        weight_temp_int=height_picker.getValue();
                        dialog.cancel();
                        edit_weight2.setText(weight_temp+" kg");
                    }
                });
                builderSingle1.show();

            }
        });

        edit_birthday2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(SearchAddPatients.this);
                View subView = inflater.inflate(R.layout.dialog_dob_picker, null);

                //Build dialog set it to subview
                AlertDialog.Builder builderSingle1 = new AlertDialog.Builder(context);
                builderSingle1.setView(subView);
                Calendar c = Calendar.getInstance();
                //Date date= c.getTime();
               // Log.d("Ethan Date", c.get(Calendar.DAY_OF_MONTH)+ " "+ c.get(Calendar.MONTH)+1+  " "+ c.get(Calendar.YEAR));

                final NumberPicker height_picker = (NumberPicker) subView.findViewById(R.id.day_picker) ;
                //Here is were we will set values for number picker
                height_picker.setMinValue(1);
                height_picker.setMaxValue(31);
                height_picker.setValue(c.get(Calendar.DAY_OF_MONTH));
                height_picker.setWrapSelectorWheel(true);

                final NumberPicker height_decimalPicker = (NumberPicker) subView.findViewById(R.id.month_picker) ;
                height_decimalPicker.setMinValue(1);
                height_decimalPicker.setMaxValue(12);
                height_decimalPicker.setValue(c.get(Calendar.MONTH)+1);
                height_decimalPicker.setWrapSelectorWheel(true);

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
                    String dob_day_temp1, dob_month_temp1;
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dob_day_temp=height_picker.getValue();
                        if (dob_day_temp<=9){
                             dob_day_temp1= "0"+dob_day_temp;
                        }
                        else{ dob_day_temp1=dob_day_temp+"";}
                        dob_month_temp=height_decimalPicker.getValue();
                        if (dob_month_temp<=9){
                             dob_month_temp1= "0"+dob_month_temp;
                        }
                        else{ dob_month_temp1=dob_month_temp+"";}
                        dob_year_temp=year_picker.getValue();

                        dialog.cancel();

                        edit_birthday2.setText(dob_day_temp1+"-"+dob_month_temp1+"-"+dob_year_temp);
                        dob_temp=dob_month_temp1+"-"+dob_month_temp1+"-"+dob_year_temp;
                    }
                });
                builderSingle1.show();

            }
        });
        builderSingle1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builderSingle1.setPositiveButton(
                "Crear",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        /* This is to be used for demonstration where communication
                        to database is not an option. Take out for actual DB communication.


                        boolean cancel = false;
                        View focusView = null;

                        final String s_edit_name2 = edit_name2.getText().toString();
                        final String s_edit_ID2 = edit_ID2.getText().toString();
                        final String s_edit_adress2 = edit_adress2.getText().toString();
                        final String s_edit_telephone2 = edit_telephone2.getText().toString();
                        final String s_edit_gender2 = edit_gender2.getText().toString();
                        final String s_edit_married2 = edit_married2.getText().toString();
                        final String s_edit_birthday2 = edit_birthday2.getText().toString();
                        final String s_edit_children2 = edit_children2.getText().toString();
                        final String s_edit_height2 = edit_height2.getText().toString();
                        final String s_edit_weight2 = edit_weight2.getText().toString();
                        final String s_edit_allergies2 = edit_allergies2.getText().toString();
                        final String s_edit_medicalConditions2 = edit_medicalConditions2.getText().toString();

                       if(s_edit_name2.matches("")){
                           Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                           return;
                       }
                        if(s_edit_ID2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(s_edit_adress2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(s_edit_telephone2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(s_edit_gender2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(s_edit_married2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(s_edit_birthday2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(s_edit_children2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(s_edit_height2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(s_edit_weight2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(s_edit_allergies2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(s_edit_medicalConditions2.matches("")){
                            Toast.makeText(getApplicationContext(), "Por favor rellena todos los campos.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else{
                            Toast toast = Toast.makeText(getApplicationContext(), "Paciente guarde.", Toast.LENGTH_LONG);
                            toast.show();

                            Intent i = new Intent(getApplicationContext(), SearchAddPatients.class);
                            startActivity(i);
                        }
                        */


                        /***This is the code needed for db communication.***/

                        patname = edit_name2.getText().toString();
                        patid = edit_ID2.getText().toString();
                        address = edit_adress2.getText().toString();
                        telephone = edit_telephone2.getText().toString();
                        gender = gender_temp;
                        Log.d("Ethan gender",gender);
                        marstat = married_temp;
                        Log.d("Ethan married",marstat);
                        s_dob = dob_temp;
                        s_children =children_temp+"";
                        Log.d("Ethan children",s_children);
                        s_height = height_temp+"";
                        //s_height = height_temp_int+"";
                        s_weight = weight_temp+"";
                        //s_weight = weight_temp_int+"";
                        allergies = edit_allergies2.getText().toString();
                        medcond = edit_medicalConditions2.getText().toString();

                        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            //dob = dateFormat.parse(s_dob);
                            //Log.d("Test1", "dob: " +s_dob);
                            //children = Integer.decode(s_children);
                            //height = Integer.decode(s_height);
                            //weight = Integer.decode(s_weight);
                            newPatient(patname, patid, address, telephone, gender, marstat, s_dob,s_children,s_height,s_weight,allergies,medcond);
                        } catch (Exception e) {
                            Log.d("ethan","we failed");
                        }

                    }
                });
            builderSingle1.show();
    }
    private void newPatient(final String patname, final String patid, final String address, final String telephone, final String gender, final String marstat,
                            final String dob, final String children, final String height, final String weight, final String allergies, final String medcond ) {
        class get_newPatient extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SearchAddPatients.this, "Buscando...", "Espera, por favor", false, false);
            }

            // Once JSON received correctly, parse and display it
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Log.d("Test1","Here is s"+s);
                int eMessage;
                eMessage= jsonParseAdd(s);

                if (eMessage==11){
                    db_message = new AlertDialog.Builder(SearchAddPatients.this).create();
                    db_message.setTitle("Accion Termino");
                    db_message.setMessage("Nuevo Paciente Creo");
                    db_message.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    db_message.show();
                }
                else if (eMessage==10){
                    db_message = new AlertDialog.Builder(SearchAddPatients.this).create();
                    db_message.setTitle("ERRORES!");
                    db_message.setMessage("Habia errores.\n Aseguranse que este paciente ID no existe!\nAseguranse que la informacion esta correcto");
                    db_message.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    db_message.show();
                }
                else{
                    db_message = new AlertDialog.Builder(SearchAddPatients.this).create();
                    db_message.setTitle("ERRORES!");
                    db_message.setMessage("Habia errores.\n Revisa la informacion. Aseguranse que todo esta llenado");
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
                map1.put("p",patname);
                map1.put("i",patid);
                map1.put("a", address);
                map1.put("t",telephone);
                map1.put("g", gender);
                map1.put("m",marstat);
                map1.put("b", s_dob);
                map1.put("c",children);
                map1.put("h",height);
                map1.put("w", weight);
                map1.put("al",allergies);
                map1.put("me", medcond);

                Log.d("g,dob,c,h,w", gender+" " +s_dob+" " + children+" "+ height+" "+ weight);
                String s;
                s = reqHan1.sendPostRequest(ConnVars.URL_ADD_PATIENTINFO_ROW, map1);
                Log.d("Test1", "S is :"+s);
                return s;
            }
        }
        get_newPatient np = new get_newPatient();
        np.execute();
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
            jsonArray = jsonObject.getJSONArray(ConnVars.TAG_NEWPAT_ERRORMESSAGES);

            //initiliaze count for while loop, strings for all data we will get from json
            //all data comes out as strings so for int values we need to cast them into int values later
            //while count is less than length of jsonarray
            // while (count < jsonArray.length()) {
            //get the object put drugid into drugid ect..




           // while (count < jsonArray.length()) {
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
             //   count++;
            //}
        }
             catch(JSONException p){
                 Log.d("Test1", "JSON ERROR MESSAGE");
             }
        return int_message;
    }


    public void selectPatient_accept(View view){
        System.out.println(selectedListItem.getPatid());
        Intent intent = new Intent(getBaseContext(), FetchPrescriptions.class);
        intent.putExtra("patid", selectedListItem.getPatid());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent go_back = new Intent(this, DoctorMain.class);
        startActivity(go_back);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        client.disconnect();
    }





}
