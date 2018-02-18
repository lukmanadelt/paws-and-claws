package mobile.skripsi.pawsandclaws.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import mobile.skripsi.pawsandclaws.R;

/**
 * Report Activity
 * Created by @lukmanadelt on 11/12/2017.
 */

public class ReportActivity extends AppCompatActivity implements View.OnClickListener {
    private View parentView;
    private EditText etStartDate, etEndDate;
    private int year, month, day, report_type;
    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Initial Component
        parentView = findViewById(R.id.parentLayout);
        Spinner sReport = findViewById(R.id.sReport);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        Button bViewReport = findViewById(R.id.bViewReport);

        // Set component to listen click event
        etStartDate.setOnClickListener(this);
        etEndDate.setOnClickListener(this);
        bViewReport.setOnClickListener(this);

        // Report Spinner drop down elements
        List<String> report = new ArrayList<>();
        report.add("Laporan Jumlah Vaksin Terpakai");
        report.add("Laporan Jumlah Pemeriksaan Hewan");

        ArrayAdapter<String> sReportAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, report) {
            public @NonNull
            View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                ((TextView) v).setTextColor(getResources().getColorStateList(R.color.colorGray));

                return v;
            }

            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);

                ((TextView) v).setTextColor(getResources().getColorStateList(R.color.colorGray));

                return v;
            }
        };

        sReportAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sReport.setAdapter(sReportAdapter);

        sReport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                report_type = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        // Initial Date Picker element
        Calendar cDate = Calendar.getInstance();
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
                setDate(998);
                break;
            case R.id.etEndDate:
                setDate(999);
                break;
            case R.id.bViewReport:
                if (etStartDate.getText().toString().trim().isEmpty()) {
                    Snackbar.make(parentView, "Tanggal Mulai wajib diisi", Snackbar.LENGTH_SHORT).show();
                } else if (etEndDate.getText().toString().trim().isEmpty()) {
                    Snackbar.make(parentView, "Tanggal Akhir wajib diisi", Snackbar.LENGTH_SHORT).show();
                } else if (!dateValidation(etStartDate.getText().toString().trim(), etEndDate.getText().toString().trim())) {
                    Snackbar.make(parentView, "Tanggal Mulai wajib lebih kecil dari Tanggal Akhir", Snackbar.LENGTH_SHORT).show();
                } else {
                    Intent report = new Intent(this, ReportViewActivity.class);
                    report.putExtra("report_type", report_type);
                    report.putExtra("period_start", etStartDate.getText().toString().trim());
                    report.putExtra("period_end", etEndDate.getText().toString().trim());
                    startActivity(report);
                }
                break;
        }
    }

    @SuppressWarnings("deprecation")
    public void setDate(int id) {
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

    private static boolean dateValidation(String period_start, String period_end) {
        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        boolean b = false;

        try {
            if (dfDate.parse(period_start).before(dfDate.parse(period_end))) {
                b = true; // If start date is before end date
            } else if (dfDate.parse(period_start).equals(dfDate.parse(period_end))) {
                b = true; // If two dates are equal
            } else {
                b = false; // If start date is after the end date
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return b;
    }
}
