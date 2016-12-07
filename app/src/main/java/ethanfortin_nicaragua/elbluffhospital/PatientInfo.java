package ethanfortin_nicaragua.elbluffhospital;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class PatientInfo extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void buscar (View v) {
        EditText name_EditText = (EditText) findViewById(R.id.edit_name);
        EditText id_EditText = (EditText) findViewById(R.id.edit_ID);
        String sName = name_EditText.getText().toString();
        String sID = id_EditText.getText().toString();
        String[] Nombres =
                {
                        "Pablo Sanchez 9/8/1991 M 0119235",
                        "Mario Lugio   1/2/1874 M 1239450",
                        "Dr. Pali      4/5/1990 M 12345069"
                };


        if ((sName.matches("")) & (sID.matches(""))) {
            Toast.makeText(this, "Necesitas Entrar un ID o un Nombre", Toast.LENGTH_SHORT).show();
        } else {
            /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Elige el paciente");
            DialogInterface.OnClickListener patient_dialog = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            };
           builder.setItems(Nombres.toString(), );
            builder.show();*/

            AlertDialog.Builder builderSingle = new AlertDialog.Builder(PatientInfo.this);
            builderSingle.setTitle("Elige el paciente");
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    PatientInfo.this,
                    android.R.layout.select_dialog_singlechoice);
            arrayAdapter.add("Pablo Himenz 8/7/1990 M 194356");
            arrayAdapter.add("Pablo Himnez 6/19/2004 M 23345");
            arrayAdapter.add("Pablo Shancez 6/1/2010  M 245674");
            arrayAdapter.add("Pablo Tarin 12/3/2015   M 264510");

            builderSingle.setNegativeButton(
                    "cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builderSingle.setAdapter(
                    arrayAdapter,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String strName = arrayAdapter.getItem(which);
                            AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                    PatientInfo.this);
                            builderInner.setMessage(strName);
                            builderInner.setTitle("Eligiste el paciente");
                            builderInner.setNegativeButton(
                                    "cancel",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            builderInner.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(

                                                DialogInterface dialog,
                                                int which) {
                                            dialog.dismiss();
                                            Intent intent_patientData = new Intent(PatientInfo.this, NewPatientGenInfo.class);

                                            startActivity(intent_patientData);
                                        }
                                    });
                            builderInner.show();
                        }
                    });
            builderSingle.show();
        }


}

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("PatientInfo Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    public void NuevoPaciente (View V)
    {
        //Need to make dialog_patient_preccription pull data from DB not my made up stuff
        //create layout inflater and subView assign sub view to dialog xml
        LayoutInflater inflater = LayoutInflater.from(PatientInfo.this);
        View subView = inflater.inflate(R.layout.dialog_new_pacient, null);
        //Build dialog set it to subview
        AlertDialog.Builder builderSingle1 = new AlertDialog.Builder(this);
        builderSingle1.setView(subView);
        //Set dialog        title
        builderSingle1.setTitle("Entrar Nueva Paciente");
        final EditText edit_name2 = (EditText) subView.findViewById(R.id.newdialog_edit_name);
        final EditText edit_ID2 = (EditText) subView.findViewById(R.id.newdialog_edit_ID);
        final EditText edit_adress2 = (EditText) subView.findViewById(R.id.newdialog_edit_adress);
        final EditText edit_telephone2 = (EditText) subView.findViewById(R.id.newdialog_edit_Telephone);
        final EditText edit_gender2 = (EditText) subView.findViewById(R.id.newdialog_edit_gender);
        final EditText edit_married2 = (EditText) subView.findViewById(R.id.newdialog_edit_Married);
        final EditText edit_birthday2 = (EditText) subView.findViewById(R.id.newdialog_edit_Birthdate);
        final EditText edit_children2 = (EditText) subView.findViewById(R.id.newdialog_edit_Children);
        final EditText edit_height2 = (EditText) subView.findViewById(R.id.newdialog_edit_Height);
        final EditText edit_weight2 = (EditText) subView.findViewById(R.id.newdialog_edit_Weight);
        final EditText edit_allergies2 = (EditText) subView.findViewById(R.id.newdialog_edit_Allergies);
        final EditText edit_medicalConditions2 = (EditText) subView.findViewById(R.id.newdialog_edit_MedicalConditions);
        builderSingle1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builderSingle1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Push to DB including ones not above but in Dialog
                        dialog.dismiss();
                    }
                });
        builderSingle1.show();
    }
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
