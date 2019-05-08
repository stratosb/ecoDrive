package stratos.apps.ecodrive.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import stratos.apps.ecodrive.R;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {
    /**
     * Checks whether an internet connection exists.
     * @param context
     * @return
     */
    public static boolean existsNetworkConnection(final Context context) {
        boolean existsNetworkConnection = false;

        try {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                existsNetworkConnection = true;
            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage(R.string.prompt_activate_connection)
                        .setCancelable(false)
                        .setPositiveButton(R.string.prompt_goto_settings,
                                new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent callWirelessSettingIntent = new Intent(
                                                android.provider.Settings.ACTION_WIFI_SETTINGS);
                                        context.startActivity(callWirelessSettingIntent);
                                    }
                                });
                alertDialogBuilder.setNegativeButton(R.string.prompt_no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id){
                                dialog.cancel();
                            }
                        });

                final AlertDialog alertDialog = alertDialogBuilder.create();  // create alert dialog

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                    }
                });

                alertDialog.show(); // show it
            }
        } catch (Exception ex) {
            Toast.makeText(context, R.string.prompt_connection_problem, Toast.LENGTH_LONG).show();
        }

        return existsNetworkConnection;
    }
}