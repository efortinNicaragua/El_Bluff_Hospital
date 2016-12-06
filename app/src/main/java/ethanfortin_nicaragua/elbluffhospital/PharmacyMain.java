package ethanfortin_nicaragua.elbluffhospital;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class PharmacyMain extends AppCompatActivity {

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phamarcy_main);

        ImageButton newShip = (ImageButton) findViewById(R.id.newShipment);
        newShip.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(PharmacyMain.this);
                View subView = inflater.inflate(R.layout.dialog_new_med, null);

                final EditText subEditText1 = (EditText)subView.findViewById(R.id.addName);
                final EditText subEditText2 = (EditText)subView.findViewById(R.id.addId);
                final EditText subEditText3 = (EditText)subView.findViewById(R.id.addQuantity);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Añadir nuevo envío");

                // set Dialog message
                alertDialogBuilder
                        .setView(subView)
                        .setMessage("Añadir la medicina nueva del envío al inventario.")
                        .setPositiveButton("Guardar",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                int nameFlag = 0; int idFlag = 0; int countFlag = 0;

                                // Verify drug name
                                String s_drugName = subEditText1.getText().toString();
                                if(s_drugName.equals("")) {
                                    nameFlag = 1;
                                }
                                else {
                                    // TODO Transfer variable to database
                                }

                                // Verify drug id
                                String s_drugId = subEditText2.getText().toString();
                                if(s_drugId.equals("")) {
                                    idFlag = 1;
                                }
                                else {
                                    // TODO Transfer variable to database
                                }

                                // Verify drug quantity
                                String s_drugQuant = subEditText3.getText().toString();
                                if(s_drugQuant.equals("")) {
                                    countFlag = 1;
                                }
                                else {
                                    // TODO Transfer variable to database
                                }

                                // If all entries are populated, close window and confirm
                                if(nameFlag + idFlag + countFlag == 0){
                                    int duration = Toast.LENGTH_LONG;
                                    Context context = getApplicationContext();
                                    String text1 = "Medicina nueva archivada.";
                                    Toast toast1 = Toast.makeText(context, text1, duration);
                                    toast1.show();
                                }

                                // TODO ^ More error handling here ^

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

    public void seeInventory(View v) {
        Intent seeInv = new Intent(this, SeeInventory.class);
        startActivity(seeInv);
    }

}


