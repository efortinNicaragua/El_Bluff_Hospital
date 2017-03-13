package ethanfortin_nicaragua.elbluffhospital.Inventory;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;

import ethanfortin_nicaragua.elbluffhospital.R;

public class InventoryOptions extends AppCompatActivity {

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_options);

        Button searchInv = (Button) findViewById(R.id.b_searchShipments);
        searchInv.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(InventoryOptions.this);
                final View subView = inflater.inflate(R.layout.dialog_search_shipments, null);

                final EditText entryName = (EditText) subView.findViewById(R.id.searchByName);
                final EditText entryId = (EditText) subView.findViewById(R.id.searchByID);
                final DatePicker datePicker = (DatePicker) subView.findViewById(R.id.datePicker2);

                datePicker.setEnabled(false);

                final Switch dateEnable = (Switch) subView.findViewById(R.id.dateEnable);
                // Clicking on the switch will toggle whether the user
                // would like to search by date or not.
                dateEnable.setChecked(false);

                dateEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        datePicker.setEnabled(!datePicker.isEnabled());
                    }
                });

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Busca por envios por la fecha, la droga, o los dos:");

                // set Dialog message
                alertDialogBuilder
                        .setView(subView)
                        //.setMessage("Enter Drug Name, ID, and quantity")
                        .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

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
                final AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();

                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(subView.getContext(), FetchShipments.class);
                        View focus = null;
                        boolean cancel = false;

                        // Verify drug name and/or ID + Date
                        String s_drugName = entryName.getText().toString();
                        String s_drugId = entryId.getText().toString();

                        if (TextUtils.isEmpty(s_drugName) && TextUtils.isEmpty(s_drugId)
                                && !dateEnable.isChecked()) {
                            cancel = true;
                            System.out.println("LLLLLLLL:" + cancel);
                            entryName.setError("Attencion");
                            focus = entryName;
                        }
                        if (cancel) {
                            focus.requestFocus();
                        } else {
                            int day = datePicker.getDayOfMonth();
                            int month = datePicker.getMonth() + 1;
                            int year = datePicker.getYear();
                            String sdate = year + "-" + month + "-" + day;

                            if(dateEnable.isChecked()) {
                                i.putExtra("shipdate", sdate);
                            } else {
                                i.putExtra("shipdate", "");
                            }
                            i.putExtra("drugid", s_drugId);
                            i.putExtra("drugname", s_drugName);

                            startActivity(i);
                        }
                    }
                });
            }

        });
    }

    public void entireInventory(View v) {
        Intent entInv = new Intent(this, Inventory.class);
        startActivity(entInv);
    }

    public void specificDrug(View v) {
        Intent specDrug = new Intent(this, FetchSpecificDrug.class);
        startActivity(specDrug);
    }
}
