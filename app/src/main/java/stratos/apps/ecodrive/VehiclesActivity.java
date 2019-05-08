package stratos.apps.ecodrive;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import stratos.apps.ecodrive.model.Vehicle;

public class VehiclesActivity extends AppCompatActivity {

    @BindView(R.id.linearLayout) LinearLayout linearLayout;
    @BindView(R.id.nestedLinearLayout) LinearLayout nestedLinearLayout;
    @BindView(R.id.button) Button button;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles);
        ButterKnife.bind(this);

        toolbar.setTitle("Enter your Vehicles Data");

        linearLayout.removeView(nestedLinearLayout);

        final int vehiclesNo = Integer.valueOf(getIntent().getStringExtra("vehiclesNo"));

        for (int i = 1; i<=vehiclesNo; i++) {
            TextView textView4 = new TextView(VehiclesActivity.this);
            textView4.setText("VEHICLE " + i);
            textView4.setTextSize(20);
            textView4.setTypeface(textView4.getTypeface(), Typeface.BOLD);
            textView4.setTextAppearance(R.style.VehiclesLabelColor);
            LinearLayout.LayoutParams params7 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params7.setMargins(0, 50,0,0);
            textView4.setLayoutParams(params7);
            linearLayout.addView(textView4);

            TextView textView = new TextView(VehiclesActivity.this);
            textView.setText("Brand / Model");
            textView.setTextSize(13);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setTextAppearance(R.style.RegisterLabelColor);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 30,0, 0);
            textView.setLayoutParams(params);
            linearLayout.addView(textView);

            EditText editText = new EditText(VehiclesActivity.this);
            editText.setId(i*10);
            editText.setEms(10);
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            editText.setTextAppearance(R.style.RegisterTextColor);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(700,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params2.setMargins(0, 0, 0, 0);
            editText.setLayoutParams(params2);
            linearLayout.addView(editText);

            TextView textView2 = new TextView(VehiclesActivity.this);
            textView2.setText("Vehicle Age");
            textView2.setTextSize(13);
            textView2.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView2.setTextAppearance(R.style.RegisterLabelColor);
            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params3.setMargins(0, 30, 0, 0);
            textView2.setLayoutParams(params3);
            linearLayout.addView(textView2);

            Spinner spinner = new Spinner(VehiclesActivity.this);
            spinner.setId(i*10 + 1);
            LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params4.setMargins(0, 0, 0, 0);
            spinner.setLayoutParams(params4);
            linearLayout.addView(spinner);
            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.register_spinner_item,
                    getResources().getStringArray(R.array.vehicleAge_array));
            spinner.setAdapter(dataAdapter);

            TextView textView3 = new TextView(VehiclesActivity.this);
            textView3.setText("Fuel Type");
            textView3.setTextSize(13);
            textView3.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView3.setTextAppearance(R.style.RegisterLabelColor);
            LinearLayout.LayoutParams params5 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params5.setMargins(0, 30, 0, 0);
            textView3.setLayoutParams(params5);
            linearLayout.addView(textView3);

            Spinner spinner2 = new Spinner(VehiclesActivity.this);
            spinner2.setId(i*10 + 2);
            LinearLayout.LayoutParams params6 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params6.setMargins(0, 0, 0, 0);
            spinner2.setLayoutParams(params6);
            linearLayout.addView(spinner2);
            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, R.layout.register_spinner_item,
                    getResources().getStringArray(R.array.fuelType_array));
            spinner2.setAdapter(dataAdapter2);
        }

//        Button button = new Button(VehiclesActivity.this);
//        button.setText("Save");
//        button.setTextAppearance(R.style.Widget_AppCompat_Button);
//        button.setBackgroundColor(getResources().getColor(R.color.colorButton));
//        LinearLayout.LayoutParams params7 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        params7.setMargins(0, 30, 0, 15);
//        button.setLayoutParams(params7);
//        linearLayout.addView(button);

        linearLayout.addView(nestedLinearLayout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean editBoxesAreFilled = true;
                for (int i = 1; i<=vehiclesNo; i++) {
                    if (((EditText) linearLayout.findViewById(i * 10)).getText().toString().equals("")) {
                        editBoxesAreFilled = false;
                        break;
                    }
                }
                if (!editBoxesAreFilled) {
                    Toast.makeText(VehiclesActivity.this, getResources().getString(R.string.prompt_fill_in_details), Toast.LENGTH_LONG).show();
                } else {
                    List<Vehicle> vehicles = new ArrayList<>();
                    for (int i = 1; i <= vehiclesNo; i++) {
                        Vehicle vehicle = new Vehicle();
                        vehicle.setIdentifier(UUID.randomUUID().toString());
                        vehicle.setModel(((EditText) linearLayout.findViewById(i * 10)).getText().toString());
                        vehicle.setAge(((Spinner) linearLayout.findViewById(i * 10 + 1)).getSelectedItem().toString());
                        vehicle.setFuelType(((Spinner) linearLayout.findViewById(i * 10 + 2)).getSelectedItem().toString());
                        vehicles.add(vehicle);
                    }
                    RegisterActivity.setVehicles(vehicles);
                    finish();
                }
            }
        });
    }
}