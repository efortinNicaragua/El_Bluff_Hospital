package ethanfortin_nicaragua.elbluffhospital;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                View subView = inflater.inflate(R.layout.dialog_search_inv, null);

                final EditText subEditText1 = (EditText)subView.findViewById(R.id.searchByName);
                final EditText subEditText2 = (EditText)subView.findViewById(R.id.searchByID);
                //final EditText subEditText3 = (EditText)subView.findViewById(R.id.calendarView);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Busca por medicamentos O por la fecha:");

                // set Dialog message
                alertDialogBuilder
                        .setView(subView)
                        //.setMessage("Enter Drug Name, ID, and quantity")
                        .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                int eFlag = 0;

                                // Verify drug name and/or ID
                                String s_drugName = subEditText1.getText().toString();
                                String s_drugId = subEditText2.getText().toString();

                                // Only proceed if one or the other is chosen
                                if(s_drugId.equals("") && s_drugName.equals("")) {
                                    eFlag = 1;
                                }
                                else {
                                    // TODO Transfer variable to database
                                    // - If both name and Drug are filled out...
                                    //   - Search by one or the other first?
                                    //   - Search by both? I.e. search for Tylenol shipped on Mar 2.
                                    //   - Toast, "Only search by one or the other."
                                }
                                dialog.cancel();
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
