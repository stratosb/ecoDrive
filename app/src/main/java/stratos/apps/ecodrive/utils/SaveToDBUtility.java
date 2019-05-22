package stratos.apps.ecodrive.utils;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import stratos.apps.ecodrive.R;
import stratos.apps.ecodrive.data.ChildContract;
import stratos.apps.ecodrive.data.PerformanceContract;
import stratos.apps.ecodrive.data.TripContract;
import stratos.apps.ecodrive.data.UserContract;
import stratos.apps.ecodrive.data.VehicleContract;
import stratos.apps.ecodrive.model.Child;
import stratos.apps.ecodrive.model.Performance;
import stratos.apps.ecodrive.model.Registration;
import stratos.apps.ecodrive.model.Trip;
import stratos.apps.ecodrive.model.Vehicle;

public class SaveToDBUtility {

    public static int updateTripDataToSent(Context ctx) {
        ContentValues values = new ContentValues();
        values.put(TripContract.TripEntry.COLUMN_DATA_SENT, 1);

        String mSelection = "data_sent=?";
        String[] mSelectionArgs = new String[]{ "0" };

        int n = ctx.getContentResolver().update(TripContract.TripEntry.CONTENT_URI, values,
                mSelection, mSelectionArgs);
        return n;
    }

    public static int updatePerformanceToSent(Context ctx) {
        ContentValues values = new ContentValues();
        values.put(PerformanceContract.PerformanceEntry.COLUMN_DATA_SENT, 1);

        String mSelection = "data_sent=?";
        String[] mSelectionArgs = new String[]{ "0" };

        int n = ctx.getContentResolver().update(PerformanceContract.PerformanceEntry.CONTENT_URI, values,
                mSelection, mSelectionArgs);
        return n;
    }

    public static int updateRegistrationToSent(Context ctx) {
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_DATA_SENT, 1);

        String mSelection = "data_sent=?";
        String[] mSelectionArgs = new String[]{ "0" };

