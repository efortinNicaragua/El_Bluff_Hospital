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

public class ShipmentAdapter1 extends ArrayAdapter<ShipmentFields> {

    public ShipmentAdapter1(Context context,  ArrayList<ShipmentFields> drugdata) {
        super(context,0,drugdata);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ShipmentFields shipRow = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_specific_drug, parent, false);
        }

        TextView date = (TextView)convertView.findViewById(R.id.text_Fecha);
        TextView shipquant = (TextView)convertView.findViewById(R.id.shipquant);
        TextView expDate=(TextView)convertView.findViewById(R.id.expD);

        date.setText(shipRow.shipdate);
        shipquant.setText(Integer.toString(shipRow.shipquant));
        expDate.setText(shipRow.expdate);

        return convertView;
    }
}
