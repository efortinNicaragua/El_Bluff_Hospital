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

        TextView patid = (TextView)convertView.findViewById(R.id.geninfo_patid);
        TextView fileid=(TextView)convertView.findViewById(R.id.geninfo_fileid);
        TextView patname = (TextView)convertView.findViewById(R.id.geninfo_name);
        TextView address = (TextView)convertView.findViewById(R.id.geninfo_address);
        TextView telephone = (TextView)convertView.findViewById(R.id.geninfo_telephone);
        TextView civil_status = (TextView)convertView.findViewById(R.id.geninfo_civil_status);


         patid.setText(single_patgeninfo.patid);
         patname.setText(single_patgeninfo.patname);
         fileid.setText(single_patgeninfo.fileid);
         address.setText("Direccion: "+single_patgeninfo.address);
         telephone.setText("Tele: "+single_patgeninfo.telephone);
         civil_status.setText("Estado Civil: "+single_patgeninfo.civil_status);

        return convertView;

    }

}
