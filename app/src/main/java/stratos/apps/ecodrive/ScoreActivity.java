package stratos.apps.ecodrive;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import stratos.apps.ecodrive.model.Performance;
import stratos.apps.ecodrive.model.Scoring;
import stratos.apps.ecodrive.model.Trip;
import stratos.apps.ecodrive.model.VehicleSpinner;
import stratos.apps.ecodrive.utils.GetFromDBUtility;
import stratos.apps.ecodrive.utils.NetworkUtils;
import stratos.apps.ecodrive.utils.SaveToDBUtility;
import stratos.apps.ecodrive.utils.SendToServerUtility;

import static stratos.apps.ecodrive.model.Scoring.allSpeeds;

public class ScoreActivity extends AppCompatActivity {
    private static final String TAG = ScoreActivity.class.getSimpleName();
    public static final String LOGIN = "Login";

    /**
     * Code used in requesting runtime permissions.
     */
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    /**
     * Constant used in the location settings dialog.
     */
    private static final int REQUEST_CHECK_SETTINGS = 0x1;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    // Keys for storing activity state in the Bundle.
    private final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
    private final static String KEY_LOCATION = "location";
    private final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";

    public final static int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 11;

    /**
     * Provides access to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;

    /**
     * Provides access to the Location Settings API.
     */
    private SettingsClient mSettingsClient;

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    private LocationRequest mLocationRequest;

    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    private LocationSettingsRequest mLocationSettingsRequest;

    /**
     * Callback for Location events.
     */
    private LocationCallback mLocationCallback;

    /**
     * Represents a geographical location.
     */
    private Location currentLocation;

    /**
     * Tracks the status of the location updates request. Value changes when the user presses the
     * Start Updates and Stop Updates buttons.
     */
    private Boolean mRequestingLocationUpdates;

