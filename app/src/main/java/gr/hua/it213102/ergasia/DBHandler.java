package gr.hua.it213102.ergasia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by it213102 on 25/11/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "data";
    // Table name
    private static final String TABLE_USERS = "userGeo";
    // Users Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USERID = "userid";
    private static final String KEY_LON = "longitude";
    private static final String KEY_LAT = "latitude";
    private static final String KEY_DT = "dt";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Table Query
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + KEY_USERID + " TEXT NOT NULL,"
                + KEY_LON + " REAL NOT NULL," + KEY_LAT + " REAL NOT NULL," + KEY_DT + " TEXT NOT NULL" + ")";
        //Execute table creation query
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        //Creating tables again
        onCreate(db);
    }

    public long addUser(ContentValues values) {
        long res; //Var to keep insert query result
        SQLiteDatabase db = this.getWritableDatabase(); //Get Writable Db instance
        res = db.insert(TABLE_USERS, null, values); //Exec insert query and save result
        db.close(); // Closing database connection
        return res; //Return Query Result
    }

    //Adding user to the db
    public long addUser(User user) {
        long res; //Var to keep insert query result
        SQLiteDatabase db = this.getWritableDatabase(); //Get Writable Db instance
        ContentValues values = new ContentValues();

        values.put(KEY_USERID, user.getUid()); // User Id
        values.put(KEY_LON, user.getLon()); // User Longitude
        values.put(KEY_LAT, user.getLat()); // User Latitude
        values.put(KEY_DT, user.getDt()); // User timestamp
        // Inserting Row
        res = db.insert(TABLE_USERS, null, values); //Exec insert query and save result
        db.close(); // Closing database connection
        return res; //Return Query Result
    }

    // Getting All Users(General Purpose Method)
    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<User>(); //List that will contain the results
        SQLiteDatabase db = this.getReadableDatabase(); //Get readable db instance
        Cursor cursor = db.query(TABLE_USERS,null,null,new String[]{},null,null,null); //Select * type of query
        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) { //If cursor isn't empty
            do {
                User user = new User(); //Create new user object
                //Set user parameters
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID))));
                user.setUid(cursor.getString(cursor.getColumnIndexOrThrow(KEY_USERID)));
                user.setLon(Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow(KEY_LON))));
                user.setLat(Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow(KEY_LAT))));
                user.setDt(cursor.getString(cursor.getColumnIndexOrThrow(KEY_DT)));

                //Adding user to list
                usersList.add(user);
            } while (cursor.moveToNext());
        }
        //Return Users List
        return usersList;
    }


    // Deleting a user (Only used for debugging)
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_USERID + " = ?",
                new String[] { String.valueOf(user.getUid()) });
        db.close();
    }

    //Get List of Users that have the uid or dt or both that we are searching for
    public List<User> getSearch(String uid, String dt) {
        List<User> usersList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,null,KEY_USERID + "=? OR " + KEY_DT + "=?",new String[]{uid, dt},null,null,null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID))));
                user.setUid(cursor.getString(cursor.getColumnIndexOrThrow(KEY_USERID)));
                user.setLon(Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow(KEY_LON))));
                user.setLat(Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow(KEY_LAT))));
                user.setDt(cursor.getString(cursor.getColumnIndexOrThrow(KEY_DT)));

                usersList.add(user);
            } while (cursor.moveToNext());
        }
        return usersList;
    }

    public int updateUser(ContentValues values,
                          String selection, String[] selectionArgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(TABLE_USERS, values, selection,
                selectionArgs);
    }

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERID, user.getUid()); // User Id
        values.put(KEY_LON, user.getLon()); // User Longitude
        values.put(KEY_LAT, user.getLat()); // User Latitude
        values.put(KEY_DT, user.getDt()); // User timestamp
        // updating row
        return db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

}