package stratos.apps.ecodrive;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import stratos.apps.ecodrive.model.Child;

public class ChildrenActivity extends AppCompatActivity {

    @BindView(R.id.linearLayout) LinearLayout linearLayout;
    @BindView(R.id.nestedLinearLayout) LinearLayout nestedLinearLayout;
    @BindView(R.id.button) Button button;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children);
        ButterKnife.bind(this);

        toolbar.setTitle("Enter your Children Data");

        linearLayout.removeView(nestedLinearLayout);

        final int childrenNo = Integer.valueOf(getIntent().getStringExtra("childrenNo"));

        for (int i = 1; i<=childrenNo; i++) {
            TextView textView4 = new TextView(ChildrenActivity.this);
            textView4.setText("CHILD " + i);
            textView4.setTextSize(20);
            textView4.setTypeface(textView4.getTypeface(), Typeface.BOLD);
            textView4.setTextAppearance(R.style.VehiclesLabelColor);
            LinearLayout.LayoutParams params7 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params7.setMargins(0, 50,0,0);
            textView4.setLayoutParams(params7);
            linearLayout.addView(textView4);

            TextView textView = new TextView(ChildrenActivity.this);
            textView.setText("Age Group");
            textView.setTextSize(15);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setTextAppearance(R.style.RegisterLabelColor);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 30, 0, 8);
            textView.setLayoutParams(params);
            linearLayout.addView(textView);

            Spinner spinner = new Spinner(ChildrenActivity.this);
            spinner.setId(i);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params2.setMargins(0, 0, 0, 10);
            spinner.setLayoutParams(params2);
            linearLayout.addView(spinner);
            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.register_spinner_item,
                    getResources().getStringArray(R.array.childrenAge_array));
            spinner.setAdapter(dataAdapter);
        }

//        Button button = new Button(ChildrenActivity.this);
//        button.setText("Save");
//        button.setTextAppearance(R.style.TextAppearance_AppCompat_Widget_Button_Borderless_Colored);
//        button.setBackgroundColor(getResources().getColor(R.color.colorButton));
//        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        params3.setMargins(0, 30, 0, 15);
//        button.setLayoutParams(params3);
//        linearLayout.addView(button);

        linearLayout.addView(nestedLinearLayout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Child> children = new ArrayList<>();
                for (int i = 1; i<=childrenNo; i++) {
                    Child child = new Child();
                    child.setAgeGroup(((Spinner)linearLayout.findViewById(i)).getSelectedItem().toString());
                    children.add(child);
                }
                RegisterActivity.setChildren(children);
                finish();
            }
        });
    }
}