package com.example.i851409.photonotes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoDescriptionActivity extends AppCompatActivity {

    TextView tvr;
    ImageView ivr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Receiving the intent from the activity MainActivity
        Intent intent3 = getIntent();

        //Preparing the variables to store the received Caption and Filepath
        String caption_received = intent3.getStringExtra(MainActivity.key1);
        String filepath_received = intent3.getStringExtra(MainActivity.key2);

        //Now fetch the TextView and the ImageView objects
        tvr = (TextView) findViewById(R.id.textView4);
        ivr = (ImageView) findViewById(R.id.imageView2);

        //Set the appropriate received Caption in the TextView
        tvr.setText(caption_received);

        //Set the appropriate received Image from the File Path in the ImageView
        //Fetch the image from the External Storage
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap b1 = BitmapFactory.decodeFile(filepath_received, options);

        //Now set the image inside the ImageView
        ivr.setImageBitmap(b1);
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

}
