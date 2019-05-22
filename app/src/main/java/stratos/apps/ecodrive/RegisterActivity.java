package stratos.apps.ecodrive;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.storage.CredentialsManager;
import com.auth0.android.authentication.storage.SharedPreferencesStorage;
import com.google.android.gms.common.util.Strings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stratos.apps.ecodrive.data.ChildContract;
import stratos.apps.ecodrive.data.UserContract;
import stratos.apps.ecodrive.data.VehicleContract;
import stratos.apps.ecodrive.model.Child;
import stratos.apps.ecodrive.model.Registration;
import stratos.apps.ecodrive.model.Vehicle;
import stratos.apps.ecodrive.rest.ApiClient;
import stratos.apps.ecodrive.rest.ApiInterface;
import stratos.apps.ecodrive.utils.NetworkUtils;
import stratos.apps.ecodrive.utils.SaveToDBUtility;
import stratos.apps.ecodrive.utils.SendToServerUtility;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();

    @BindView(R.id.editText2) TextView txtFullName;
    @BindView(R.id.editText5) TextView txtEmail;
    @BindView(R.id.editText6) TextView txtPhone;
    @BindView(R.id.textView1) TextView txtChildrenAgeGroups;
    @BindView(R.id.textView4) TextView txtVehiclesDetails;
    @BindView(R.id.radioGroup) RadioGroup radioGroupGender;
    @BindView(R.id.spinner3) Spinner spinnerAgeGroup;
    @BindView(R.id.spinner6) Spinner spinnerMaritalStatus;
    @BindView(R.id.spinner8) Spinner spinnerChildrenNo;
    @BindView(R.id.spinner10) Spinner spinnerVehiclesNo;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private static List<Child> children = null;
    private static List<Vehicle> vehicles = null;
    private List<Registration> registrationList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        toolbar.setTitle(getResources().getString(R.string.title_activity_register));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.register_spinner_item,
                getResources().getStringArray(R.array.ageGroup_array));
        spinnerAgeGroup.setAdapter(dataAdapter);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, R.layout.register_spinner_item,
                getResources().getStringArray(R.array.maritalStatus_array));
        spinnerMaritalStatus.setAdapter(dataAdapter2);
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, R.layout.register_spinner_item,
                getResources().getStringArray(R.array.childrenNo_array));
        spinnerChildrenNo.setAdapter(dataAdapter3);
        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, R.layout.register_spinner_item,
                getResources().getStringArray(R.array.vehicleNo_array));
        spinnerVehiclesNo.setAdapter(dataAdapter4);

        spinnerChildrenNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 1) {
                    Intent intent = new Intent(RegisterActivity.this, ChildrenActivity.class);
                    intent.putExtra("childrenNo", spinnerChildrenNo.getSelectedItem().toString());
                    startActivity(intent);
                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        spinnerVehiclesNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    Intent intent = new Intent(RegisterActivity.this, VehiclesActivity.class);
                    intent.putExtra("vehiclesNo", spinnerVehiclesNo.getSelectedItem().toString());
                    startActivity(intent);
                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        txtVehiclesDetails.setMovementMethod(new ScrollingMovementMethod());
        txtChildrenAgeGroups.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onStart() {
        super.onStart();
        String childrenValues = "";
        if (children != null && children.size() > 0) {
            for (Child c : children) {
                childrenValues += c.getAgeGroup() + "\n";
            }
            txtChildrenAgeGroups.setText(childrenValues);
        }
        String vehiclesValues = "";
        if (vehicles != null && vehicles.size() > 0) {
            for (Vehicle v : vehicles) {
                vehiclesValues += v.getModel() + ", " + v.getAge() + ", " + v.getFuelType() + "\n";
            }
            txtVehiclesDetails.setText(vehiclesValues);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void register(View view) {
        Registration registration = null;

        if (Strings.isEmptyOrWhitespace(txtFullName.getText().toString()) ||
                Strings.isEmptyOrWhitespace(txtEmail.getText().toString()) ||
                Strings.isEmptyOrWhitespace(txtPhone.getText().toString()) ||
                radioGroupGender.getCheckedRadioButtonId() == -1 ||
                (!spinnerChildrenNo.getSelectedItem().toString().equals("0") && children == null) ||
                vehicles == null) {
            Toast.makeText(this, getResources().getString(R.string.prompt_fill_in_details), Toast.LENGTH_LONG).show();
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches()) {
                Toast.makeText(this, getResources().getString(R.string.prompt_invalid_email), Toast.LENGTH_LONG).show();
            } else {
                registration = new Registration();
                registration.setFullname(txtFullName.getText().toString());
                registration.setEmail(txtEmail.getText().toString());
                registration.setPhone(txtPhone.getText().toString());
                registration.setGender(((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString());
                registration.setAgeGroup(spinnerAgeGroup.getSelectedItem().toString());
                registration.setMaritalStatus(spinnerMaritalStatus.getSelectedItem().toString());
                if (children == null) {
                    List<Child> empty_children = new ArrayList<>();
                    registration.setChildren(empty_children);
                } else {
                    registration.setChildren(children);
                }
                registration.setVehicles(vehicles);

                // save date to db
                SaveToDBUtility.saveRegistration(registration, this);

                // prepare for sending data to server
                registrationList = new ArrayList<>();
                registrationList.add(registration);

                SharedPreferences sharedPref = getSharedPreferences("userData", MODE_PRIVATE);
                String token = sharedPref.getString("token", "");

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("email", registration.getEmail());
                editor.putString("gender", registration.getGender());
                editor.apply();

                // send data to server
                if (NetworkUtils.existsNetworkConnection(this)) {
                    SendToServerUtility.sendRegistration(registrationList, "Bearer " + token, RegisterActivity.this);
                }
            }
        }
    }

    public static void setChildren(List<Child> children) {
        RegisterActivity.children = children;
    }

    public static void setVehicles(List<Vehicle> vehicles) {
        RegisterActivity.vehicles = vehicles;
    }
}