package ethanfortin_nicaragua.elbluffhospital;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import ethanfortin_nicaragua.elbluffhospital.ArrayAdapters.VisitAdapter;
import ethanfortin_nicaragua.elbluffhospital.DataClasses.VisitFields;

public class UploadFile extends AppCompatActivity {

    // List of VisitField objects fetched from database
    ArrayList<VisitFields> visitList = new ArrayList<>();

    // VisitFields object that was selected - selectedListItem.visitid is search param
    VisitFields selectedListItem = null;

    ListView listView;
    Dialog visitDialog;
    AlertDialog adMessage;
    Context context = this;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private ImageView ivImage;
    private DatePicker datePicker;
    private String stDate, userChosenTask;
    private TextView tvDate;
    private Button chooseImage, chooseDate, upload, cancel, accept;
    private Uri filePath;
    private boolean gallery;
    private boolean ready = false;

    String patid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);

        Intent i = getIntent();
        patid = i.getStringExtra("patid");

        chooseImage = (Button)findViewById(R.id.image);
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Clicked on button.");
                selectImage();
            }
        });

        chooseDate = (Button)findViewById(R.id.visit);
        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectVisit();
            }
        });

        upload = (Button)findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ready && selectedListItem != null) uploadMultipart(gallery);
            }
        });

        ivImage = (ImageView)findViewById(R.id.ivImage);
        tvDate = (TextView)findViewById(R.id.date_string);
    }


    private void selectVisit() {
        class FetchVisitRecords extends AsyncTask<Void,VisitFields,String> {
            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UploadFile.this, "Buscando...", "Espera, por favor", false, false);
                visitList.clear();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                parseJson(s);
            }

            @Override
            protected String doInBackground(Void... params) {

                RequestHandler requestHandler = new RequestHandler();
                HashMap<String,String> map = new HashMap<>();
                map.put("patid", patid);

                // TODO Replace this url constant with the real one in ConnVars
                String s = requestHandler.sendGetRequestParam(ConnVars.URL_FETCH_PAT_VISIT, map);
                return s;
            }
        }

        FetchVisitRecords fetchVisitRecords = new FetchVisitRecords();
        fetchVisitRecords.execute();
    }

    private void parseJson(String json) {
        Context context = this;
        int visitInt, count = 0;
        String visitdate, reason, visitid;

        JSONObject jsonObject;
        JSONArray jsonArray;
        try {
            jsonObject = new JSONObject(json);
            jsonArray = jsonObject.getJSONArray(ConnVars.TAG_VISITHISTORY);

            while (count < jsonArray.length()) {
                JSONObject jo1 = jsonArray.getJSONObject(count);

                visitid = jo1.getString(ConnVars.TAG_VISITHISTORY_VISITID);
                visitdate = jo1.getString(ConnVars.TAG_VISITHISTORY_VISITDATE);
                reason = jo1.getString(ConnVars.TAG_VISITHISTORY_REASON);

                // casting try-catch
                try {
                    visitInt = Integer.parseInt(visitid);
                    visitList.add(new VisitFields(visitInt, visitdate, reason));
                } catch (NumberFormatException e){
                    System.out.println("Number format exception occurred...");
                }

                count++;

            }

        } catch (JSONException e) {
            System.out.println("JSON Exception occurred...");
        }

        ArrayAdapter<VisitFields> adapter = new VisitAdapter(context, visitList);
        visitDialog = new Dialog(this);
        visitDialog.setTitle("Eliga la evaluación");
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_visit_picker,null,false);
        visitDialog.setContentView(view);

        listView = (ListView) visitDialog.findViewById(R.id.listview);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setSelector(R.drawable.greygradient);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectedListItem = (VisitFields) listView.getItemAtPosition(position);
                System.out.println(selectedListItem);
            }
        });

        listView.setAdapter(adapter);
        visitDialog.setCancelable(true);
        visitDialog.show();
    }

    public void cancelVisitSearch(View view) {
        visitDialog.cancel();
    }

    public void acceptVisitSearch(View view) {
        if(selectedListItem != null) {
            tvDate.setText(selectedListItem.visitdate);
            visitDialog.cancel();
        } else {
            // Nothing, an item must be selected.
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Tomar un foto", "Eligir de galería", "Cancelar" };
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadFile.this);
        builder.setTitle("Eligir imagen");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(UploadFile.this);
                if (items[item].equals("Tomar un foto")) {
                    userChosenTask = "Tomar un foto";
                    gallery = false;
                    if(result) cameraIntent();

                } else if (items[item].equals("Eligir de galería")) {
                    userChosenTask = "Eligir de galería";
                    gallery = true;
                    if(result) galleryIntent();

                } else if (items[item].equals("Calcelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if(photoFile != null)
            {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, REQUEST_CAMERA);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Image_" + timeStamp + "_";

        File storageDirectory = getExternalFilesDir("");
        File image = File.createTempFile(imageFileName, ".jpg",storageDirectory);
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChosenTask.equals("Tomar un foto"))
                        cameraIntent();
                    else if(userChosenTask.equals("Eligir de galería"))
                        galleryIntent();
                } else {
                    System.out.println("DENIED");
                }
                break;
        }
    }

    private void onCaptureImageResult(Intent data) {

        File myPath = getExternalFilesDir(null);

        filePath = Uri.parse("file://" + myPath.listFiles()[myPath.listFiles().length - 1].getAbsolutePath());
        System.out.println("FilePath = " + filePath);
        Bitmap bm = null;
        try {
            bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ivImage.setImageBitmap(bm);
        ready = true;
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            filePath = data.getData();
            System.out.println("FilePath = " + filePath);
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ivImage.setImageBitmap(bm);
        ready = true;
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    public void uploadMultipart(boolean gallery) {

        //getting the actual path of the image
        String path;
        if(gallery) {
            path = getPath(filePath);
            System.out.println("Path variable set from gallery.");
        } else {
            path = filePath.getPath();
            System.out.println("Path variable set from gallery.");
        }

        System.out.println("filePath = " + filePath);
        System.out.println("path = " + path);
        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, ConnVars.URL_UPLOAD)
                    .addFileToUpload(path, "image") // Adding file
                    .addParameter("visitid", String.valueOf(selectedListItem.visitid))
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); // Starting the upload

            Toast.makeText(this, "Image successfully uploaded.", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
