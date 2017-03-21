package ethanfortin_nicaragua.elbluffhospital.ArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ethanfortin_nicaragua.elbluffhospital.DataClasses.ShipmentFields;
import ethanfortin_nicaragua.elbluffhospital.R;

/**
 * Created by Wildcat on 1/3/2017.
 */

public class ShipmentAdapter extends ArrayAdapter<ShipmentFields> {
    public ShipmentAdapter(Context context, ArrayList<ShipmentFields> drugdata){
        super(context,0,drugdata);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ShipmentFields shipRow = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_fetch_shipments_rows, parent, false);
        }

        TextView drugid = (TextView)convertView.findViewById(R.id.drugid);
        TextView drugname = (TextView)convertView.findViewById(R.id.drugname);
        TextView shipquant = (TextView)convertView.findViewById(R.id.shipquant);
        TextView shipdate = (TextView)convertView.findViewById(R.id.shipdate) ;

        drugid.setText(shipRow.drugid);
        drugname.setText(shipRow.drugname);
        shipquant.setText(Integer.toString(shipRow.shipquant));
        shipdate.setText(shipRow.shipdate);

        return convertView;

    }

}
