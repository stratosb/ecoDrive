package stratos.apps.ecodrive.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import stratos.apps.ecodrive.model.Scoring;

public class PhoneCallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                //Toast.makeText(context,"Incoming Call State", Toast.LENGTH_SHORT).show();
                //Toast.makeText(context,"Ringing State Number is -"+incomingNumber, Toast.LENGTH_SHORT).show();
            }
            if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))) {
                //Toast.makeText(context,"Call Received State", Toast.LENGTH_SHORT).show();
                Scoring.phone_usage = true;
                Scoring.phone_usage_count++;
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                //Toast.makeText(context,"Call Idle State", Toast.LENGTH_SHORT).show();
                Scoring.phone_usage = false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}


