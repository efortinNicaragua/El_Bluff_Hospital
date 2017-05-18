package ethanfortin_nicaragua.elbluffhospital.ArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ethanfortin_nicaragua.elbluffhospital.DataClasses.VisitFields;
import ethanfortin_nicaragua.elbluffhospital.R;

/**
 * Created by chris on 5/17/17.
 */

public class VisitAdapter extends ArrayAdapter<VisitFields> {

    public VisitAdapter(Context context, ArrayList<VisitFields> visitList) {
        super(context,0,visitList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        VisitFields visitFields = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_fetch_visits, parent, false);
        }

        TextView tvVisitdate = (TextView) convertView.findViewById(R.id.visitdate);
        TextView tvReason = (TextView) convertView.findViewById(R.id.reason);
        String shortReason;
        if(visitFields.reason.length() > 17) {
            shortReason = visitFields.reason.substring(0,15) + "...";
            tvReason.setText(shortReason);
            tvVisitdate.setText(visitFields.visitdate);
        } else {
            tvReason.setText(visitFields.reason);
            tvVisitdate.setText(visitFields.visitdate);
        }





        return convertView;
    }
}
