package mobile.skripsi.pawsandclaws.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.api.APIService;
import mobile.skripsi.pawsandclaws.api.APIUrl;
import mobile.skripsi.pawsandclaws.model.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Update Doctor Activity
 * Created by @lukmanadelt on 11/9/2017.
 */

public class DoctorUpdateActivity extends AppCompatActivity implements View.OnClickListener {
    private View parentView;
    private EditText etUsername, etFullname;
    private RadioButton rbActive, rbNotActive;
    private Button bUpdate;
    private int id, status;
    private String username, fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_update);

        // Initial Component
        parentView = findViewById(R.id.parentLayout);
        etUsername = findViewById(R.id.etUsername);
        etFullname = findViewById(R.id.etFullname);
        rbActive = findViewById(R.id.rbActive);
        rbNotActive = findViewById(R.id.rbNotActive);
        bUpdate = findViewById(R.id.bUpdate);

        // Set component to listen click event
        bUpdate.setOnClickListener(this);

        // Initial doctor id from previous activity
        id = getIntent().getIntExtra("doctor_id", 0);

        // Getting a doctor
        getDoctor(id);
    }

    /**
     * Method to getting a doctor
     */
    private void getDoctor(int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Result> call = service.getDoctor(id);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();

                etUsername.setText(response.body().getDoctor().getUsername());
                etFullname.setText(response.body().getDoctor().getFullname());

                switch (response.body().getDoctor().getStatus()) {
                    case 0:
                        rbNotActive.setChecked(true);
                        break;
                    case 1:
                        rbActive.setChecked(true);
                        break;
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parentView, t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method to updating a doctor
     */
    private void updateDoctor(int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mengubah...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Result> call = service.updateDoctor(id, username, fullname, status);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess()) {
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
    public void onClick(View v) {
        if (v == bUpdate) {
            username = etUsername.getText().toString().trim();
            fullname = etFullname.getText().toString().trim();

            if (username.isEmpty()) {
                Snackbar.make(parentView, R.string.empty_username, Snackbar.LENGTH_SHORT).show();
            } else if (fullname.isEmpty()) {
                Snackbar.make(parentView, R.string.empty_fullname, Snackbar.LENGTH_SHORT).show();
            } else if (!rbActive.isChecked() && !rbNotActive.isChecked()) {
                Snackbar.make(parentView, R.string.empty_status, Snackbar.LENGTH_SHORT).show();
            } else {
                if (rbActive.isChecked()) {
                    status = 1;
                } else if (rbNotActive.isChecked()) {
                    status = 0;
                }

                updateDoctor(id);
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, DoctorListActivity.class));
        finish();
    }
}
