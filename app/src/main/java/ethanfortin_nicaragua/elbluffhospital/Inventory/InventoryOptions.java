package ethanfortin_nicaragua.elbluffhospital.Inventory;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
