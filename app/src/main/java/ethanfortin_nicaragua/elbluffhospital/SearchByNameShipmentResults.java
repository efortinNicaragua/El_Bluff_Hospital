package ethanfortin_nicaragua.elbluffhospital;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SearchByNameShipmentResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_name_shipment_results);

        Bundle bundle = getIntent().getExtras();
        String retName = bundle.getString("DRUG_NAME");
        String retId = bundle.getString("DRUG_ID");
        String retDate = bundle.getString("DATE");
        int toggle = bundle.getInt("TOGGLE");

        TextView dateSearch = (TextView)findViewById(R.id.retrieveDate);
        TextView drugSearch = (TextView)findViewById(R.id.retrieveName);

        if(toggle == 1) {
            dateSearch.setText(retDate);
        }
        drugSearch.setText(retName);

    }
}
