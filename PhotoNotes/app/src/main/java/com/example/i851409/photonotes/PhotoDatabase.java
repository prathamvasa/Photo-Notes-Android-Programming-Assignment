package com.example.i851409.photonotes;

/**
 * Created by I851409 on 2/13/2016.
 */

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

//This Class basically creates the database, design of the database schema, creates a table and updates the database
public class PhotoDatabase extends SQLiteOpenHelper{
    static final String DATABASE_NAME = "PhotoDatabase.db";
    static final int DATABASE_VERSION = 1;
    static final String TABLE_NAME = "PhotoTable";
    static final String ID ="_id";
    static final String CAPTION = "caption";
    static final String FILE_PATH = "path";
    static final String TABLE_CREATE = "CREATE TABLE "+TABLE_NAME+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CAPTION+" VARCHAR(255), "+FILE_PATH+" VARCHAR(255));";
    static final String TABLE_QUERY = "SELECT * FROM "+TABLE_NAME;
    Context context;

    //Must implement this constructor
    public PhotoDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    //Must implement the onCreate() method
    //onCreate() method creates the database schema by creating a table with the specified columns
    public void onCreate(SQLiteDatabase db){
        //Creating the Table Definition
        try {
            db.execSQL(TABLE_CREATE);

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //Must also implement onUpgrade() which is invoked when there is any change in the database schema
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

}
