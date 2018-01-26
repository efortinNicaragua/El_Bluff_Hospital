package ethanfortin_nicaragua.elbluffhospital.Inventory;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.DrugNameAdapter;
import ethanfortin_nicaragua.elbluffhospital.ConnVars;
import ethanfortin_nicaragua.elbluffhospital.R;

public class InventoryOptions extends AppCompatActivity {

    final Context context = this;
    //set variable for knowing when switch is on or off
    int on_off=0;

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
                final DatePicker datePicker = (DatePicker) subView.findViewById(R.id.datePicker2);

                //datePicker.setEnabled(false);
                datePicker.setVisibility(View.INVISIBLE);

                final Switch dateEnable = (Switch) subView.findViewById(R.id.dateEnable);



                // Clicking on the switch will toggle whether the user
                // would like to search by date or not.
                dateEnable.setChecked(false);

                dateEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if(on_off==0) {
                            datePicker.setVisibility(View.VISIBLE);
                            datePicker.setEnabled(!datePicker.isEnabled());
                            on_off=1;
                        }
                        else {
                            datePicker.setVisibility(View.INVISIBLE);
                            on_off=0;
                        }

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
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth()+1;
                        int day = datePicker.getDayOfMonth();
                        String s_day;
                        String s_month;

                        if (day<=9){
                            s_day= "0"+day;
                        }
                        else{ s_day= day+"";}
                        if (month<=9){
                            s_month="0"+month;
                        }
                        else{s_month=month+"";}

                         if(dateEnable.isChecked()) {
                        i.putExtra("shipdate",year+"-"+s_month+"-"+s_day);
                    } else {
                             i.putExtra("shipdate", "");
                         }

                        if(entryName.getText().toString().trim().equals("") && dateEnable.isChecked()==false){
                            entryName.setError("Necesitas un droga o una fecha");
                            dateEnable.setError("Necesitas un droga o una fecha");
                        }

                         else{
                                entryName.setError(null,null);
                                dateEnable.setError(null,null);
                                i.putExtra("drugname",s_drugName);
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
