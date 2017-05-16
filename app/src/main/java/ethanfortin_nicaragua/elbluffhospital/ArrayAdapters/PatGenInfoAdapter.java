package ethanfortin_nicaragua.elbluffhospital.ArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ethanfortin_nicaragua.elbluffhospital.DataClasses.PatientinfoFields;
import ethanfortin_nicaragua.elbluffhospital.R;

/**
 * Created by wildcat on 1/26/2017!!
 */

public class PatGenInfoAdapter extends ArrayAdapter<PatientinfoFields> {

    public PatGenInfoAdapter(Context context, ArrayList<PatientinfoFields> patgeninfodata){
        super(context,0,patgeninfodata);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        PatientinfoFields single_patgeninfo = getItem(position);

        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.row_patgeninfo_all_mainpage, parent, false);
        }

        //Needs to be updated for all data from pat info class but for now row_patinfo_all only has 2 text views


        TextView patid = (TextView)convertView.findViewById(R.id.patid);
        TextView patname = (TextView)convertView.findViewById(R.id.patname);
        TextView address = (TextView)convertView.findViewById(R.id.address);
        TextView telephone = (TextView)convertView.findViewById(R.id.telephone);
        TextView height = (TextView)convertView.findViewById(R.id.height);
        TextView gender = (TextView)convertView.findViewById(R.id.gender);
        TextView marstat = (TextView)convertView.findViewById(R.id.marstat);
        TextView children = (TextView)convertView.findViewById(R.id.children);
        TextView weight = (TextView)convertView.findViewById(R.id.weight);
        TextView allergies = (TextView)convertView.findViewById(R.id.allergies);
        TextView medcond = (TextView)convertView.findViewById(R.id.medcond);
        TextView dob = (TextView) convertView.findViewById(R.id.dob);


        patid.setText(single_patgeninfo.C_patid);
        patname.setText(single_patgeninfo.C_patname);
        address.setText(single_patgeninfo.C_address);
        telephone.setText(single_patgeninfo.C_telephone);
        height.setText(Integer.toString(single_patgeninfo.C_height));
        gender.setText(single_patgeninfo.C_gender);
        marstat.setText(single_patgeninfo.C_marstat);
        children.setText(Integer.toString(single_patgeninfo.C_children));
        weight.setText(Integer.toString(single_patgeninfo.C_weight));
        allergies.setText(single_patgeninfo.C_allergies);
        medcond.setText(single_patgeninfo.C_medcond);
        dob.setText(single_patgeninfo.C_dob);


        return convertView;

    }

}
