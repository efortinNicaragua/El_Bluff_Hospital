package ethanfortin_nicaragua.elbluffhospital.ArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ethanfortin_nicaragua.elbluffhospital.DataClasses.Class_FetchDruginfoRow;
import ethanfortin_nicaragua.elbluffhospital.R;

/**
 * Created by wildcat on 12/7/2016.
 */

public class ArrayAdapter_FetchDruginfoRow extends ArrayAdapter<Class_FetchDruginfoRow> {

    public ArrayAdapter_FetchDruginfoRow(Context context, ArrayList<Class_FetchDruginfoRow> drugdata) {
        super(context,0,drugdata);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Class_FetchDruginfoRow single = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_druginventory_all, parent, false);
        }

        TextView drugid = (TextView)convertView.findViewById(R.id.drugid);
        TextView drugname=(TextView)convertView.findViewById(R.id.drugname);
        TextView drugtotal=(TextView)convertView.findViewById(R.id.drugtotal);

        drugid.setText(single.drugid);
        drugname.setText(single.drugname);
        drugtotal.setText(Integer.toString(single.drugtotal));

        return convertView;
    }
}
