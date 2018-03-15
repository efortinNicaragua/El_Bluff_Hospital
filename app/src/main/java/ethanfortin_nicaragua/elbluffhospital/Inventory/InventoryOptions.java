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

    }

    public void searchShipments(View v){
        Intent entInv = new Intent(this, FetchShipments.class);
        startActivity(entInv);
    }
    public void entireInventory(View v) {
        Intent entInv = new Intent(this, Inventory.class);
        startActivity(entInv);
    }

    /*public void specificDrug(View v) {
        Intent specDrug = new Intent(this, FetchSpecificDrug.class);
        startActivity(specDrug);
    }*/
}
