package com.example.i851409.photonotes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.io.IOException;
import java.lang.*;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class AddPhotoActivity extends AppCompatActivity {
    EditText ed;
    Button btn1;
    Button btn2;
    boolean flag_capture_button = false;
    File image_file;
    static String image_path;
    String image_name;
    public static final int IMAGE_CAPTURE_IDENTIFIER = 21;
    Bitmap current_image;
    ImageView imgView;
    Intent current_photo_intent;
    File external_picture_directory;
    Uri image_uri;
    String image_caption;
    ContentValues cv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Instantiating the EditText object
        ed = (EditText) findViewById(R.id.editText);

        //Setting an onClick() listener to the CAPTURE image button
        btn1 = (Button) findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set the flag variable to true
                flag_capture_button = true;
                //This should now open the Camera of the Device
                //Capture the Image
                Intent image_capture_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //Check if there is a suitable component to handle the CaptureImage component
                //If a suitable component is found then start the activity to Capture the Image
                if(image_capture_intent.resolveActivity(getPackageManager()) != null){

                    //Get the reference to the External Storage
                    external_picture_directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                    //Generating random numbers between 1 and 10000 to have a collision-free file name
                    Random random_generator = new Random();
                    int random_number = random_generator.nextInt(10000);

                    //Generating the current timestamp to be added to the generated file
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                    //Getting the name of the image file
                    image_name = ed.getText().toString() + random_number + timeStamp;

                    //Creating a new file for the captured image
                    File image_file = new File(external_picture_directory, image_name + ".jpg");

                    //Set up the image URI
                    image_uri = Uri.fromFile(image_file);

                    //Storing the image path in a class variable
                    image_path = image_file.getAbsolutePath();

                    //SAVE the Captured Image
                    image_capture_intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);

                    //Start the Camera Activity
                    startActivityForResult(image_capture_intent, IMAGE_CAPTURE_IDENTIFIER);

                }

            }
        });


        //Setting an onClick() listener to the SAVE image button
        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This should now save the image inside the database
                //First check if the caption field is not null
                if(ed.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "CAPTION cannot be empty", Toast.LENGTH_SHORT).show();
                }

                //Secondly check if the image has been captured or not
                else if(flag_capture_button == false){
                    Toast.makeText(getApplicationContext(), "IMAGE has to be CAPTURED", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Now you can proceed with saving the Image Caption and the Image Path inside the database
                    //At the end you need to navigate back to the Main Activity with the newly added row in ListView

                    //First, you need to have the image caption and the image file path in two strings
                    image_caption = ed.getText().toString();

                    //Now you have to insert the two strings into the table
                    cv = new ContentValues();
                    cv.put(PhotoDatabase.CAPTION, image_caption);
                    cv.put(PhotoDatabase.FILE_PATH, image_path);
                    long res = MainActivity.sqdb.insert(PhotoDatabase.TABLE_NAME, null, cv);

                    //After insertion has been done successfully, return back to the original activity displaying the listView

                    //Starting the MainActivity
                    Intent it = new Intent(AddPhotoActivity.this, MainActivity.class);

                    //The finish() will destroy the current activity and the previous activity from the Activity Stack will be started
                    finish();

                    startActivity(it);
                }
            }
        });


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_uninstall, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        //If the Uninstall button is pressed then the Application should be uninstalled
        if(id == R.id.item1){
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:com.example.i851409.photonotes"));
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    //This method will be invoked when the Camera Application is closed
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Check whether the Camera Activity handles the correct activity
        if(requestCode == IMAGE_CAPTURE_IDENTIFIER){
            //Now you can do whatever you want to depending upon whether the user ticks right or wrong option

            //Do something if the user hits the OK button
            if(resultCode == RESULT_OK){
                //Do something if you want to do with the achieved result
                //Fetch the image from the External Storage
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap b = BitmapFactory.decodeFile(image_path, options);

                //Display the captured image inside the ImageView
                imgView = (ImageView) findViewById(R.id.imageView);
                imgView.setImageBitmap(b);
            }

            //Do nothing if the user hits the cancel button
            if(resultCode == RESULT_CANCELED){
                //This will not allow the user to save the image after selecting to not capture the image
                flag_capture_button = false;
            }
        }
    }
}
