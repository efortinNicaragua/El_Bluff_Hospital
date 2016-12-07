package ethanfortin_nicaragua.elbluffhospital;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewVisitHistory extends AppCompatActivity {

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_visit_history);

        FloatingActionButton addVisit = (FloatingActionButton) findViewById(R.id.fab_add);


        addVisit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(NewVisitHistory.this);
                final View subView = inflater.inflate(R.layout.dialog_add_visit, null);

                final EditText entryDate = (EditText)subView.findViewById(R.id.ddate);
                final EditText entryReason = (EditText)subView.findViewById(R.id.dReason);
                final EditText entryDoctor = (EditText)subView.findViewById(R.id.dDoctor);
                final EditText entryRx = (EditText)subView.findViewById(R.id.dRx);
                final EditText entryPDF = (EditText)subView.findViewById(R.id.dPDF);



                // Get Current Date
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date date = new Date();
                entryDate.setText(dateFormat.format(date));

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Anadir la visita nueva");

                // set Dialog message
                alertDialogBuilder
                        .setView(subView)
                        .setMessage("Ingrese la informacion de la visita")
                        .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                //Checking to see if edit text's are filled
                                //int eFlag = 0;

                                // Verify reason
                                String  s_razon = entryReason.getText().toString();
                                //if(s_razon.matches("")) {
                                    //eFlag = 1;
                                //}

                               // // Verify doctor
                                String s_doc = entryDoctor.getText().toString();
                                //if(s_doc.matches("")) {
                                   // eFlag = 1;
                               // }

                                if(TextUtils.isEmpty(s_razon)) {
                                    entryReason.setError("Por Favor, llena toda la informacion.");
                                    int duration = Toast.LENGTH_LONG;
                                    Context context = getApplicationContext();
                                    String text1 = "Por Favor, llena toda la informacion.";
                                    Toast toast1 = Toast.makeText(context, text1, duration);
                                    toast1.show();
                                }

                                if(TextUtils.isEmpty(s_doc)) {
                                    entryDoctor.setError("Por Favor, llena toda la informacion.");
                                    int duration = Toast.LENGTH_LONG;
                                    Context context = getApplicationContext();
                                    String text1 = "Por Favor, llena toda la informacion.";
                                    Toast toast1 = Toast.makeText(context, text1, duration);
                                    toast1.show();
                                }

                                //Set confirmation toast
                                int duration = Toast.LENGTH_LONG;
                                Context context = getApplicationContext();
                                String text1 = "La visita se ha guardado.";
                                Toast toast1 = Toast.makeText(context, text1, duration);
                                toast1.show();
                            }

                            })

                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                /*if(eFlag == 0) {

                }
                else {
                    // Creation of Toast warning that is displayed if fields are blank
                    Context apContext1 = getApplicationContext();
                    CharSequence text = "Por Favor, llena toda la informacion.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(apContext1, text, duration);

                    toast.show();
                }
*/

            }

        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pat_gen_info:
                startActivity(new Intent(this, NewPatientGenInfo.class));
                return true;
            case R.id.pat_history:
                startActivity(new Intent(this, NewVisitHistory.class));
                return true;
            case R.id.pat_prescription:
                startActivity(new Intent(this, NewPatientPrescription.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        Intent go_back_to_PGI_2 = new Intent(this, NewPatientGenInfo.class);
        startActivity(go_back_to_PGI_2);
    }

//        public void addNewVisit(View v){
//            Intent i = new Intent(this, AddNewVisit.class);
//            startActivity(i);
//        }


}


