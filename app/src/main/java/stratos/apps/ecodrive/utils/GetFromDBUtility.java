package stratos.apps.ecodrive.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import stratos.apps.ecodrive.data.PerformanceContract;
import stratos.apps.ecodrive.data.TripContract;
import stratos.apps.ecodrive.data.UserContract;
import stratos.apps.ecodrive.data.VehicleContract;
import stratos.apps.ecodrive.model.Performance;
import stratos.apps.ecodrive.model.Trip;
import stratos.apps.ecodrive.model.VehicleSpinner;

public class GetFromDBUtility {

    public static String getVehicleUuid() {
        return null;
    }

    public static boolean isRegistrationSent(Context ctx) {
        String mSelection = "data_sent=?";
        String[] mSelectionArgs = new String[]{ "1" };
        Cursor cursor = ctx.getContentResolver().query(UserContract.UserEntry.CONTENT_URI,
                null,
                mSelection,
                mSelectionArgs,
                null,
                null);
        if (cursor != null && cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public static Trip getUnsentTripData(Context ctx) {
        String mSelection = "data_sent=?";
        String[] mSelectionArgs = new String[]{ "0" };
        Cursor cursor = ctx.getContentResolver().query(TripContract.TripEntry.CONTENT_URI,
                null,
                mSelection,
                mSelectionArgs,
                null,
                null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            int id1 = cursor.getColumnIndex(TripContract.TripEntry.COLUMN_DATETIME);
            int id2 = cursor.getColumnIndex(TripContract.TripEntry.COLUMN_DISTANCE);
            int id3 = cursor.getColumnIndex(TripContract.TripEntry.COLUMN_AVG_SPEED);
            int id4 = cursor.getColumnIndex(TripContract.TripEntry.COLUMN_MAX_SPEED);
            int id5 = cursor.getColumnIndex(TripContract.TripEntry.COLUMN_SPEEDING_EVENT_NUMBER);
            int id6 = cursor.getColumnIndex(TripContract.TripEntry.COLUMN_SPEEDING_EVENT_DISTANCE);
            int id7 = cursor.getColumnIndex(TripContract.TripEntry.COLUMN_PHONE_USAGE_EVENT_NUMBER);
            int id8 = cursor.getColumnIndex(TripContract.TripEntry.COLUMN_PHONE_USAGE_EVENT_DISTANCE);
            int id9 = cursor.getColumnIndex(TripContract.TripEntry.COLUMN_ACCELERATION_EVENT_NUMBER);
            int id10 = cursor.getColumnIndex(TripContract.TripEntry.COLUMN_BREAKING_EVENT_NUMBER);
            int id11 = cursor.getColumnIndex(TripContract.TripEntry.COLUMN_CORNERING_EVENT_NUMBER);
            int id12 = cursor.getColumnIndex(TripContract.TripEntry.COLUMN_IDLING_EVENT_DISTANCE);
            int id13 = cursor.getColumnIndex(TripContract.TripEntry.COLUMN_TOTAL_PERFORMANCE);
            int id14 = cursor.getColumnIndex(TripContract.TripEntry.COLUMN_VEHICLE_UUID);

            Trip trip = new Trip();
            trip.setDate_time(new Date(cursor.getLong(id1)));
            trip.setDistance(cursor.getDouble(id2));
            trip.setAvg_speed(cursor.getDouble(id3));
            trip.setMax_speed(cursor.getDouble(id4));
            trip.setSpeeding_event_number(cursor.getInt(id5));
            trip.setSpeeding_event_distance(cursor.getDouble(id6));
            trip.setPhone_usage_event_number(cursor.getInt(id7));
            trip.setPhone_usage_event_distance(id8);
            trip.setAcceleration_event_number(cursor.getInt(id9));
            trip.setBreaking_event_number(cursor.getInt(id10));
            trip.setCornering_event_number(cursor.getInt(id11));
            trip.setIdling_event_distance(cursor.getDouble(id12));
            trip.setTotal_performance(cursor.getDouble(id13));
            trip.setIdentifier(cursor.getString(id14));

            return trip;
        }
        return null;
    }

    public static Performance getUnsentPerformance(Context ctx) {
        String mSelection = "data_sent=?";
        String[] mSelectionArgs = new String[]{ "0" };
        Cursor cursor = ctx.getContentResolver().query(PerformanceContract.PerformanceEntry.CONTENT_URI,
                null,
                mSelection,
                mSelectionArgs,
                null,
                null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            int id1 = cursor.getColumnIndex(PerformanceContract.PerformanceEntry.COLUMN_TOTAL_PERFORMANCE);
            int id2 = cursor.getColumnIndex(PerformanceContract.PerformanceEntry.COLUMN_SAFETY);
            int id3 = cursor.getColumnIndex(PerformanceContract.PerformanceEntry.COLUMN_ECONOMY);
            int id4 = cursor.getColumnIndex(PerformanceContract.PerformanceEntry.COLUMN_SPEED);
            int id5 = cursor.getColumnIndex(PerformanceContract.PerformanceEntry.COLUMN_FOCUS);
            int id6 = cursor.getColumnIndex(PerformanceContract.PerformanceEntry.COLUMN_PHONE_USAGE);
            int id7 = cursor.getColumnIndex(PerformanceContract.PerformanceEntry.COLUMN_ACCELERATION);
            int id8 = cursor.getColumnIndex(PerformanceContract.PerformanceEntry.COLUMN_BREAKING);
            int id9 = cursor.getColumnIndex(PerformanceContract.PerformanceEntry.COLUMN_CORNERING);
            int id10 = cursor.getColumnIndex(PerformanceContract.PerformanceEntry.COLUMN_EXCESSIVE_SPEEDING);
            int id11 = cursor.getColumnIndex(PerformanceContract.PerformanceEntry.COLUMN_SPEEDING);
            int id12 = cursor.getColumnIndex(PerformanceContract.PerformanceEntry.COLUMN_IDLING);
            int id13 = cursor.getColumnIndex(PerformanceContract.PerformanceEntry.COLUMN_VEHICLE_UUID);

            Performance performance = new Performance();
            performance.setTotal_performance(cursor.getDouble(id1));
            performance.setSafety(cursor.getDouble(id2));
            performance.setEconomy(cursor.getDouble(id3));
            performance.setSpeed(cursor.getDouble(id4));
            performance.setFocus(cursor.getDouble(id5));
            performance.setPhone_usage(cursor.getDouble(id6));
            performance.setAcceleration(cursor.getDouble(id7));
            performance.setBreaking(cursor.getDouble(id8));
            performance.setCornering(cursor.getDouble(id9));
            performance.setExcessive_speeding(cursor.getDouble(id10));
            performance.setSpeeding(cursor.getDouble(id11));
            performance.setIdling(cursor.getDouble(id12));
            performance.setIdentifier(cursor.getString(id13));

            return performance;
        }
        return null;
    }

    public static List<VehicleSpinner> getVehiclesModels(String userEmail, Context ctx) {
        String mSelection = "user_id=?";
        String[] mSelectionArgs = new String[]{ userEmail };
        Cursor cursor = ctx.getContentResolver().query(VehicleContract.VehicleEntry.CONTENT_URI,
                null,
                mSelection,
                mSelectionArgs,
                null,
                null);

        List<VehicleSpinner> vehicleList = null;
        if (cursor != null && cursor.getCount() > 0) {
            vehicleList = new ArrayList<VehicleSpinner>();
            if (cursor.moveToFirst()) {
                int idUuid = cursor.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_UUID);
                int idModel = cursor.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_MODEL);
                do {
                    VehicleSpinner vehicleSpinner = new VehicleSpinner();
                    vehicleSpinner.setUuid(cursor.getString(idUuid));
                    vehicleSpinner.setModel(cursor.getString(idModel));
                    vehicleList.add(vehicleSpinner);
                } while (cursor.moveToNext());
            }
        }

        return vehicleList;
    }
}
