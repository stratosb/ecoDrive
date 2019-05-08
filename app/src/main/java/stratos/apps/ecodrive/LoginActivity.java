package stratos.apps.ecodrive;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import retrofit2.Call;
import retrofit2.Callback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;
import stratos.apps.ecodrive.data.PerformanceContract;
import stratos.apps.ecodrive.data.TripContract;
import stratos.apps.ecodrive.model.Performance;
import stratos.apps.ecodrive.model.Trip;
import stratos.apps.ecodrive.rest.ApiClient;
import stratos.apps.ecodrive.rest.ApiInterface;
import stratos.apps.ecodrive.utils.GetFromDBUtility;
import stratos.apps.ecodrive.utils.SaveToDBUtility;
import stratos.apps.ecodrive.utils.SendToServerUtility;

public class LoginActivity extends AppCompatActivity {
    public static final String LOGIN_URL = "http://23.97.203.69/users"; //"https://georgemakrakis.eu.auth0.com/users";

    private static final String TAG = LoginActivity.class.getSimpleName();

    //    @BindView(R.id.spinner) Spinner spinner;
//    @BindView(R.id.toolbar) Toolbar toolbar;
//    @BindView(R.id.loginButton) Button loginButton;
//    @BindView(R.id.id_token) EditText id_token;

    private String idToken = "";
    private Date tokenExpiryDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login();
    }

    private void login() {
        Auth0 auth0 = new Auth0(this);
        auth0.setOIDCConformant(true);
        WebAuthProvider.init(auth0)
                .withScheme("https")
                .withAudience(String.format("https://%s/userinfo", getString(R.string.com_auth0_domain)))
                .start(LoginActivity.this, new AuthCallback() {
                    @Override
                    public void onFailure(@NonNull final Dialog dialog) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(final AuthenticationException exception) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onSuccess(@NonNull final Credentials credentials) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SharedPreferences sharedPref = getSharedPreferences("userData", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("token", credentials.getIdToken());
                                editor.putLong("expiresAt", credentials.getExpiresAt().getTime());
                                editor.apply();

                                finish();

                                //id_token.setText(credentials.getIdToken());
                                //idToken = credentials.getIdToken();
                                //tokenExpiryDate = credentials.getExpiresAt();

//                                if (!idToken.isEmpty()) {
//                                    OkHttpClient client = new OkHttpClient();
//                                    Request request = new Request.Builder()
//                                            .get()
//                                            .url(LOGIN_URL)
//                                            .addHeader("Authorization", "Bearer " + idToken)
//                                            .build();
//
//                                    client.newCall(request).enqueue(new Callback() {
//                                        @Override
//                                        public void onFailure(Request request, IOException e) {
//                                            runOnUiThread(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    Toast.makeText(LoginActivity.this, "An error occurred", Toast.LENGTH_LONG).show();
//                                                }
//                                            });
//                                        }
//
//                                        @Override
//                                        public void onResponse(final Response response) throws IOException {
//                                            runOnUiThread(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    if (response.isSuccessful()) {
//                                                        Toast.makeText(LoginActivity.this, "tokenExpiryDate: " + tokenExpiryDate, Toast.LENGTH_LONG).show();
//                                                        //Toast.makeText(LoginActivity.this, "API call success: " + response.code(), Toast.LENGTH_LONG).show();
//                                                    } else {
//                                                        Toast.makeText(LoginActivity.this, "API call failed: " + response.code(), Toast.LENGTH_LONG).show();
//                                                    }
//                                                }
//                                            });
//                                        }
//                                    });
//                                }
                            }
                        });
                    }
                });
    }
}
