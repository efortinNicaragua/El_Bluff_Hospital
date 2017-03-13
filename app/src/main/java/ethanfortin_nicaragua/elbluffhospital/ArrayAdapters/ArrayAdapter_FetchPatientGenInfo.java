package ethanfortin_nicaragua.elbluffhospital.ArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ethanfortin_nicaragua.elbluffhospital.DataClasses.Class_FetchPatientGenInfo;
import ethanfortin_nicaragua.elbluffhospital.R;

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
        TextView category=(TextView)convertView.findViewById(R.id.category);
        TextView information=(TextView)convertView.findViewById(R.id.information);

        category.setText(single_patgeninfo.category);
        information.setText(single_patgeninfo.information);

        return convertView;

    }

}
