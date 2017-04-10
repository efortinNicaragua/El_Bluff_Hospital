package ethanfortin_nicaragua.elbluffhospital.ArrayAdapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ethanfortin_nicaragua.elbluffhospital.DataClasses.DruginfoFields;
import ethanfortin_nicaragua.elbluffhospital.R;

public class DrugNameAdapter extends ArrayAdapter<String> {

    public DrugNameAdapter(Context context, ArrayList<String> drugNames) {
        super(context,0,drugNames);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String drugName = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_specific_drug, parent, false);
        }

        TextView name = (TextView)convertView.findViewById(R.id.row_name);

        name.setText(drugName);

        return convertView;
    }
}
