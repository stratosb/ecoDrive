package stratos.apps.ecodrive.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class PerformanceContract {
    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "stratos.apps.ecodrive";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    public static final String PATH_PERFORMANCE = "performance";

    // To prevent someone from accidentally instantiating the contract class
    private PerformanceContract() {}

    public static final class PerformanceEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PERFORMANCE).build();

        public static final String TABLE_NAME = "PERFORMANCE";
        public static final String COLUMN_TOTAL_PERFORMANCE = "total_performance";
        public static final String COLUMN_SAFETY = "safety";
        public static final String COLUMN_ECONOMY = "economy";
        public static final String COLUMN_SPEED = "speed";
        public static final String COLUMN_FOCUS = "focus";
        public static final String COLUMN_PHONE_USAGE = "phone_usage";
        public static final String COLUMN_ACCELERATION = "acceleration";
        public static final String COLUMN_BREAKING = "breaking";
        public static final String COLUMN_CORNERING = "cornering";
        public static final String COLUMN_EXCESSIVE_SPEEDING = "excessive_speeding";
        public static final String COLUMN_SPEEDING = "speeding";
        public static final String COLUMN_IDLING = "idling";
        public static final String COLUMN_VEHICLE_UUID = "vehicle_uuid";    // vehicle's unique id
        public static final String COLUMN_DATA_SENT = "data_sent";    // check whether data has been sent to server successfully

        public static Uri buildTripUriWithId(int id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(id))
                    .build();
        }
    }
}
