package ethanfortin_nicaragua.elbluffhospital;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Wildcat on 12/8/2016.
 */

public class ArrayAdapter_add_patient_info_row extends ArrayAdapter<Class_add_patient_info_row> {
    public ArrayAdapter_add_patient_info_row(Context context, ArrayList<Class_add_patient_info_row> patientdata){
        super(context,0,patientdata);
    }


@Override
public View getView(int position, View convertView, ViewGroup parent){
    Class_add_patient_info_row single_patient=getItem(position);

    if(convertView==null){
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_patient, parent, false);
    }

    //The newdialog does not actually indicate a new dialog, it's the ID tag of the edit text for the dialog_new_patient screen//

    EditText patname=(EditText) convertView.findViewById(R.id.newdialog_edit_ID);
    EditText patid=(EditText) convertView.findViewById(R.id.newdialog_edit_name);
    EditText patadress=(EditText) convertView.findViewById(R.id.newdialog_edit_adress);
    EditText pattel=(EditText) convertView.findViewById(R.id.newdialog_edit_Telephone);
    EditText gender=(EditText) convertView.findViewById(R.id.newdialog_edit_gender);
    EditText married=(EditText) convertView.findViewById(R.id.newdialog_edit_Married);
    EditText birthday=(EditText) convertView.findViewById(R.id.newdialog_edit_Birthdate);
    EditText children=(EditText) convertView.findViewById(R.id.newdialog_edit_Children);
    EditText height =(EditText) convertView.findViewById(R.id.newdialog_edit_Height);
    EditText weight=(EditText) convertView.findViewById(R.id.newdialog_edit_Weight);
    EditText allergies=(EditText) convertView.findViewById(R.id.newdialog_edit_Allergies);
    EditText medcond=(EditText) convertView.findViewById(R.id.newdialog_edit_MedicalConditions);

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    patname.setText(single_patient.patname);
    patid.setText(single_patient.patid);
    patadress.setText(single_patient.adress);
    pattel.setText(single_patient.tel);
    gender.setText(single_patient.gender);
    married.setText(single_patient.marstat);
    birthday.setText(dateFormat.format(single_patient.dob));
    children.setText(Integer.toString(single_patient.children));
    height.setText(Integer.toString(single_patient.height));
    weight.setText(Integer.toString(single_patient.weight));
    allergies.setText(single_patient.allergies);
    medcond.setText(single_patient.medcond);

    return convertView;

}

}
