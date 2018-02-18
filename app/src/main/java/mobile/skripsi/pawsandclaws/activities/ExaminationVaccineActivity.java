package mobile.skripsi.pawsandclaws.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.api.APIService;
import mobile.skripsi.pawsandclaws.api.APIUrl;
import mobile.skripsi.pawsandclaws.helper.NonScrollListView;
import mobile.skripsi.pawsandclaws.helper.SharedPreferencesManager;
import mobile.skripsi.pawsandclaws.helper.VaccineAdapter;
import mobile.skripsi.pawsandclaws.model.ExaminationDetail;
import mobile.skripsi.pawsandclaws.model.ExaminationDetails;
import mobile.skripsi.pawsandclaws.model.Result;
import mobile.skripsi.pawsandclaws.model.Vaccine;
import mobile.skripsi.pawsandclaws.model.Vaccines;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Examination Vaccine Activity
 * Created by @lukmanadelt on 12/7/2017.
 */

public class ExaminationVaccineActivity extends AppCompatActivity implements View.OnClickListener {
    private View parentView;
    private EditText etWeight, etTemperature, etDueDate, etGivenDate;
    private NonScrollListView lvVaccine;
    private ArrayList<Vaccine> vaccines;
    private VaccineAdapter adapter;
    private int id, pet_id, year, month, day;
    private Double weight, temperature;
    private String due_date, given_date;
    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination_vaccine);

        // Initial Component
        parentView = findViewById(R.id.parentLayout);
        etWeight = findViewById(R.id.etWeight);
        etTemperature = findViewById(R.id.etTemperature);
        etDueDate = findViewById(R.id.etDueDate);
        etGivenDate = findViewById(R.id.etGivenDate);
        lvVaccine = findViewById(R.id.lvVaccine);
        Button bSave = findViewById(R.id.bSave);

        // Array List for binding data from JSON to this list
        vaccines = new ArrayList<>();

        // Set component to listen click event
        etDueDate.setOnClickListener(this);
        etGivenDate.setOnClickListener(this);
        bSave.setOnClickListener(this);

        // Initial pet id from previous activity
        id = SharedPreferencesManager.getInstance(getApplicationContext()).getUser().getId();
        pet_id = getIntent().getIntExtra("pet_id", 0);

        // Initial Date Picker element
        Calendar cDate = Calendar.getInstance();
        year = cDate.get(Calendar.YEAR);
        month = cDate.get(Calendar.MONTH);
        day = cDate.get(Calendar.DAY_OF_MONTH);
        decimalFormat = new DecimalFormat("00");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        etGivenDate.setText(new StringBuilder().append(year).append("-")
                .append(decimalFormat.format(Double.valueOf(month + 1))).append("-").append(decimalFormat.format(Double.valueOf(day))));

        // Getting all customers
        getVaccines(pet_id);
    }

    private void getVaccines(int pet_id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Vaccines> call = service.getVaccines(pet_id);

        call.enqueue(new Callback<Vaccines>() {
            @Override
            public void onResponse(Call<Vaccines> call, Response<Vaccines> response) {
                progressDialog.dismiss();

                vaccines = response.body().getVaccines();

                adapter = new VaccineAdapter(ExaminationVaccineActivity.this, vaccines);
                lvVaccine.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Vaccines> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parentView, t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.etDueDate:
                setDate(998);
                break;
            case R.id.etGivenDate:
                setDate(999);
                break;
            case R.id.bSave:
                weight = (etWeight.getText().toString().trim().isEmpty()) ? 0 : Double.parseDouble(etWeight.getText().toString().trim());
                temperature = (etTemperature.getText().toString().trim().isEmpty()) ? 0 : Double.parseDouble(etTemperature.getText().toString().trim());
                due_date = etDueDate.getText().toString().trim();
                given_date = etGivenDate.getText().toString().trim();

                if (etWeight.getText().toString().trim().isEmpty()) {
                    Snackbar.make(parentView, R.string.empty_weight, Snackbar.LENGTH_SHORT).show();
                } else if (etTemperature.getText().toString().trim().isEmpty()) {
                    Snackbar.make(parentView, R.string.empty_temperature, Snackbar.LENGTH_SHORT).show();
                } else if (given_date.isEmpty()) {
                    Snackbar.make(parentView, R.string.empty_given_date, Snackbar.LENGTH_SHORT).show();
                } else {
                    ArrayList<ExaminationDetail> examination_detail = new ArrayList<>();
                    ExaminationDetails examination_details = new ExaminationDetails();

                    for (int i = 0; i < adapter.getCount(); i++) {
                        if (adapter.getItem(i).getChecked() == 1) {
                            String next_date = (adapter.getItem(i).getNextDate() == null) ? "" : adapter.getItem(i).getNextDate();
                            String remark = (adapter.getItem(i).getRemark() == null) ? "" : adapter.getItem(i).getRemark();

                            examination_detail.add(new ExaminationDetail(adapter.getItem(i).getId(), next_date.trim(), remark.trim(), null));
                        }
                    }

                    examination_details.setExaminationDetails(examination_detail);

                    Gson gson = new Gson();
                    String details = gson.toJson(examination_details.getExaminationDetails());

                    if (details.equals("[]")) {
                        Snackbar.make(parentView, R.string.empty_vaccine_examination_detail, Snackbar.LENGTH_SHORT).show();
                    } else {
                        insertVaccineExamination(details);
                    }
                }
                break;
        }
    }

    /**
     * Method to inserting a vaccine examination
     */
    private void insertVaccineExamination(String details) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Menambah...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Result> call = service.insertVaccineExamination(id, pet_id, weight, temperature, due_date, given_date, details);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess()) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    finish();
                }

                Snackbar.make(parentView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parentView, t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @SuppressWarnings("deprecation")
    public void setDate(int id) {
        showDialog(id);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 998) {
            return new DatePickerDialog(this,
                    dueDateListener, year, month, day);
        } else if (id == 999) {
            return new DatePickerDialog(this,
                    givenDateListener, year, month, day);
        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener dueDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1, arg2 + 1, arg3, 0);
        }
    };

    private DatePickerDialog.OnDateSetListener givenDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1, arg2 + 1, arg3, 1);
        }
    };

    private void showDate(int year, int month, int day, int dateType) {
        switch (dateType) {
            case 0:
                etDueDate.setText(new StringBuilder().append(year).append("-")
                        .append(decimalFormat.format(Double.valueOf(month))).append("-").append(decimalFormat.format(Double.valueOf(day))));
                break;
            case 1:
                etGivenDate.setText(new StringBuilder().append(year).append("-")
                        .append(decimalFormat.format(Double.valueOf(month))).append("-").append(decimalFormat.format(Double.valueOf(day))));
                break;
        }
    }
}
