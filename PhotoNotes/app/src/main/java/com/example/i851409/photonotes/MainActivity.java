package com.example.i851409.photonotes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    //String photo_captions[] = {"Harsh Pandya","Pratham Vasa","Adit Vira","Rohit Makhija","Jay Shah"};
    static PhotoDatabase pdb;
    ContentValues cv;
    static SQLiteDatabase sqdb;
    public static final String key1 = "com.example.i851409.photonotes.caption111";
    public static final String key2 = "com.example.i851409.photonotes.filepath111";
    //public static final String key3 = "com.example.i851409.photonotes.position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //We now have to first fetch the captions and the file paths from the database table and feed it to the ArrayAdapter
        //Connecting to the database to create the table
        pdb = new PhotoDatabase(this);
        sqdb = pdb.getWritableDatabase();

        //Now run the SELECT query to fetch all the records from the database table
        Cursor csr = sqdb.query(PhotoDatabase.TABLE_NAME, null, null, null, null, null, null);

        //Make two string arrays and 1 int array whose size wil depend upon the number of rows returned to store the table data
        int total_count = csr.getCount();
        int array_ids[] = new int[total_count];
        final String array_captions[] = new String[total_count];
        final String array_filepaths[] = new String[total_count];

        //Variable to keep the counter for each row fetched from the database table
        int i = 0;

        while(csr.moveToNext()){
            array_ids[i] = csr.getInt(0);
            array_captions[i] = csr.getString(1);
            array_filepaths[i] = csr.getString(2);
            i = i + 1;
        }

        //A simple ArrayAdapter to store the names of the Captions of the images
        //The ArrayAdapter will fetch the data from the database
        ArrayAdapter adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.simple_list_view, R.id.textView, array_captions);

        //Instantiating the ListView object
        ListView lv = (ListView) findViewById(R.id.listView);

        //Setting the ArrayAdapter for the ListView to display the contents in the ListView fetched from the sqlite database
        lv.setAdapter(adapter);

        //Setting up a listener for each item inside the ListView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Whenever a particular item is clicked a new activity will be invoked
                Intent intent2 = new Intent(MainActivity.this, PhotoDescriptionActivity.class);

                //Depending upon the position clicked by the user to PhotoDescriptionActivity
                //Sending the Caption selected by the user to PhotoDescriptionActivity
                intent2.putExtra(key1, array_captions[position]);

                //Sending the Filepath associated to PhotoDescriptionActivity
                intent2.putExtra(key2, array_filepaths[position]);

                //Launching the activity PhotoDescriptionActivity
                startActivity(intent2);
            }
        });

        //Setting an onClick() event listener to the Floating Action Button
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //This will invoke the new activity called AddPhotoActivity
                Intent intent1 = new Intent(MainActivity.this, AddPhotoActivity.class);
                startActivity(intent1);
            }
        });
    }//end of onCreate()

    @Override
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
