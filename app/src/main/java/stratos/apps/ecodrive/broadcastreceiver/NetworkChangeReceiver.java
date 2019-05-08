package stratos.apps.ecodrive.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {

    public static final String NETWORK_CHANGE_ACTION = "stratos.apps.ecodrive.broadcastreceiver.NetworkChangeReceiver";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (isOnline(context)) {
            Toast.makeText(context, "Internet Connected", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Internet Not Connected", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Check if network available or not
     *
     * @param context
     * */
    public boolean isOnline(Context context)
    {
        boolean isOnline = false;
        try {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connManager.getActiveNetworkInfo();

            isOnline = (netInfo != null && netInfo.isConnected());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return isOnline;
    }
}
