package stratos.apps.ecodrive.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class TripContract {
    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "stratos.apps.ecodrive";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    public static final String PATH_TRIP = "trip";

    // To prevent someone from accidentally instantiating the contract class
    private TripContract() {}

    public static final class TripEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRIP).build();

        public static final String TABLE_NAME = "TRIP";
        public static final String COLUMN_DATETIME = "dateTime";
        public static final String COLUMN_DISTANCE = "distance";
        public static final String COLUMN_AVG_SPEED = "avg_speed";
        public static final String COLUMN_MAX_SPEED = "max_speed";
        public static final String COLUMN_SPEEDING_EVENT_NUMBER = "speeding_event_number";
        public static final String COLUMN_SPEEDING_EVENT_DISTANCE = "speeding_event_distance";
        public static final String COLUMN_PHONE_USAGE_EVENT_NUMBER = "phone_usage_event_number";
        public static final String COLUMN_PHONE_USAGE_EVENT_DISTANCE = "phone_usage_event_distance";
        public static final String COLUMN_ACCELERATION_EVENT_NUMBER = "acceleration_event_number";
        public static final String COLUMN_BREAKING_EVENT_NUMBER = "breaking_event_number";
        public static final String COLUMN_CORNERING_EVENT_NUMBER = "cornering_event_number";
        public static final String COLUMN_IDLING_EVENT_DISTANCE = "idling_event_distance";
        public static final String COLUMN_TOTAL_PERFORMANCE = "total_performance";
        public static final String COLUMN_VEHICLE_UUID = "vehicle_uuid";    // vehicle's unique id
        public static final String COLUMN_DATA_SENT = "data_sent";    // check whether data has been sent to server successfully

        public static Uri buildTripUriWithId(int id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(id))
                    .build();
        }
    }
}