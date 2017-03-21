package ethanfortin_nicaragua.elbluffhospital.ArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ethanfortin_nicaragua.elbluffhospital.DataClasses.PrescriptionFields;
import ethanfortin_nicaragua.elbluffhospital.R;

public class PrescriptionAdapter extends ArrayAdapter<PrescriptionFields> {

    public PrescriptionAdapter(Context context, ArrayList<PrescriptionFields> f_patRXdata) {
        super(context,0,f_patRXdata);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PrescriptionFields single_patRXinfo = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_fetchprescriptions, parent, false);
        }

        /**ML: These take the items in the row xml file (R.id.**) and store them as a Textview variable to be used**/

        /**ML: Need to add these for every field**/
        TextView Rxid = (TextView) convertView.findViewById(R.id.RXID);
        TextView Drugid = (TextView)convertView.findViewById(R.id.DRUGID);
        TextView Transdate = (TextView)convertView.findViewById(R.id.TRANSDATE);
        TextView Quantity = (TextView)convertView.findViewById(R.id.QUANTITY);
        TextView Patid = (TextView)convertView.findViewById(R.id.PATID);
        TextView Directions = (TextView)convertView.findViewById(R.id.DIRECTIONS);
        TextView Duration = (TextView)convertView.findViewById(R.id.DURATION);
        TextView Doctor = (TextView)convertView.findViewById(R.id.DOCTOR);
        TextView Symptoms = (TextView)convertView.findViewById(R.id.SYMPTOMS);
        //TextView Drugname = (TextView)convertView.findViewById(R.id.DRUGNAME);

        /**ML: This takes the textview variable from above (seen in the row xml file) and sets the class variable to it**/
        Rxid.setText(single_patRXinfo.C_rxid);
        Drugid.setText(single_patRXinfo.C_drugid);
        Patid.setText(single_patRXinfo.C_patid);
        Duration.setText(single_patRXinfo.C_duration);
        Transdate.setText(single_patRXinfo.C_transdate);
        Quantity.setText(Integer.toString(single_patRXinfo.C_quantity));
       // Drugname.setText(single_patRXinfo.C_drugname);
        Directions.setText(single_patRXinfo.C_directions);
        Doctor.setText(single_patRXinfo.C_doctor);
        Symptoms.setText(single_patRXinfo.C_symptoms);

        return convertView;

    }

}
