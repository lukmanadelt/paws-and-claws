package mobile.skripsi.pawsandclaws.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.helper.SharedPreferencesManager;

/**
 * Update Doctor Activity
 * Created by @lukmanadelt on 11/9/2017.
 */

public class DoctorUpdateActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etFullname;
    private RadioGroup rgStatus;
    private RadioButton rbActive, rbNotActive;
    private Button bUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_update);

        // Initial Component
        etFullname = findViewById(R.id.etFullname);
        rgStatus = findViewById(R.id.rgStatus);
        rbActive = findViewById(R.id.rbActive);
        rbNotActive = findViewById(R.id.rbNotActive);
        bUpdate = findViewById(R.id.bUpdate);

        // Set component to listen click event
        bUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
