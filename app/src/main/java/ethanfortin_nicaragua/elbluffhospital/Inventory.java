package ethanfortin_nicaragua.elbluffhospital;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import static android.R.id.list;
//import static ethanfortin_nicaragua.elbluffhospitalan.android.R.id.listView_fetch_druginfo_all;

public class Inventory extends ListActivity{//implements interface_DruginfoAll {
//Create global variables for view

    Context context;
    ListView listView;
    String Drop;
     //fetch_druginfo_all asyncTask  = new fetch_druginfo_all(context);
     ArrayList<Class_FetchAllDrugInfo> druginfo_data = new ArrayList();

    @Override
    public void onCreate(Bundle savedInstanceState) {
       // asyncTask.delegate = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

    ListView listview2= (ListView) findViewById(android.R.id.list);
    }

    public void jumpTo(View v) {
        String link = "http://153.104.62.31//android_connect/fetch_druginfo_all.php";///android_connect
       /*try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            AlertDialog alertDialog;
            alertDialog= new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Connection");
            alertDialog.setMessage("IT worked ");
            alertDialog.show();
        }
        catch (Exception ex){
        AlertDialog alertDialog1;
        alertDialog1= new AlertDialog.Builder(this).create();
        alertDialog1.setTitle("Connection");
        alertDialog1.setMessage("Could not connect ya dingus, bad url ");
        alertDialog1.show();}*/


        fetch_druginfo_all fetch_druginfo_all=new fetch_druginfo_all(this);
         fetch_druginfo_all.execute();

       // asyncTask.execute();


    }

    public void setArrayList( ArrayList<Class_FetchAllDrugInfo> ArrayList) {
        //Drop="";
        //this.Drop = go;
        this.druginfo_data = ArrayList;

        }

    public void loadTo(View v)
    {
       /* int i =123;
        String j="123452";
        String p="Tylenol";
        druginfo_data.add(new Class_FetchAllDrugInfo(j,p,i));
        druginfo_data.add(new Class_FetchAllDrugInfo("12345", "Dugs", 1234))*/
        ArrayAdapter<Class_FetchAllDrugInfo> adapter= new ArrayAdapter_FetchAllDrugInfo(this,druginfo_data);



        for(int crtx=0; crtx<druginfo_data.size();crtx++)
        {
            Class_FetchAllDrugInfo drug1;
            drug1= druginfo_data.get(crtx);
            System.out.println(drug1.getAllData());
        }
        ListView listView=(ListView) findViewById(android.R.id.list);
        listView.setAdapter(adapter);
    }
    /*@Override
    public void processFinish(Class_FetchAllDrugInfo[] output) {


        ArrayAdapter_FetchAllDrugInfo adapter = new ArrayAdapter_FetchAllDrugInfo(context,
                R.layout.row_druginventory_all, output);
        listView= (ListView) listView.findViewById(listView_fetch_druginfo_all);
        listView.setAdapter(adapter);


    }*/
}