    /**
     * Time when the location was updated represented as a String.
     */
    private String mLastUpdateTime;
    private List<VehicleSpinner> vehiclesList;
    private List tripData = null;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.start_updates_button) Button startUpdatesBtn;
    @BindView(R.id.stop_updates_button) Button stopUpdatesBtn;
    @BindView(R.id.challenges_button) Button challengesBtn;
    @BindView(R.id.marketplace_button) Button marketplaceBtn;
    @BindView(R.id.progressBar) ProgressBar progressBar1;
    @BindView(R.id.progressBar2) ProgressBar progressBar2;
    @BindView(R.id.progressBar4) ProgressBar progressBar4;
    @BindView(R.id.textView2) TextView txtPoints;
    @BindView(R.id.textView3) TextView txtRank;
    @BindView(R.id.textView4) TextView textView4;
    @BindView(R.id.textView6) TextView textView6;
    @BindView(R.id.textView10) TextView textView10;
    @BindView(R.id.spinner) Spinner spinnerVehicles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (!GetFromDBUtility.isRegistrationSent(this)) {
            Intent intent = new Intent(ScoreActivity.this, RegisterActivity.class);
            startActivity(intent);
        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }
        }

        // get sharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("userData", MODE_PRIVATE);
        float points = sharedPref.getFloat("points", 0);
        float score = sharedPref.getFloat("score", 0);
        float safeDriving = sharedPref.getFloat("safeDriving", 0);
        float economyDriving = sharedPref.getFloat("economyDriving", 0);
        // update UI
        updateUIPercentages(points, score, safeDriving, economyDriving);

        String gender = sharedPref.getString("gender", "");
        // set the image
        Bitmap bitmap = null;
        if (gender.equals("Male")) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_male);
        } else {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_female);
        }
        // make the image rounded
        RoundedBitmapDrawable rounded = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        rounded.setCornerRadius(Math.max(bitmap.getWidth(), bitmap.getHeight()) / 2.0f);
        ImageView profileImg = (ImageView) findViewById(R.id.profile_image);
        profileImg.setImageDrawable(rounded);

        // set the parameters for location request
        mRequestingLocationUpdates = false;
        mLastUpdateTime = "";

        // Update values using data stored in the Bundle.
        updateValuesFromBundle(savedInstanceState);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        // Kick off the process of building the LocationCallback, LocationRequest, and
        // LocationSettingsRequest objects.
        createLocationCallback();
        createLocationRequest();
        buildLocationSettingsRequest();
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences sharedPref = getSharedPreferences("userData", MODE_PRIVATE);
        String email = sharedPref.getString("email", "");

        vehiclesList = GetFromDBUtility.getVehiclesModels(email, this);
        if (vehiclesList != null && vehiclesList.size() > 0) {
            List<String> vehicleList = new ArrayList<>();
            for (VehicleSpinner vehicleSpinner: vehiclesList) {
                vehicleList.add(vehicleSpinner.getModel());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    R.layout.score_spinner_item, vehicleList);
            spinnerVehicles.setAdapter(dataAdapter);
        }
    }

    public void updateUIPercentages(float points, float score, float safeDriving, float economyDriving) {
        txtPoints.setText(points + "");
        textView4.setText(Math.round(score) + "%       driving score");
        progressBar4.setProgress(Math.round(score));
        textView6.setText(Math.round(safeDriving) + "%");
        textView10.setText(Math.round(economyDriving) + "%");
        progressBar1.setProgress(Math.round(safeDriving));
        progressBar2.setProgress(Math.round(economyDriving));
    }

    /**
     * Updates fields based on data stored in the bundle.
     *
     * @param savedInstanceState The activity state saved in the Bundle.
     */
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and make sure that
            // the Start Updates and Stop Updates buttons are correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(KEY_REQUESTING_LOCATION_UPDATES)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        KEY_REQUESTING_LOCATION_UPDATES);
            }

            // Update the value of currentLocation from the Bundle and update the UI to show the
            // correct latitude and longitude.
            if (savedInstanceState.keySet().contains(KEY_LOCATION)) {
                // Since KEY_LOCATION was found in the Bundle, we can be sure that currentLocation
                // is not null.
                currentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            }

            // Update the value of mLastUpdateTime from the Bundle and update the UI.
            if (savedInstanceState.keySet().contains(KEY_LAST_UPDATED_TIME_STRING)) {
                mLastUpdateTime = savedInstanceState.getString(KEY_LAST_UPDATED_TIME_STRING);
            }
            updateUI();
        }
    }

    /**
     * Sets up the location request. Android has two location request settings:
     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     * <p/>
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused Location Provider API returns location updates that are
     * accurate to within a few feet.
     * <p/>
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Creates a callback for receiving location events.
     */
    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                currentLocation = locationResult.getLastLocation();
                Date now = new Date();
                mLastUpdateTime = DateFormat.getTimeInstance().format(now);
                // get the very first datetime of the trip started
                if (Scoring.firstDateTime == null) {
                    Scoring.firstDateTime = now;
                }
                Scoring.speed = currentLocation.getSpeed() * 3.6;  // km/h
                // get each speed in meters/second
                allSpeeds.add(Scoring.speed);

                if (Scoring.speed > 70) {
                    if (Scoring.excessive_speeding) {
                        if (Scoring.previousLocation != null) {
                            // distance in meters
                            Scoring.excessive_speeding_distance += currentLocation.distanceTo(Scoring.previousLocation);
                        }
                    } else {
                        Scoring.excessive_speeding = true;
                    }
                } else if (Scoring.speed <= 70) {
                    Scoring.excessive_speeding = false;
                }

                if (Scoring.speed >= 0 || Scoring.speed <= 10) {
                    if (Scoring.idling) {
                        if (Scoring.previousLocation != null) {
                            // distance in meters
                            Scoring.idling_distance += currentLocation.distanceTo(Scoring.previousLocation);
                        }
                    } else {
                        Scoring.idling = true;
                    }
                } else {
                    Scoring.idling = false;
                }

                if (Scoring.speed > 50) {
                    if (Scoring.previousSpeed < 50) {
                        Scoring.speeding_count++;
                    }
                }

                if (Scoring.previousSpeed > 0) {
                    double speed_difference = Scoring.speed - Scoring.previousSpeed;
                    if (speed_difference > 0) {     // acceleration
                        if (speed_difference > 13) {
                            Scoring.hard_acceleration_count++;
                        }
                    } else if (speed_difference < 0) {   // decceleration
                        if (Math.abs(speed_difference) > 9) {
                            Scoring.harsh_breaking_count++;
                        }
                    }
                }

                if (Scoring.phone_usage) {
                    // distance in meters
                    Scoring.phone_usage_distance += currentLocation.distanceTo(Scoring.previousLocation);
                }

                // calculate total distance in meters
                if (Scoring.previousLocation != null) {
                    // distance in meters
                    Scoring.totalDistance += currentLocation.distanceTo(Scoring.previousLocation);
                }

                // lastly save the latest speed
                Scoring.previousSpeed = Scoring.speed;
                Scoring.previousLocation = currentLocation;
            }
        };
    }

    /**
     * Uses a {@link com.google.android.gms.location.LocationSettingsRequest.Builder} to build
     * a {@link com.google.android.gms.location.LocationSettingsRequest} that is used for checking
     * if a device has the needed location settings.
     */
    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        updateUI();
                        break;
                }
                break;
        }
    }

    /**
     * Handles the Start Updates button and requests start of location updates. Does nothing if
     * updates have already been requested.
     */
    public void startUpdatesButtonHandler(View view) {
        if (!mRequestingLocationUpdates) {
            Trip trip = GetFromDBUtility.getUnsentTripData(ScoreActivity.this);
            if (trip != null) {
                Toast.makeText(this, getResources().getString(R.string.prompt_send_data_to_server), Toast.LENGTH_LONG).show();

                Performance performance = GetFromDBUtility.getUnsentPerformance(ScoreActivity.this);
                if (performance != null) {
                    tripData = new ArrayList<>();
                    tripData.add(trip);
                    tripData.add(performance);

                    // get data from sharedPreferences
                    SharedPreferences sharedPref = getSharedPreferences("userData", MODE_PRIVATE);
                    String token = sharedPref.getString("token", "");

                    // send data to server
                    if (NetworkUtils.existsNetworkConnection(this)) {
                        SendToServerUtility.sendTripData(tripData, "Bearer " + token, ScoreActivity.this);
                    }
                }
            } else {
                Scoring.clearAll();

                // Acquire screen lock
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                mRequestingLocationUpdates = true;
                setButtonsEnabledState();
                startLocationUpdates();
            }
        }
    }

    // in km/hr
    private double calcTotalAvgSpeed() {
        double totalAvgSpeed = 0;
        for (double speed: allSpeeds) {
            totalAvgSpeed += speed;
        }
        return totalAvgSpeed / allSpeeds.size();
    }

    /**
     * Handles the Stop Updates button, and requests removal of location updates.
     */
    public void stopUpdatesButtonHandler(View view) {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        stopLocationUpdates();

        // Release screen lock
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        double totalAvgSpeed = calcTotalAvgSpeed();
        double totalMaxSpeed = Collections.max(Scoring.allSpeeds);

        Scoring.totalDistance = Scoring.totalDistance / 1000;   // in km
        Scoring.idling_distance = Scoring.idling_distance / 1000;   // in km
        Scoring.phone_usage_distance = Scoring.phone_usage_distance / 1000;    // in km

        int Multiplier = 13;
        double hardAcceleration = 0;
        double harshBraking = 0;
        double harshCornering = 0;
        double excessiveSpeeding = 0;
        double idling = 0;
        double speeding = 0;
        double phoneUsageCount = 0;
        double phoneUsageDistance = 0;
        double phoneUsage = 0;

        if (Scoring.totalDistance > 0) {
            hardAcceleration = 100 - (Scoring.hard_acceleration_count * 1000) / Scoring.totalDistance;
            harshBraking = 100 - (Scoring.harsh_breaking_count * 1000) / Scoring.totalDistance;
            harshCornering = 100 - (0 * 1000) / Scoring.totalDistance;
            excessiveSpeeding = 100 - (((Scoring.excessive_speeding_distance / Scoring.totalDistance) * 100) * Multiplier);
            idling = 100 - (((Scoring.idling_distance / Scoring.totalDistance) * 100) * Multiplier);
            speeding = 100 - (Scoring.speeding_count * 1000) / Scoring.totalDistance;
            phoneUsageCount = 100 - (Scoring.phone_usage_count * 1000) / Scoring.totalDistance;
            phoneUsageDistance = 100 - (Scoring.phone_usage_distance / Scoring.totalDistance) * 12;
            phoneUsage = 0.3 * phoneUsageCount + 0.7 * phoneUsageDistance;
        }
        double focus = (hardAcceleration + harshBraking + excessiveSpeeding + phoneUsage) / 4;
        double speed = (excessiveSpeeding + speeding) / 2;
        double safety = (focus + speed) / 2;
        double economy = (focus + speed + idling) / 3;

        double totalScore = (hardAcceleration + harshBraking + excessiveSpeeding + idling +
                    speeding + phoneUsage) / 6;

        int pointsEarned = 0;
        if (totalScore >= 61 && totalScore <= 70) {
            pointsEarned = 1;
        } else if (totalScore >= 71 && totalScore <= 80) {
            pointsEarned = 2;
        } else if (totalScore >= 81 && totalScore <= 90) {
            pointsEarned = 3;
        } else if (totalScore >= 91 && totalScore <= 100) {
            pointsEarned = 5;
        }

        // update sharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("userData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        float points = sharedPref.getFloat("points", 0);
        float score = sharedPref.getFloat("score", 0);
        float safeDriving = sharedPref.getFloat("safeDriving", 0);
        float economyDriving = sharedPref.getFloat("economyDriving", 0);

        float finalPoints = points + pointsEarned;
        float finalScore = (score + (float)totalScore) / 2;
        float finalSafeDriving = (safeDriving + (float)safety) / 2;
        float finalEconomyDriving = (economyDriving + (float)economy) / 2;

        editor.putFloat("points", finalPoints);
        editor.putFloat("score", finalScore);
        editor.putFloat("safeDriving", finalSafeDriving);
        editor.putFloat("economyDriving", finalEconomyDriving);
        editor.apply();

        // update UI
        updateUIPercentages(finalPoints, finalScore, finalSafeDriving, finalEconomyDriving);


        Trip trip = new Trip();
        trip.setDate_time(new Date());
        trip.setDistance(Scoring.totalDistance);
        trip.setAvg_speed(totalAvgSpeed);
        trip.setMax_speed(totalMaxSpeed);
        trip.setSpeeding_event_number(Scoring.speeding_count);
        trip.setSpeeding_event_distance(Scoring.excessive_speeding_distance);
        trip.setPhone_usage_event_number(Scoring.phone_usage_count);
        trip.setPhone_usage_event_distance(Scoring.phone_usage_distance);
        trip.setAcceleration_event_number(Scoring.hard_acceleration_count);
        trip.setBreaking_event_number(Scoring.harsh_breaking_count);
        trip.setCornering_event_number(0);
        trip.setIdling_event_distance(Scoring.idling_distance);
        trip.setTotal_performance(totalScore);
        String uuid = vehiclesList.get(spinnerVehicles.getSelectedItemPosition()).getUuid();
        trip.setIdentifier(uuid);
        SaveToDBUtility.saveTrip(trip, this);

        Performance performance = new Performance();
        performance.setTotal_performance(totalScore);
        performance.setSafety(safety);
        performance.setEconomy(economy);
        performance.setSpeed(speed);
        performance.setFocus(focus);
        performance.setPhone_usage(phoneUsage);
        performance.setAcceleration(hardAcceleration);
        performance.setBreaking(harshBraking);
        performance.setCornering(harshCornering);
        performance.setExcessive_speeding(excessiveSpeeding);
        performance.setSpeeding(speeding);
        performance.setIdling(idling);
        performance.setIdentifier(uuid);
        SaveToDBUtility.savePerformance(performance, this);

        tripData = new ArrayList<>();
        tripData.add(trip);
        tripData.add(performance);

        // get data from sharedPreferences
        String token = sharedPref.getString("token", "");
        //long expiresAt = sharedPref.getLong("expiresAt", 0);

        // send data to server
        //if (NetworkUtils.existsNetworkConnection(this)) {
        //    SendToServerUtility.sendTripData(tripData, "Bearer " + token, ScoreActivity.this);
        //}
    }

    /**
     * Requests location updates from the FusedLocationApi. Note: we don't call this unless location
     * runtime permission has been granted.
     */
    private void startLocationUpdates() {
        // Begin by checking if the device has the necessary location settings.
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        //Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();

                        if (ActivityCompat.checkSelfPermission( ScoreActivity.this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                            requestPermissions();
                        }
                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");

                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(ScoreActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";

                                Log.e(TAG, errorMessage);
                                Toast.makeText(ScoreActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                                mRequestingLocationUpdates = false;
                        }

                        updateUI();
                    }
                });
    }

    /**
     * Updates all UI fields.
     */
    private void updateUI() {
        setButtonsEnabledState();
    }

    /**
     * Disables both buttons when functionality is disabled due to insuffucient location settings.
     * Otherwise ensures that only one button is enabled at any time. The Start Updates button is
     * enabled if the user is not requesting location updates. The Stop Updates button is enabled
     * if the user is requesting location updates.
     */
    private void setButtonsEnabledState() {
        if (!Scoring.phone_usage) {
            if (mRequestingLocationUpdates) {
                startUpdatesBtn.setEnabled(false);
                stopUpdatesBtn.setEnabled(true);
            } else {
                startUpdatesBtn.setEnabled(true);
                stopUpdatesBtn.setEnabled(false);
            }
        }
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    private void stopLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            Log.d(TAG, "stopLocationUpdates: updates never requested, no-op.");
            return;
        }

        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mRequestingLocationUpdates = false;
                        setButtonsEnabledState();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();

        // Within {@code onPause()}, we remove location updates. Here, we resume receiving
        // location updates if the user has requested them.
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        } else if (!checkPermissions()) {
            requestPermissions();
        }

        updateUI();

        if (Scoring.phone_usage) {
            Scoring.phone_usage = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (!Scoring.phone_usage) {
            // Remove location updates to save battery.
            stopLocationUpdates();
        }
    }

    /**
     * Stores activity data in the Bundle.
     */
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(KEY_REQUESTING_LOCATION_UPDATES, mRequestingLocationUpdates);
        savedInstanceState.putParcelable(KEY_LOCATION, currentLocation);
        savedInstanceState.putString(KEY_LAST_UPDATED_TIME_STRING, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(ScoreActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(ScoreActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length <= 0) {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                    Log.i(TAG, "User interaction was cancelled.");
                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mRequestingLocationUpdates) {
                        Log.i(TAG, "Permission granted, updates requested, starting location updates");
                        startLocationUpdates();
                    }
                } else {
                    // Permission denied.

                    // Notify the user via a SnackBar that they have rejected a core permission for the
                    // app, which makes the Activity useless. In a real app, core permissions would
                    // typically be best requested during a welcome-screen flow.

                    // Additionally, it is important to remember that a permission might have been
                    // rejected without asking the user for permission (device policy or "Never ask
                    // again" prompts). Therefore, a user interface affordance is typically implemented
                    // when permissions are denied. Otherwise, your app could appear unresponsive to
                    // touches or interactions which have required permissions.
                    showSnackbar(R.string.permission_denied_explanation,
                            R.string.settings, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Build intent that displays the App settings screen.
                                    Intent intent = new Intent();
                                    intent.setAction(
                                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package",
                                            BuildConfig.APPLICATION_ID, null);
                                    intent.setData(uri);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            });
                }
                break;
            }
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                }
                break;
            }
        }
    }

    public void showChallenges(View view) {
        Intent intent = new Intent(this, ChallengeActivity.class);
        startActivity(intent);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_results:
                Intent resultsIntent = new Intent(this, RegisterActivity.class);
                startActivity(resultsIntent);
                return true;
            case R.id.logout:
                SharedPreferences.Editor editor = getSharedPreferences(LOGIN, MODE_PRIVATE).edit();
                editor.putString("user", null);
                editor.apply();

                Intent signinIntent = new Intent(this, LoginActivity.class);
                startActivity(signinIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}