package stratos.apps.ecodrive.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * User Registration
 */
public class UserContract {
    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "stratos.apps.ecodrive";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    public static final String PATH_USER = "user";

    // To prevent someone from accidentally instantiating the contract class
    private UserContract() {}

    public static final class UserEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String TABLE_NAME = "USER";
        public static final String COLUMN_FULLNAME = "fullname";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_AGE_GROUP = "age_group";
        public static final String COLUMN_MARITAL_STATUS = "marital_status";

        public static Uri buildTripUriWithId(int id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(id))
                    .build();
        }
    }
}
