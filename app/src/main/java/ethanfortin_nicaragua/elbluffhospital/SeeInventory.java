package ethanfortin_nicaragua.elbluffhospital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SeeInventory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_inventory);
    }

    public void searchTotals(View v) {
        Intent searchTots = new Intent(this, SearchTotals.class);
        startActivity(searchTots);
    }

    public void searchShipments(View v) {
        Intent searchShips = new Intent(this, SearchShipments.class);
        startActivity(searchShips);
    }

    public void entireInventory(View v) {
        Intent entInv = new Intent(this, Inventory.class);
        startActivity(entInv);
    }
}
