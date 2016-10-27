package ethanfortin_nicaragua.elbluffhospital;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class SeeInventory extends AppCompatActivity {

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_inventory);

        Button searchInv = (Button) findViewById(R.id.b_searchInv);
        searchInv.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(SeeInventory.this);
                final View subView = inflater.inflate(R.layout.dialog_search_inv, null);

                final EditText entryName = (EditText)subView.findViewById(R.id.searchByName);
                final EditText entryId = (EditText)subView.findViewById(R.id.searchByID);
                final DatePicker datePicker = (DatePicker)subView.findViewById(R.id.datePicker2);

                final Button dateEnable = (Button)subView.findViewById(R.id.button4);
                dateEnable.setText("apagado");
                dateEnable.setBackgroundColor(Color.parseColor("#DFDAD5"));

                // Clicking on the "habilitar" button will toggle whether the user
                // would like to search by date or not.

                dateEnable.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        if(dateEnable.getText() == "apagado") {
                            dateEnable.setText("abierto");
                            dateEnable.setBackgroundColor(Color.parseColor("#4286f4"));
                        }
                        else {
                            dateEnable.setText("apagado");
                            dateEnable.setBackgroundColor(Color.parseColor("#DFDAD5"));

                        }
                    }
                });

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Busca por medicamentos O por la fecha:");

                // set Dialog message
                alertDialogBuilder
                        .setView(subView)
                        //.setMessage("Enter Drug Name, ID, and quantity")
                        .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                int toggle = 0;

                                // Verify drug name and/or ID + Date
                                String s_drugName = entryName.getText().toString();
                                String s_drugId = entryId.getText().toString();
                                int day = datePicker.getDayOfMonth();
                                int month = datePicker.getMonth();
                                int year = datePicker.getYear();

                                String sDate = day + "/" + month + "/" + year;


                                // TODO Transfer variable to database
                                // - If both name and Drug are filled out...
                                //   - Search by one or the other first?
                                //   - Search by both? I.e. search for Tylenol shipped on Mar 2.
                                //   - Toast, "Only search by one or the other."

                                // For now, just transferring variable to next screen

                                if(dateEnable.getText() == "abierto") {
                                    toggle = 1;
                                }

                                Intent i = new Intent(subView.getContext(), SearchByNameShipmentResults.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("DRUG_NAME",s_drugName);
                                bundle.putString("DRUG_ID",s_drugId);
                                bundle.putString("DATE",sDate);
                                bundle.putInt("TOGGLE",toggle);

                                i.putExtras(bundle);
                                startActivity(i);


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

    public void entireInventory(View v) {
        Intent entInv = new Intent(this, Inventory.class);
        startActivity(entInv);
    }
}
