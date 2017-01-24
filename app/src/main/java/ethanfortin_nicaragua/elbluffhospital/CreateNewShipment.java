package ethanfortin_nicaragua.elbluffhospital;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateNewShipment extends AppCompatActivity {

    private EditText et_drugName;
    private EditText et_drugId;
    private EditText et_drugQuant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_shipment);


    }

    public void createShipment(View v) {

        et_drugName = (EditText)findViewById(R.id.addName);
        et_drugId = (EditText)findViewById(R.id.addId);
        et_drugQuant = (EditText)findViewById(R.id.addQuantity);

        boolean cancel = false;
        View focusView = null;

        // Verify drug name
        String s_drugName = et_drugName.getText().toString();
        if(TextUtils.isEmpty(s_drugName)) {
            et_drugName.setError("Not Valid");
            focusView = et_drugName;
            cancel = true;
            System.out.println("%%%%%%%%%%%%%%%%%%%%11111111%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        }

        // Verify drug id
        String s_drugId = et_drugId.getText().toString();
        if(TextUtils.isEmpty(s_drugId)) {
            et_drugId.setError("Not Valid");
            focusView = et_drugId;
            cancel = true;
            System.out.println("%%%%%%%%%%%%%%%%%%%%11111111%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        }

        // Verify drug quantity
        String s_drugQuant = et_drugQuant.getText().toString();
        if(TextUtils.isEmpty(s_drugQuant)) {
            et_drugQuant.setError("Not Valid");
            focusView = et_drugQuant;
            cancel = true;
            System.out.println("%%%%%%%%%%%%%%%%%%%%11111111%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        }

        if(cancel) {
            focusView.requestFocus();
        }
        else {
            int duration = Toast.LENGTH_LONG;
            Context context = getApplicationContext();
            String text1 = "Medicina nueva archivada.";
            Toast toast1 = Toast.makeText(context, text1, duration);
            toast1.show();
        }
    }
}
