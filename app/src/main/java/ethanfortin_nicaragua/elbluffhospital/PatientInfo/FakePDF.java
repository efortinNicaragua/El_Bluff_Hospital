package ethanfortin_nicaragua.elbluffhospital.PatientInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import ethanfortin_nicaragua.elbluffhospital.R;

public class FakePDF extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_pdf);

    }


    public void onBackPressed() {
        Intent go_back_to_PGI_2 = new Intent(this, FetchVisits.class);
        startActivity(go_back_to_PGI_2);
    }

}
