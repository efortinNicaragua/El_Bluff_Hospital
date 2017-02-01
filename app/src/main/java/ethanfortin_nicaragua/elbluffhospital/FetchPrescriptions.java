package ethanfortin_nicaragua.elbluffhospital;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FetchPrescriptions extends AppCompatActivity {

    HashMap<String, List<String>> HashMap_hashmap;
    List<String> HastMap_list;
    ExpandableListView Exp_list;
    Adapter_ExpandableList adapter;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_prescriptions);

        Exp_list = (ExpandableListView) findViewById(R.id.exp_list);
        HashMap_hashmap = DataProvider.getInfo();
        HastMap_list = new ArrayList<String>(HashMap_hashmap.keySet());
        adapter = new Adapter_ExpandableList(this, HashMap_hashmap, HastMap_list);
        Exp_list.setAdapter(adapter);


        final FloatingActionButton addVisit = (FloatingActionButton) findViewById(R.id.fab_add);

        addVisit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(FetchPrescriptions.this);
                final View subView = inflater.inflate(R.layout.dialog_new_prescription, null);

                // final EditText entryDate = (EditText) subView.findViewById(R.id.);
                final EditText entryReason = (EditText) subView.findViewById(R.id.etReason);
                final EditText entryDoctor = (EditText) subView.findViewById(R.id.etDoctor);
                final EditText entryDir = (EditText) subView.findViewById(R.id.etDirections);


                // Get Current Date
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date date = new Date();
                // entryDate.setText(dateFormat.format(date));

                final AlertDialog alertDialogBuilder = new AlertDialog.Builder(context)
                        .setTitle("Anadir la prescripción nueva")
                        .setView(subView)
                        .setMessage("Ingrese la informacion de la prescripción")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Verify reason
                                String s_razon = entryReason.getText().toString();
                                if (TextUtils.isEmpty(s_razon)) {
                                    //entryReason.setError("Por Favor, llena toda la informacion.");
                                    //int duration = Toast.LENGTH_LONG;
                                    //Context context = getApplicationContext();
                                    String text1 = "Por Favor, llena toda la información.";
                                    Toast toast1 = Toast.makeText(getApplicationContext(), text1, Toast.LENGTH_LONG);
                                    toast1.show();
                                }


                                // Verify doctor
                                String s_doc = entryDoctor.getText().toString();
                                if (TextUtils.isEmpty(s_doc)) {
                                    //entryDoctor.setError("Por Favor, llena toda la informacion.");
                                    // int duration = Toast.LENGTH_LONG;
                                    // Context context = getApplicationContext();
                                    String text1 = "Por Favor, llena toda la información.";
                                    Toast toast1 = Toast.makeText(getApplicationContext(), text1, Toast.LENGTH_LONG);
                                    toast1.show();
                                }

                                // Verify Directions
                                String s_dir = entryDir.getText().toString();
                                if (TextUtils.isEmpty(s_doc)) {
                                    //entryDoctor.setError("Por Favor, llena toda la informacion.");
                                    // int duration = Toast.LENGTH_LONG;
                                    // Context context = getApplicationContext();
                                    String text1 = "Por Favor, llena toda la información.";
                                    Toast toast1 = Toast.makeText(getApplicationContext(), text1, Toast.LENGTH_LONG);
                                    toast1.show();
                                }

                                //Set confirmation toast

                                if(entryReason.getText().toString().trim().length() > 0
                                        && entryDoctor.getText().toString().trim().length() > 0
                                        && entryDir.getText().toString().trim().length() > 0) {
                                    Toast toast2 = Toast.makeText(getApplicationContext(), "La prescripción se ha guardado.", Toast.LENGTH_LONG);
                                    toast2.show();
                                }
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

    public static class DataProvider {
        public static HashMap<String, List<String>> getInfo()
        {
            HashMap<String, List<String>> rxHeaders = new HashMap<String, List<String>>();

            List<String> rx1 = new ArrayList<String>();
            rx1.add("Médico:   Maynor");
            rx1.add("Droga:   Tylenol");
            rx1.add("Cantidad:   24");
            rx1.add("Duración:   Dos semanas");
            rx1.add("Razón:   Resfriado");
            rx1.add("Direcciónes:   Haga una píldora cada mañana");
            rxHeaders.put("2017-03-02 :: Tylenol",rx1);

            List<String> rx2 = new ArrayList<String>();
            rx2.add("Médico:   Ethan");
            rx2.add("Droga:   Nyquil");
            rx2.add("Cantidad:   14");
            rx2.add("Duración:   Una semana");
            rx2.add("Razón:   Resfriado");
            rx2.add("Direcciónes:   Haga dos píldoras cada mañana");
            rxHeaders.put("2017-06-04 :: Nyquil",rx2);

            List<String> rx3 = new ArrayList<String>();
            rx3.add("Médico:   Ethan");
            rx3.add("Droga:   Ibuprofin");
            rx3.add("Cantidad:   7");
            rx3.add("Duración:   Una semana");
            rx3.add("Razón:   dolor de cabeza");
            rx3.add("Direcciónes:   Haga una píldora cada noche");
            rxHeaders.put("2017-12-13 :: Ibuprofin",rx3);

            List<String> rx4 = new ArrayList<String>();
            rx4.add("Médico:   Maddie");
            rx4.add("Droga:   Advil");
            rx4.add("Cantidad:   7");
            rx4.add("Duración:   Una semana");
            rx4.add("Razón:   dolor de cabeza");
            rx4.add("Direcciónes:   Haga una píldora cada noche");
            rxHeaders.put("2017-08-23 :: Advil",rx4);

            return rxHeaders;
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
                startActivity(new Intent(this, FetchPatientInfo.class));
                return true;
            case R.id.pat_history:
                startActivity(new Intent(this, FetchVisits.class));
                return true;
            case R.id.pat_prescription:
                startActivity(new Intent(this, FetchPrescriptions.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed(){
        Intent go_back_to_PGI = new Intent(this, FetchPatientInfo.class);
        startActivity(go_back_to_PGI);
    }
}


