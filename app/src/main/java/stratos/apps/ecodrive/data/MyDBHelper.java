package stratos.apps.ecodrive.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ecoDrive.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        addUserTable(db);
        addVehicleTable(db);
        addChildTable(db);
        addTripTable(db);
        addPerformanceTable(db);
    }

    private void addUserTable(SQLiteDatabase db){
        final String SQL_CREATE_USER_TABLE =
                "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + " (" +
                        UserContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        UserContract.UserEntry.COLUMN_FULLNAME + " TEXT NOT NULL, " +
                        UserContract.UserEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
                        UserContract.UserEntry.COLUMN_PHONE + " TEXT NOT NULL, " +
                        UserContract.UserEntry.COLUMN_GENDER + " TEXT NOT NULL, " +
                        UserContract.UserEntry.COLUMN_AGE_GROUP + " TEXT NOT NULL, " +
                        UserContract.UserEntry.COLUMN_MARITAL_STATUS + " TEXT NOT NULL, " +
                        UserContract.UserEntry.COLUMN_DATA_SENT + " INTEGER NOT NULL" +
                        "); ";

        db.execSQL(SQL_CREATE_USER_TABLE);
    }

    private void addVehicleTable(SQLiteDatabase db){
        final String SQL_CREATE_VEHICLE_TABLE =
                "CREATE TABLE " + VehicleContract.VehicleEntry.TABLE_NAME + " (" +
                        VehicleContract.VehicleEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        VehicleContract.VehicleEntry.COLUMN_UUID + " TEXT NOT NULL, " +
                        VehicleContract.VehicleEntry.COLUMN_MODEL + " TEXT NOT NULL, " +
                        VehicleContract.VehicleEntry.COLUMN_AGE + " TEXT NOT NULL, " +
                        VehicleContract.VehicleEntry.COLUMN_FUEL_TYPE + " TEXT NOT NULL, " +
                        // Foreign Key to User table
                        VehicleContract.VehicleEntry.COLUMN_USER_ID + " INTEGER NOT NULL, " +
                        "FOREIGN KEY (" + VehicleContract.VehicleEntry.COLUMN_USER_ID + ") " +
                        "REFERENCES " + UserContract.UserEntry.TABLE_NAME +
                        " (" + UserContract.UserEntry._ID + ")" +
                        "); ";

        db.execSQL(SQL_CREATE_VEHICLE_TABLE);
    }

    private void addChildTable(SQLiteDatabase db){
        final String SQL_CREATE_CHILD_TABLE =
                "CREATE TABLE " + ChildContract.ChildEntry.TABLE_NAME + " (" +
                        ChildContract.ChildEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ChildContract.ChildEntry.COLUMN_AGE_GROUP + " TEXT NOT NULL, " +
                        // Foreign Key to User table
                        ChildContract.ChildEntry.COLUMN_USER_ID + " INTEGER NOT NULL, " +
                        "FOREIGN KEY (" + ChildContract.ChildEntry.COLUMN_USER_ID + ") " +
                        "REFERENCES " + UserContract.UserEntry.TABLE_NAME +
                        " (" + UserContract.UserEntry._ID + ")" +
                        "); ";

        db.execSQL(SQL_CREATE_CHILD_TABLE);
    }

    private void addTripTable(SQLiteDatabase db){
        final String SQL_CREATE_TRIP_TABLE =
                "CREATE TABLE " + TripContract.TripEntry.TABLE_NAME + " (" +
                        TripContract.TripEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        TripContract.TripEntry.COLUMN_DATETIME + " REAL NOT NULL, " +
                        TripContract.TripEntry.COLUMN_DISTANCE + " REAL NOT NULL, " +
                        TripContract.TripEntry.COLUMN_AVG_SPEED + " REAL NOT NULL, " +
                        TripContract.TripEntry.COLUMN_MAX_SPEED + " REAL NOT NULL, " +
                        TripContract.TripEntry.COLUMN_SPEEDING_EVENT_NUMBER + " INTEGER NOT NULL, " +
                        TripContract.TripEntry.COLUMN_SPEEDING_EVENT_DISTANCE + " REAL NOT NULL, " +
                        TripContract.TripEntry.COLUMN_PHONE_USAGE_EVENT_NUMBER + " INTEGER NOT NULL, " +
                        TripContract.TripEntry.COLUMN_PHONE_USAGE_EVENT_DISTANCE + " REAL NOT NULL, " +
                        TripContract.TripEntry.COLUMN_ACCELERATION_EVENT_NUMBER + " INTEGER NOT NULL, " +
                        TripContract.TripEntry.COLUMN_BREAKING_EVENT_NUMBER + " INTEGER NOT NULL, " +
                        TripContract.TripEntry.COLUMN_CORNERING_EVENT_NUMBER + " INTEGER NOT NULL, " +
                        TripContract.TripEntry.COLUMN_IDLING_EVENT_DISTANCE + " REAL NOT NULL, " +
                        TripContract.TripEntry.COLUMN_TOTAL_PERFORMANCE + " INTEGER NOT NULL, " +
                        TripContract.TripEntry.COLUMN_DATA_SENT + " INTEGER NOT NULL, " +
                        // Foreign Key to Vehicle table
                        TripContract.TripEntry.COLUMN_VEHICLE_UUID + " INTEGER NOT NULL, " +
                        "FOREIGN KEY (" + TripContract.TripEntry.COLUMN_VEHICLE_UUID + ") " +
                        "REFERENCES " + VehicleContract.VehicleEntry.TABLE_NAME +
                        " (" + VehicleContract.VehicleEntry.COLUMN_UUID + ")" +
                        "); ";

        db.execSQL(SQL_CREATE_TRIP_TABLE);
    }

    private void addPerformanceTable(SQLiteDatabase db){
        final String SQL_CREATE_PERFORMANCE_TABLE =
                "CREATE TABLE " + PerformanceContract.PerformanceEntry.TABLE_NAME + " (" +
                        PerformanceContract.PerformanceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        PerformanceContract.PerformanceEntry.COLUMN_TOTAL_PERFORMANCE + " REAL NOT NULL, " +
                        PerformanceContract.PerformanceEntry.COLUMN_SAFETY + " REAL NOT NULL, " +
                        PerformanceContract.PerformanceEntry.COLUMN_ECONOMY + " REAL NOT NULL, " +
                        PerformanceContract.PerformanceEntry.COLUMN_SPEED + " REAL NOT NULL, " +
                        PerformanceContract.PerformanceEntry.COLUMN_FOCUS + " REAL NOT NULL, " +
                        PerformanceContract.PerformanceEntry.COLUMN_PHONE_USAGE + " REAL NOT NULL, " +
                        PerformanceContract.PerformanceEntry.COLUMN_ACCELERATION + " REAL NOT NULL, " +
                        PerformanceContract.PerformanceEntry.COLUMN_BREAKING + " REAL NOT NULL, " +
                        PerformanceContract.PerformanceEntry.COLUMN_CORNERING + " REAL NOT NULL, " +
                        PerformanceContract.PerformanceEntry.COLUMN_EXCESSIVE_SPEEDING + " REAL NOT NULL, " +
                        PerformanceContract.PerformanceEntry.COLUMN_SPEEDING + " REAL NOT NULL, " +
                        PerformanceContract.PerformanceEntry.COLUMN_IDLING + " REAL NOT NULL, " +
                        PerformanceContract.PerformanceEntry.COLUMN_DATA_SENT + " INTEGER NOT NULL, " +
                        // Foreign Key to Vehicle table
                        PerformanceContract.PerformanceEntry.COLUMN_VEHICLE_UUID + " INTEGER NOT NULL, " +
                        "FOREIGN KEY (" + PerformanceContract.PerformanceEntry.COLUMN_VEHICLE_UUID + ") " +
                        "REFERENCES " + VehicleContract.VehicleEntry.TABLE_NAME +
                        " (" + VehicleContract.VehicleEntry.COLUMN_UUID + ")" +
                        "); ";

        db.execSQL(SQL_CREATE_PERFORMANCE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VehicleContract.VehicleEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ChildContract.ChildEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TripContract.TripEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PerformanceContract.PerformanceEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
