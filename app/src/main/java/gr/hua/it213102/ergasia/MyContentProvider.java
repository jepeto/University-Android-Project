package gr.hua.it213102.ergasia;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;

import android.database.Cursor;
import android.database.SQLException;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MyContentProvider extends ContentProvider {
    static final String PROVIDER_NAME = "gr.hua.it213102.ergasia";
    static final String URL = "content://" + PROVIDER_NAME + "/userGeo";
    static final Uri CONTENT_URI = Uri.parse(URL);

    DBHandler dbHelper;


    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "userGeo", 1);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelper = new DBHandler(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */
        return (dbHelper.getWritableDatabase() == null)? false:true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        /**
         * Add a new student record
         */
        long rowID = dbHelper.addUser(values);

        /**
         * If record is added successfully
         */
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 101;
    }


//    @Override
//    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        int count = 0;
//        switch (uriMatcher.match(uri)){
//            case STUDENTS:
//                count = db.delete(STUDENTS_TABLE_NAME, selection, selectionArgs);
//                break;
//
//            case STUDENT_ID:
//                String id = uri.getPathSegments().get(1);
//                count = db.delete( STUDENTS_TABLE_NAME, _ID +  " = " + id +
//                                (!TextUtils.isEmpty(selection) ? "
//                        AND (" + selection + ')' : ""), selectionArgs);
//                break;
//            default:
//                throw new IllegalArgumentException("Unknown URI " + uri);
//        }
//
//        getContext().getContentResolver().notifyChange(uri, null);
//        return count;
//    }

    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case 1:
                count = dbHelper.updateUser(values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


}