package ethanfortin_nicaragua.elbluffhospital;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class PDFConfirmationandUpload extends AppCompatActivity {

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_confirmation_and_upload);

        //Create intent to open galley
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        //Start the intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //When an image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                //Get the image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                //Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                //Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.pdfView);
                //Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
            } else {
                Toast.makeText(this, "You haven't picked an Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong.", Toast.LENGTH_LONG).show();
        }

    }

    public void button3(View view) {
        // TODO   This is for the progress bar while the record is being saved to the database. It may be
        // TODO   inoperative but not sure because it isn't linked to anything yet, not sure how it times without file size
        progress = new ProgressDialog(this);
        progress.setMessage("Uploading Record");
        progress.setCancelable(true);
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();

        final int totalProgressTime = 100;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                while (jumpTime < totalProgressTime) {
                    try {
                        sleep(200);
                        jumpTime += 5;
                        progress.setProgress(jumpTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }


}
