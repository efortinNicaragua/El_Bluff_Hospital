package ethanfortin_nicaragua.elbluffhospital;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.SortedMap;

public class FetchShipments extends AppCompatActivity {

    ListView listView;
    ArrayList<Class_Fetch_shipment_rows> shipment_data = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_shipments);

        String str1 = getIntent().getStringExtra("shipdate");
        String str2 = getIntent().getStringExtra("drugid");
        String str3 = getIntent().getStringExtra("drugname");

        System.out.println("Date String: " + str1);

        listView = (ListView) findViewById(android.R.id.list);

        SimpleDateFormat sdf = new SimpleDateFormat("yyy");
        try {
            Date date = sdf.parse(str1);

            shipment_data.add(new Class_Fetch_shipment_rows(date, "Example ID","Example Name",9999));
            shipment_data.add(new Class_Fetch_shipment_rows(date, "123456","Tylenol",534));
            shipment_data.add(new Class_Fetch_shipment_rows(date, "875943","Nyquil",888));
            shipment_data.add(new Class_Fetch_shipment_rows(date, "232323","Dayquil",125));

        } catch (ParseException p) {
            System.out.println("Parse error.");
        }

        ArrayAdapter<Class_Fetch_shipment_rows> adapter = new ArrayAdapter_Fetch_shipment_rows(this, shipment_data);

        //set list view to listview in the xml file
        ListView listView=(ListView) findViewById(android.R.id.list);
        //turn on list view
        listView.setAdapter(adapter);
        // Dummy logic below for Scarleth.apk
        // TODO - add logic to contact and search shipment table

    }
}