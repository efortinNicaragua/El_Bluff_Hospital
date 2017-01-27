package ethanfortin_nicaragua.elbluffhospital;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FetchSpecificDrug extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_specific_drug);

        final Button btFind = (Button) findViewById(R.id.bFind);
        btFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText drugName = (EditText) findViewById(R.id.etDrugName);
                final EditText drugId = (EditText) findViewById(R.id.etDrugID);

                TextView viewName = (TextView) findViewById(R.id.resDrug);
                TextView viewQuant = (TextView) findViewById(R.id.resQuant);

                String sName = drugName.getText().toString();
                String sId = drugId.getText().toString();

                int resQuant;

                viewName.setText(sName);
                switch (sName) {
                    case "Tylenol":
                        resQuant = 500;
                        break;

                    case "Nyquil":
                        resQuant = 350;
                        break;

                    case "Ibuprofin":
                        resQuant = 1005;
                        break;

                    default:
                        resQuant = 0;
                        break;
                }

                viewQuant.setText(String.valueOf(resQuant));

            }
        });
    }
}