        int n = ctx.getContentResolver().update(UserContract.UserEntry.CONTENT_URI, values,
                mSelection, mSelectionArgs);
        return n;
    }

    public static void saveTrip(Trip trip, Context ctx) {
        ContentValues tripValues = new ContentValues();
        tripValues.put(TripContract.TripEntry.COLUMN_DATETIME, trip.getDate_time().getTime());
        tripValues.put(TripContract.TripEntry.COLUMN_DISTANCE, trip.getDistance());
        tripValues.put(TripContract.TripEntry.COLUMN_AVG_SPEED, trip.getAvg_speed());
        tripValues.put(TripContract.TripEntry.COLUMN_MAX_SPEED, trip.getMax_speed());
        tripValues.put(TripContract.TripEntry.COLUMN_SPEEDING_EVENT_NUMBER, trip.getSpeeding_event_number());
        tripValues.put(TripContract.TripEntry.COLUMN_SPEEDING_EVENT_DISTANCE, trip.getSpeeding_event_distance());
        tripValues.put(TripContract.TripEntry.COLUMN_PHONE_USAGE_EVENT_NUMBER, trip.getPhone_usage_event_number());
        tripValues.put(TripContract.TripEntry.COLUMN_PHONE_USAGE_EVENT_DISTANCE, trip.getPhone_usage_event_distance());
        tripValues.put(TripContract.TripEntry.COLUMN_ACCELERATION_EVENT_NUMBER, trip.getAcceleration_event_number());
        tripValues.put(TripContract.TripEntry.COLUMN_BREAKING_EVENT_NUMBER, trip.getBreaking_event_number());
        tripValues.put(TripContract.TripEntry.COLUMN_CORNERING_EVENT_NUMBER, trip.getCornering_event_number());
        tripValues.put(TripContract.TripEntry.COLUMN_IDLING_EVENT_DISTANCE, trip.getIdling_event_distance());
        tripValues.put(TripContract.TripEntry.COLUMN_TOTAL_PERFORMANCE, trip.getTotal_performance());
        tripValues.put(TripContract.TripEntry.COLUMN_VEHICLE_UUID, trip.getIdentifier());
        tripValues.put(TripContract.TripEntry.COLUMN_DATA_SENT, 0);

        Uri uri = ctx.getContentResolver().insert(TripContract.TripEntry.CONTENT_URI, tripValues);
        if (uri != null) {
            //Toast.makeText(ctx, "trip: " + ctx.getResources().getString(R.string.prompt_data_added), Toast.LENGTH_SHORT).show();
        }
    }

    public static void savePerformance(Performance performance, Context ctx) {
        ContentValues performanceValues = new ContentValues();
        performanceValues.put(PerformanceContract.PerformanceEntry.COLUMN_TOTAL_PERFORMANCE, performance.getTotal_performance());
        performanceValues.put(PerformanceContract.PerformanceEntry.COLUMN_SAFETY, performance.getSafety());
        performanceValues.put(PerformanceContract.PerformanceEntry.COLUMN_ECONOMY, performance.getEconomy());
        performanceValues.put(PerformanceContract.PerformanceEntry.COLUMN_SPEED, performance.getSpeed());
        performanceValues.put(PerformanceContract.PerformanceEntry.COLUMN_FOCUS, performance.getFocus());
        performanceValues.put(PerformanceContract.PerformanceEntry.COLUMN_PHONE_USAGE, performance.getPhone_usage());
        performanceValues.put(PerformanceContract.PerformanceEntry.COLUMN_ACCELERATION, performance.getAcceleration());
        performanceValues.put(PerformanceContract.PerformanceEntry.COLUMN_BREAKING, performance.getBreaking());
        performanceValues.put(PerformanceContract.PerformanceEntry.COLUMN_CORNERING, performance.getCornering());
        performanceValues.put(PerformanceContract.PerformanceEntry.COLUMN_EXCESSIVE_SPEEDING, performance.getExcessive_speeding());
        performanceValues.put(PerformanceContract.PerformanceEntry.COLUMN_SPEEDING, performance.getSpeeding());
        performanceValues.put(PerformanceContract.PerformanceEntry.COLUMN_IDLING, performance.getIdling());
        performanceValues.put(PerformanceContract.PerformanceEntry.COLUMN_VEHICLE_UUID, performance.getIdentifier());
        performanceValues.put(PerformanceContract.PerformanceEntry.COLUMN_DATA_SENT, 0);

        Uri uri = ctx.getContentResolver().insert(PerformanceContract.PerformanceEntry.CONTENT_URI, performanceValues);
        if (uri != null) {
            //Toast.makeText(ctx, "performance: " + ctx.getResources().getString(R.string.prompt_data_added), Toast.LENGTH_SHORT).show();
        }
    }

    public static void saveRegistration(Registration registration, Context ctx) {
        ContentValues userValues = new ContentValues();
        userValues.put(UserContract.UserEntry.COLUMN_FULLNAME, registration.getFullname());
        userValues.put(UserContract.UserEntry.COLUMN_EMAIL, registration.getEmail());
        userValues.put(UserContract.UserEntry.COLUMN_PHONE, registration.getPhone());
        userValues.put(UserContract.UserEntry.COLUMN_GENDER, registration.getGender());
        userValues.put(UserContract.UserEntry.COLUMN_AGE_GROUP, registration.getAgeGroup());
        userValues.put(UserContract.UserEntry.COLUMN_MARITAL_STATUS, registration.getMaritalStatus());
        userValues.put(UserContract.UserEntry.COLUMN_DATA_SENT, 0);
        Uri uri = ctx.getContentResolver().insert(UserContract.UserEntry.CONTENT_URI, userValues);
        if (uri != null) {
            //Toast.makeText(ctx, "user: " + ctx.getResources().getString(R.string.prompt_data_added), Toast.LENGTH_SHORT).show();
        }

        ContentValues vehicleValues = new ContentValues();
        for (Vehicle vehicle: registration.getVehicles()) {
            vehicleValues.put(VehicleContract.VehicleEntry.COLUMN_UUID, vehicle.getIdentifier());
            vehicleValues.put(VehicleContract.VehicleEntry.COLUMN_MODEL, vehicle.getModel());
            vehicleValues.put(VehicleContract.VehicleEntry.COLUMN_AGE, vehicle.getAge());
            vehicleValues.put(VehicleContract.VehicleEntry.COLUMN_FUEL_TYPE, vehicle.getFuelType());
            vehicleValues.put(VehicleContract.VehicleEntry.COLUMN_USER_ID, registration.getEmail());
            uri = ctx.getContentResolver().insert(VehicleContract.VehicleEntry.CONTENT_URI, vehicleValues);
            if (uri != null) {
                //Toast.makeText(ctx, "vehicle: " + ctx.getResources().getString(R.string.prompt_data_added), Toast.LENGTH_SHORT).show();
            }
        }

        if (registration.getChildren() != null) {
            ContentValues childValues = new ContentValues();
            for (Child child : registration.getChildren()) {
                childValues.put(ChildContract.ChildEntry.COLUMN_AGE_GROUP, child.getAgeGroup());
                childValues.put(ChildContract.ChildEntry.COLUMN_USER_ID, registration.getEmail());
                uri = ctx.getContentResolver().insert(ChildContract.ChildEntry.CONTENT_URI, childValues);
                if (uri != null) {
                    //Toast.makeText(ctx, "child: " + ctx.getResources().getString(R.string.prompt_data_added), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
