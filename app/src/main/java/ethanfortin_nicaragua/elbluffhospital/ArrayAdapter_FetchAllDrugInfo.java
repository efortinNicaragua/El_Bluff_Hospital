package ethanfortin_nicaragua.elbluffhospital;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wildcat on 12/3/2016.
 */

public class ArrayAdapter_FetchAllDrugInfo extends ArrayAdapter<Class_FetchAllDrugInfo>{
    public ArrayAdapter_FetchAllDrugInfo(Context context, ArrayList<Class_FetchAllDrugInfo> drugdata){
        super(context,0,drugdata);
    }
    @Override
    public View getView(int position,View convertView, ViewGroup parent){
        Class_FetchAllDrugInfo single_druginfo=getItem(position);

        if(convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.row_druginventory_all, parent, false);
        }
        TextView drugid=(TextView)convertView.findViewById(R.id.drugid);
        TextView drugname=(TextView)convertView.findViewById(R.id.drugname);
        TextView drugtotal=(TextView)convertView.findViewById(R.id.drugtotal);

        drugid.setText(single_druginfo.drugid);
        drugname.setText(single_druginfo.drugname);
        drugtotal.setText(Integer.toString(single_druginfo.drugtotal));

        return convertView;

    }

}
