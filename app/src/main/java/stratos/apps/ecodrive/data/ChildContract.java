package stratos.apps.ecodrive.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class ChildContract {
    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "stratos.apps.ecodrive";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    public static final String PATH_CHILD = "child";

    // To prevent someone from accidentally instantiating the contract class
    private ChildContract() {}

    public static final class ChildEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CHILD).build();

        public static final String TABLE_NAME = "CHILD";
        public static final String COLUMN_AGE_GROUP = "age_group";
        public static final String COLUMN_USER_ID = "user_id";    // user's email

        public static Uri buildTripUriWithId(int id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(id))
                    .build();
        }
    }
}
