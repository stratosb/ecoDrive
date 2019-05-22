package stratos.apps.ecodrive.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stratos.apps.ecodrive.LoginActivity;
import stratos.apps.ecodrive.RegisterActivity;
import stratos.apps.ecodrive.ScoreActivity;
import stratos.apps.ecodrive.model.Registration;
import stratos.apps.ecodrive.rest.ApiClient;
import stratos.apps.ecodrive.rest.ApiInterface;

public class SendToServerUtility {

    public static void sendRegistration(List<Registration> registrationList, String token, final Context ctx) {
        ApiInterface apiService = ApiClient.getClient(token).create(ApiInterface.class);
        Call call = apiService.registerUser(registrationList);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    SaveToDBUtility.updateRegistrationToSent(ctx);
                    Toast.makeText(ctx, "Registration sent successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ctx, ScoreActivity.class);
                    ctx.startActivity(intent);
                } else {
                    Toast.makeText(ctx, response.code() + ": " + response.message(), Toast.LENGTH_LONG).show();
                    //Intent intent = new Intent(ctx, LoginActivity.class);
                    //ctx.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {     // no internet connection
                Toast.makeText(ctx, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void sendTripData(List tripData, String token, final Context ctx) {
        ApiInterface apiService = ApiClient.getClient(token).create(ApiInterface.class);
        Call call = apiService.sendTripData(tripData);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    //Toast.makeText(ctx, "Trip data sent successfully", Toast.LENGTH_LONG).show();
                    SaveToDBUtility.updateTripDataToSent(ctx);
                    SaveToDBUtility.updatePerformanceToSent(ctx);
                } else {
                    Toast.makeText(ctx, response.code() + ": " + response.message(), Toast.LENGTH_LONG).show();
                    //Intent intent = new Intent(ctx, LoginActivity.class);
                    //ctx.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {     // no internet connection
                Toast.makeText(ctx, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
