package mobile.skripsi.pawsandclaws.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mobile.skripsi.pawsandclaws.R;

/**
 * Report Activity
 * Created by @lukmanadelt on 11/12/2017.
 */

public class ReportActivity extends AppCompatActivity implements View.OnClickListener {
    private View parentView;
    private Spinner sReport;
    private EditText etStartDate, etEndDate;
    private Button bViewReport;
    private String start_date, end_date;
    private Calendar cDate;
    private int year, month, day;
    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Initial Component
        parentView = findViewById(R.id.parentLayout);
        sReport = findViewById(R.id.sReport);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        bViewReport = findViewById(R.id.bViewReport);

        // Set component to listen click event
        etStartDate.setOnClickListener(this);
        etEndDate.setOnClickListener(this);
        bViewReport.setOnClickListener(this);

        // Report Spinner drop down elements
        List<String> report = new ArrayList<String>();
        report.add("Laporan Jumlah Vaksin Terpakai");
        report.add("Laporan Jumlah Pemeriksaan Hewan");
        ArrayAdapter<String> sReportAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, report);
        sReportAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sReport.setAdapter(sReportAdapter);

        // Inital Date Picker element
        cDate = Calendar.getInstance();
        year = cDate.get(Calendar.YEAR);
        month = cDate.get(Calendar.MONTH);
        day = cDate.get(Calendar.DAY_OF_MONTH);
        decimalFormat = new DecimalFormat("00");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.etStartDate:
                setDate(v, 998);
                break;
            case R.id.etEndDate:
                setDate(v, 999);
                break;
        }
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view, int id) {
        showDialog(id);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 998) {
            return new DatePickerDialog(this,
                    startDateListener, year, month, day);
        } else if (id == 999) {
            return new DatePickerDialog(this,
                    endDateListener, year, month, day);
        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener startDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1, arg2 + 1, arg3, 0);
        }
    };

    private DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1, arg2 + 1, arg3, 1);
        }
    };

    private void showDate(int year, int month, int day, int dateType) {
        switch (dateType) {
            case 0:
                etStartDate.setText(new StringBuilder().append(year).append("-")
                        .append(decimalFormat.format(Double.valueOf(month))).append("-").append(decimalFormat.format(Double.valueOf(day))));
                break;
            case 1:
                etEndDate.setText(new StringBuilder().append(year).append("-")
                        .append(decimalFormat.format(Double.valueOf(month))).append("-").append(decimalFormat.format(Double.valueOf(day))));
                break;
        }
    }
}
