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
import android.text.TextUtils;
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

    private EditText et_drugName;
    private EditText et_drugId;
    private EditText et_drugQuant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phamarcy_main);

        /*ImageButton newShip = (ImageButton) findViewById(R.id.newShipment);
        newShip.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(PharmacyMain.this);
                View subView = inflater.inflate(R.layout.dialog_new_med, null);

                et_drugName = (EditText)subView.findViewById(R.id.addName);
                et_drugId = (EditText)subView.findViewById(R.id.addId);
                et_drugQuant = (EditText)subView.findViewById(R.id.addQuantity);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Crear nuevo envío");

                // set Dialog message
                alertDialogBuilder
                        .setView(subView)
                        .setCancelable(false)
                        .setMessage("Añadir la medicina nueva del envío al inventario.")
                        .setPositiveButton("Guardar",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                boolean cancel = false;
                                View focusView = null;

                                // Verify drug name
                                String s_drugName = et_drugName.getText().toString();
                                if(TextUtils.isEmpty(s_drugName)) {
                                    et_drugName.setError("Not Valid");
                                    focusView = et_drugName;
                                    cancel = true;
                                    System.out.println("%%%%%%%%%%%%%%%%%%%%11111111%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                                }

                                // Verify drug id
                                String s_drugId = et_drugId.getText().toString();
                                if(TextUtils.isEmpty(s_drugId)) {
                                    et_drugId.setError("Not Valid");
                                    focusView = et_drugId;
                                    cancel = true;
                                    System.out.println("%%%%%%%%%%%%%%%%%%%%11111111%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                                }

                                // Verify drug quantity
                                String s_drugQuant = et_drugQuant.getText().toString();
                                if(TextUtils.isEmpty(s_drugQuant)) {
                                    et_drugQuant.setError("Not Valid");
                                    focusView = et_drugQuant;
                                    cancel = true;
                                    System.out.println("%%%%%%%%%%%%%%%%%%%%11111111%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                                }

                                if(cancel) {
                                    focusView.requestFocus();
                                }
                                else {
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

        });*/
    }

    public void createShipmentPage(View v) {
        Intent i = new Intent(this, CreateNewShipment.class);
        startActivity(i);
    }

    public void seeInventory(View v) {
        Intent seeInv = new Intent(this, SeeInventory.class);
        startActivity(seeInv);
    }

}


