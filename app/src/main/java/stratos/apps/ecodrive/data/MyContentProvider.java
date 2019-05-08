package stratos.apps.ecodrive.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import stratos.apps.ecodrive.R;

public class MyContentProvider extends ContentProvider {
    public static final int USERS = 100;
    public static final int USER_WITH_ID = 101;
    public static final int VEHICLES = 200;
    public static final int VEHICLE_WITH_ID = 201;
    public static final int CHILDREN = 300;
    public static final int CHILD_WITH_ID = 301;
    public static final int TRIPS = 400;
    public static final int TRIP_WITH_ID = 401;
    public static final int PERFORMANCES = 500;
    public static final int PERFORMANCE_WITH_ID = 501;

    private static UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(UserContract.AUTHORITY, UserContract.PATH_USER, USERS);
        uriMatcher.addURI(UserContract.AUTHORITY, UserContract.PATH_USER + "/#", USER_WITH_ID);

        uriMatcher.addURI(VehicleContract.AUTHORITY, VehicleContract.PATH_VEHICLE, VEHICLES);
        uriMatcher.addURI(VehicleContract.AUTHORITY, VehicleContract.PATH_VEHICLE + "/#", VEHICLE_WITH_ID);

        uriMatcher.addURI(ChildContract.AUTHORITY, ChildContract.PATH_CHILD, CHILDREN);
        uriMatcher.addURI(ChildContract.AUTHORITY, ChildContract.PATH_CHILD + "/#", CHILD_WITH_ID);

        uriMatcher.addURI(TripContract.AUTHORITY, TripContract.PATH_TRIP, TRIPS);
        uriMatcher.addURI(TripContract.AUTHORITY, TripContract.PATH_TRIP + "/#", TRIP_WITH_ID);

        uriMatcher.addURI(PerformanceContract.AUTHORITY, PerformanceContract.PATH_PERFORMANCE, PERFORMANCES);
        uriMatcher.addURI(PerformanceContract.AUTHORITY, PerformanceContract.PATH_PERFORMANCE + "/#", PERFORMANCE_WITH_ID);

        return uriMatcher;
    }

    private MyDBHelper myDBHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        myDBHelper = new MyDBHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = myDBHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor retCursor = null;

        switch (match) {
            case USERS:
                retCursor = db.query(UserContract.UserEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case VEHICLES:
                retCursor = db.query(VehicleContract.VehicleEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case CHILDREN:
                retCursor = db.query(ChildContract.ChildEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case TRIPS:
                retCursor = db.query(TripContract.TripEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case TRIP_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = "_id=?";
                String[] mSelectionArgs = new String[]{id};

                retCursor = db.query(TripContract.TripEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case PERFORMANCES:
                retCursor = db.query(PerformanceContract.PerformanceEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException(getContext().getResources().getString(R.string.error_unknown_uri) + uri);
        }

        // Set a notification URI on the Cursor and return that Cursor
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = myDBHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri = null;
        long id = 0;

        switch (match) {
            case USERS:
                id = db.insert(UserContract.UserEntry.TABLE_NAME,
                        null,
                        contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(UserContract.UserEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException(getContext().getResources().getString(R.string.error_cannot_insert_row) + uri);
                }
                break;
            case VEHICLES:
                id = db.insert(VehicleContract.VehicleEntry.TABLE_NAME,
                        null,
                        contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(VehicleContract.VehicleEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException(getContext().getResources().getString(R.string.error_cannot_insert_row) + uri);
                }
                break;
            case CHILDREN:
                id = db.insert(ChildContract.ChildEntry.TABLE_NAME,
                        null,
                        contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(ChildContract.ChildEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException(getContext().getResources().getString(R.string.error_cannot_insert_row) + uri);
                }
                break;
            case TRIPS:
                id = db.insert(TripContract.TripEntry.TABLE_NAME,
                        null,
                        contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(TripContract.TripEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException(getContext().getResources().getString(R.string.error_cannot_insert_row) + uri);
                }
                break;
            case PERFORMANCES:
                id = db.insert(PerformanceContract.PerformanceEntry.TABLE_NAME,
                        null,
                        contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(PerformanceContract.PerformanceEntry .CONTENT_URI, id);
                } else {
                    throw new SQLException(getContext().getResources().getString(R.string.error_cannot_insert_row) + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException(getContext().getResources().getString(R.string.error_unknown_uri) + uri);
        }

        // Notify the resolver if the uri has been changed
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = myDBHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri = null;
        int rowsUpdated = 0;

        switch (match) {
            case TRIPS:
                rowsUpdated = db.update(TripContract.TripEntry.TABLE_NAME,
                        contentValues, s, strings);
                break;
            case PERFORMANCES:
                rowsUpdated = db.update(PerformanceContract.PerformanceEntry.TABLE_NAME,
                        contentValues, s, strings);
                break;
            default:
                throw new UnsupportedOperationException(getContext().getResources().getString(R.string.error_unknown_uri) + uri);
        }

        // Notify the resolver if the uri has been changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = myDBHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowsDeleted = 0;

        switch (match) {
            case TRIPS:
                rowsDeleted = db.delete(TripContract.TripEntry.TABLE_NAME, s, strings);
                break;
            case TRIP_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = "_id=?";
                String[] mSelectionArgs = new String[]{id};

                rowsDeleted = db.delete(TripContract.TripEntry.TABLE_NAME,
                        mSelection,
                        mSelectionArgs);
                break;
            default:
                throw new UnsupportedOperationException(getContext().getResources().getString(R.string.error_unknown_uri) + uri);
        }

        // Notify the resolver if the uri has been changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
