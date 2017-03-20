package ethanfortin_nicaragua.elbluffhospital.ArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import ethanfortin_nicaragua.elbluffhospital.DataClasses.Class_FetchPrescriptions;
import ethanfortin_nicaragua.elbluffhospital.R;

import static android.R.attr.duration;
import static ethanfortin_nicaragua.elbluffhospital.R.id.patID;

public class ArrayAdapter_FetchPrescriptions extends ArrayAdapter<Class_FetchPrescriptions> {

    public ArrayAdapter_FetchPrescriptions(Context context, ArrayList<Class_FetchPrescriptions> f_patRXdata) {
        super(context,0,f_patRXdata);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Class_FetchPrescriptions single_patRXinfo = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_fetch_prescriptions, parent, false);
        }

        TextView Drugid = (TextView)convertView.findViewById(R.id.DRUGID);
        TextView Patid = (TextView)convertView.findViewById(R.id.PATID);
        TextView Duration = (TextView)convertView.findViewById(R.id.DURATION);
        TextView Transdate = (TextView)convertView.findViewById(R.id.TRANSDATE);
        TextView Quantity = (TextView)convertView.findViewById(R.id.QUANTITY);
        TextView Drugname = (TextView)convertView.findViewById(R.id.DRUGNAME);

        Drugid.setText(single_patRXinfo.C_drugid);
        Patid.setText(single_patRXinfo.C_patid);
        Duration.setText(single_patRXinfo.C_duration);
        Transdate.setText(single_patRXinfo.C_transdate);
        Quantity.setText(single_patRXinfo.C_quantity);
        Drugname.setText(single_patRXinfo.C_drugname);

        return convertView;

    }

}
