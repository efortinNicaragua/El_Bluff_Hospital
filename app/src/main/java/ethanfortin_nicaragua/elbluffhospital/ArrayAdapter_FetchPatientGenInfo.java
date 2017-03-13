package ethanfortin_nicaragua.elbluffhospital;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wildcat on 1/26/2017.
 */

public class ArrayAdapter_FetchPatientGenInfo extends ArrayAdapter<Class_FetchPatientGenInfo> {
    public ArrayAdapter_FetchPatientGenInfo(Context context, ArrayList<Class_FetchPatientGenInfo> patgeninfodata){
        super(context,0,patgeninfodata);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Class_FetchPatientGenInfo single_patgeninfo=getItem(position);

        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.row_patgeninfo_all, parent, false);
        }

        //Needs to be updated for all data from pat info class but for now row_patinfo_all only has 2 text views


        TextView patid=(TextView)convertView.findViewById(R.id.patname);
        TextView patname=(TextView)convertView.findViewById(R.id.patid);

        //String test1="DID IT WORK";
        //String test2="YA BRO IT DID";
        //patid.setText(test1);
        //patname.setText(test2);
        patid.setText(single_patgeninfo.patid);
        patname.setText(single_patgeninfo.patname);

        return convertView;

    }

}
