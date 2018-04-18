package ethanfortin_nicaragua.elbluffhospital.PatientInfo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.PatientinfoAdapter;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.PatientinfoFields;
import ethanfortin_nicaragua.elbluffhospital.MainMenu;
import ethanfortin_nicaragua.elbluffhospital.R;
import ethanfortin_nicaragua.elbluffhospital.RequestHandler;

public class SearchAddPatients extends Activity {
    ArrayAdapter<PatientinfoFields> adapter;
    ListView listView;
    ArrayList<PatientinfoFields> patInfoData = new ArrayList();
    ArrayList<PatientinfoFields> temp_patInfoData = new ArrayList();
    EditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_add_patients);


        listView= (ListView) findViewById(R.id.patient_list);
        et_search=(EditText) findViewById(R.id.filter_bar2);
        patientFetch();
        et_search.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i1, int i2) {
            System.out.println("ontxt change s="+s);
            if(s.toString().equals("")){
                System.out.println("its empy="+s);
                patientFetch();
            }
            else{
                //perform search
                System.out.println("s="+s.toString());
                searchItem(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

            }
        });
    }



    public void searchItem(String textToSearch){

        for (int i=0; i<patInfoData.size();i++){
            System.out.println("drugname="+patInfoData.get(i).patname.toString());
        }


        ArrayList<PatientinfoFields> temp= new ArrayList<>();
        int temp_size=patInfoData.size();
        temp_patInfoData.clear();

        for (int i=0; i<temp_size;i++){
            //System.out.println("i= "+i);
            //System.out.println("Temp DName: "+temp_drugInfoData.get(i).getDrugname());

            //set add druginfodata to temp_druginfo array
            temp_patInfoData.add(patInfoData.get(i));

            //add drugname to temp list if it does not match searched for character
            if (!temp_patInfoData.get(i).patname.toLowerCase().contains(textToSearch.toLowerCase())) {
                temp.add(temp_patInfoData.get(i));
            }
        }

        System.out.println("pause");
        for (int i=0; i<patInfoData.size();i++){
            System.out.println("drugname="+patInfoData.get(i).patname.toString());
        }

        for (int j = temp.size()-1; j >= 0; j--) {
            temp_patInfoData.remove(temp.get(j));
        }
        //temp_drugInfoData.removeAll(temp);

        //System.out.println("temp size after removal="+temp_drugInfoData.size());
        /*for (int i=0; i<drugInfoData.size();i++) {
            System.out.println("DrugName "+drugInfoData.get(i).drugname);
        }*/
        System.out.println("pause1");
        for (int i=0; i<patInfoData.size();i++){
            System.out.println("drugname="+patInfoData.get(i).patname.toString());
        }
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }




    private void patientFetch() {
        class fetch_patientinfo extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;

                @Override
                protected void onPreExecute() {
                    System.out.println("got here");

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

                    RequestHandler reqHan = new RequestHandler();
                    String s;

                    s = reqHan.sendGetRequest (ConnVars.URL_FETCH_PATIENTINFO_ROW);

                    return s;
                }
        }
        fetch_patientinfo pinfo = new fetch_patientinfo();
        pinfo.execute();
    }

    private void jsonParse(String json_string) {

        Context context=this;

        int totalCast, count=0;
        String patid, patname,fileid, address, telephone, gender,civil_status;

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
                fileid=jo.getString("fileid");
                patname= jo.getString("patname");
                address = jo.getString("address");
                telephone= jo.getString("telephone");
                gender=jo.getString("gender");
                civil_status = jo.getString("civil_status");

                //try to cast string into int


                    //add this data as DruginfoFields to ArrayList
                   patInfoData.add(new PatientinfoFields(patid, fileid, patname , address, telephone, gender, civil_status));

                //increment count
                count++;
            }

        } catch (JSONException e) {

        }
        //System.out.println(patInfoData.size()+" that was size of pat info data");
        for(int i=0;i<patInfoData.size();i++){
            temp_patInfoData.add(patInfoData.get(i));
           // System.out.println("name="+temp_patInfoData.get(i).patname.toString());
        }

        adapter = new PatientinfoAdapter(context, temp_patInfoData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //System.out.println( temp_drugInfoData.get(position));
                Intent intent = new Intent(SearchAddPatients.this, FetchPatientInfo.class);
                Bundle b = new Bundle();
                b.putString("patid", temp_patInfoData.get(position).patid); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
                finish();
            }
        });

    }

   /* public void NuevoPaciente(View V) {
        /**Need to make dialog_patient_preccription pull data from DB not my made up stuff
         *create layout inflater and subView assign sub view to dialog xml
         * */
         /*  LayoutInflater inflater = LayoutInflater.from(SearchAddPatients.this);
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
                        dialog.dismiss();
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
                    String dob_day_temp1, dob_month_temp1;
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

                        /***This is the code needed for db communication.***/

                       /* patname = edit_name2.getText().toString();
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
                            final String s_dob, final String children, final String height, final String weight, final String allergies, final String medcond ) {
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


    public void selectPatient_accept(View view){
        System.out.println(selectedListItem.getPatid());

        Intent intent = new Intent(getBaseContext(), FetchPatientInfo.class);

        intent.putExtra("patid", selectedListItem.getPatid());
        startActivity(intent);
    }
    public void selectPatient_cancel(View view){
        findPatient_dialog.cancel();
        patinfo.clear();
    }*/

    @Override
    public void onBackPressed() {
        Log.d("onBackPressed","backpPressed");
        Intent go_back = new Intent(this, MainMenu.class);
        startActivity(go_back);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}