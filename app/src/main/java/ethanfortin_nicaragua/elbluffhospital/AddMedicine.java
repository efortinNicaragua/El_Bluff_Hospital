package ethanfortin_nicaragua.elbluffhospital;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class AddMedicine extends AppCompatActivity {

    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        Button newMed = (Button) findViewById(R.id.button2);
        newMed.setOnClickListener(new OnClickListener() {

        public void onClick(View v) {
            LayoutInflater inflater = LayoutInflater.from(AddMedicine.this);
            View subView = inflater.inflate(R.layout.dialog_new_med, null);

            final EditText subEditText1 = (EditText)subView.findViewById(R.id.addName);
            final EditText subEditText2 = (EditText)subView.findViewById(R.id.addId);
            final EditText subEditText3 = (EditText)subView.findViewById(R.id.addQuantity);
            int eFlag = 0;


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            // set title
            alertDialogBuilder.setTitle("Add New Medicine");

            // set Dialog message
            alertDialogBuilder
                    .setView(subView)
                    .setMessage("Enter Drug Name, ID, and quantity")
                    .setPositiveButton("Añadir",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {

                            int eFlag = 0;

                            // Verify drug name
                            String s_drugName = subEditText1.getText().toString();
                            if(s_drugName.equals("")) {
                                eFlag = 1;
                            }
                            else {
                                // TODO Transfer variable to database
                            }

                            // Verify drug id
                            String s_drugId = subEditText2.getText().toString();
                            if(s_drugId.equals("")) {
                                eFlag = 1;
                            }
                            else {
                                // TODO Transfer variable to database
                            }

                            // Verify drug quantity
                            String s_drugQuant = subEditText3.getText().toString();
                            if(s_drugQuant.equals("")) {
                                eFlag = 1;
                            }
                            else {
                                // TODO Transfer variable to database
                            }

                            // If all entries are populated, close window and confirm
                            if(eFlag == 0){
                                int duration = Toast.LENGTH_LONG;
                                Context context = getApplicationContext();
                                String text1 = "Medicina nueva archivada.";
                                Toast toast1 = Toast.makeText(context, text1, duration);
                                toast1.show();
                                //AddMedicine.this.finish();
                            }
                            else{
                                Button clicked = (Button)dialog;
                                //clicked.setEnabled(false);
                            }

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
        }

        });
    }



}
